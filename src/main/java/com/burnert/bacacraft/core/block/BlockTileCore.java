package com.burnert.bacacraft.core.block;

import com.burnert.bacacraft.core.property.NBTProperty;
import com.burnert.bacacraft.core.property.util.NBTPropertyHelper;
import com.burnert.bacacraft.core.tile.ITileEntityCoreProvider;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import com.burnert.bacacraft.core.util.ItemStackHelper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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
import java.util.Map;

public abstract class BlockTileCore extends BlockCore implements ITileEntityCoreProvider {

	public BlockTileCore(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockTileCore(String name, Material materialIn, MapColor blockMapColorIn) {
		super(name, materialIn, blockMapColorIn);
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

		TileEntityCore tileEntityCore = (TileEntityCore) worldIn.getTileEntity(pos);
		if (tileEntityCore != null) {
			tileEntityCore.createNBTProperties();

			if (stack.hasDisplayName() && tileEntityCore instanceof ITileNameable) {
				((ITileNameable)tileEntityCore).setCustomName(stack.getDisplayName());
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
		ItemStackHelper.lazyInitTagCompound(stack);

		TileEntity te = world.getTileEntity(pos);

		TileEntityCore tileEntityCore = (TileEntityCore) world.getTileEntity(pos);
		setItemProperties(stack, tileEntityCore);

		drops.add(stack);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);

		TileEntityCore tileEntityCore = (TileEntityCore) world.getTileEntity(pos);
		setItemProperties(stack, tileEntityCore);

		return stack;
	}

	// End of Block

	private static void setItemProperties(ItemStack stack, TileEntityCore entity) {
		if (entity != null) {
			Map<String, NBTProperty> properties = entity.getNBTProperties();

			for (Map.Entry<String, NBTProperty> entry : properties.entrySet()) {
				NBTProperty property = entry.getValue();

				if (property.isSet()) {
					if (property.isDisplayName()) {
						ItemStackHelper.setStackDisplayName(stack, property.getStringValue());
						continue;
					}
					if (property.isPersistent()) {
						NBTPropertyHelper.writeNBTProperty(property, stack.getTagCompound());
					}
				}
			}
		}
	}
}
