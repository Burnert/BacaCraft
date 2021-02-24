package com.burnert.bacacraft.core.block;

import com.burnert.bacacraft.core.property.attribute.EnumAttributeType;
import com.burnert.bacacraft.core.property.attribute.ILinkedToStateFunction;
import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;
import com.burnert.bacacraft.core.property.tile.NBTProperty;
import com.burnert.bacacraft.core.property.tile.NBTPropertyContainer;
import com.burnert.bacacraft.core.property.util.NBTPropertyHelper;
import com.burnert.bacacraft.core.property.util.PropertyLinker;
import com.burnert.bacacraft.core.tile.ITileEntityCoreProvider;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import com.burnert.bacacraft.core.util.ItemStackHelper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTileCore extends BlockCore implements ITileEntityCoreProvider {

	public BlockTileCore(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockTileCore(String name, Material materialIn, MapColor blockMapColorIn) {
		super(name, materialIn, blockMapColorIn);
	}

	public static TileEntityCore tileFromWorld(IBlockAccess world, BlockPos pos) {
		return (TileEntityCore) world.getTileEntity(pos);
	}

	// ITileEntityProvider:

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return this.createNewTileEntityCore(world, meta);
	}

	// End of ITileEntityProvider

	// Block:

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityCore entity = tileFromWorld(worldIn, pos);
		if (entity != null) {
			IBlockState newState = state;
			NBTPropertyContainer propertyContainer = entity.getNBTPropertyContainer();

			for (NBTProperty nbtProperty : propertyContainer) {
				NBTPropertyAttribute.LinkedToState attributeLinkedToState =
						(NBTPropertyAttribute.LinkedToState) nbtProperty.getAttribute(EnumAttributeType.LINKED_TO_STATE);
				if (attributeLinkedToState != null) {
					if (attributeLinkedToState.hasCustomStateFunction()) {
						ILinkedToStateFunction function = attributeLinkedToState.getStateFunction();
						IProperty property = attributeLinkedToState.getProperty();
						newState = newState.withProperty(property, function.updateState(nbtProperty));
						continue;
					}

					// TODO: Add the rest
					switch (nbtProperty.getTagType()) {
						case BOOLEAN:
							PropertyLinker<Boolean> linker = nbtProperty.getPropertyLinker();
							IProperty<Boolean> property = linker.getLinkedProperty();
							newState = newState.withProperty(property, linker.getPropertyValue());
							break;
					}
				}
			}
			return newState;
		}
		return state;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		TileEntityCore entity = tileFromWorld(worldIn, pos);
		if (entity != null) {
			entity.initProperties();

			if (stack.hasDisplayName() && entity instanceof ITileNameable) {
				((ITileNameable)entity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		// Defer block removal until drops are altered.
		// TileEntity would get deleted if this wasn't done.
		if (willHarvest) {
			return true;
		}
		return super.removedByPlayer(state, world, pos, player, false);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Item item = Item.getItemFromBlock(this);
		ItemStack stack = new ItemStack(item, 1, this.getMetaFromState(state));

		TileEntityCore entity = tileFromWorld(world, pos);
		setItemProperties(stack, entity);

		drops.add(stack);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);

		TileEntityCore entity = tileFromWorld(world, pos);
		setItemProperties(stack, entity);

		return stack;
	}

	// End of Block

	private static void setItemProperties(ItemStack stack, TileEntityCore entity) {
		if (entity != null) {
			NBTPropertyContainer propertyContainer = entity.getNBTPropertyContainer();
			if (propertyContainer == null) return; // FIXME: The reason for this line needs further examination...

			for (NBTProperty nbtProperty : propertyContainer) {
				if (nbtProperty.isSet()) {
					if (nbtProperty.hasAttribute(EnumAttributeType.DISPLAY_NAME)) {
						ItemStackHelper.setStackDisplayName(stack, nbtProperty.getStringValue());
						continue;
					}
					if (nbtProperty.hasAttribute(EnumAttributeType.PERSISTENT)) {
						NBTPropertyHelper.writeNBTProperty(nbtProperty, stack.getTagCompound());
					}
				}
			}
		}
	}
}
