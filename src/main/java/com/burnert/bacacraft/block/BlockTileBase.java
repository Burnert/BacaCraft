package com.burnert.bacacraft.block;

import com.burnert.bacacraft.core.block.BlockTileCore;
import com.burnert.bacacraft.core.inventory.IGuiProvider;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.tile.TileEntityContraption;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTileBase extends BlockTileCore {

	public BlockTileBase(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockTileBase(String name, Material materialIn, MapColor blockMapColorIn) {
		super(name, materialIn, blockMapColorIn);
	}

	protected <T extends Enum<T> & IStringSerializable> BlockTileBase setDefaultTileType(PropertyEnum<T> propertyEnum, T type) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(propertyEnum, type));
		return this;
	}

	public boolean onBlockActivatedImpl(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	// Block:

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
			return true;

		TileEntityContraption tileContraption = (TileEntityContraption) worldIn.getTileEntity(pos);
		if (tileContraption != null)
			System.out.println("Facing: " + tileContraption.getFacing().toString());

		if (tileContraption == null || tileContraption.isInvalid())
			return false;

		if (this.onBlockActivatedImpl(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ))
			return true;

		if (tileContraption instanceof IGuiProvider) {
			return ((IGuiProvider)tileContraption).openGui(playerIn);
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (entity instanceof IInventory) {
			IInventory inventory = (IInventory) entity;
			InventoryHelper.dropInventoryItems(worldIn, pos, inventory);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof ITileNameable) {
				((ITileNameable)tileentity).setCustomName(stack.getDisplayName());
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	// End of Block
}
