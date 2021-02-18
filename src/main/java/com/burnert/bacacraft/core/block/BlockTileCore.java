package com.burnert.bacacraft.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class BlockTileCore extends BlockCore implements ITileEntityProvider {

	public BlockTileCore(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockTileCore(String name, Material materialIn, MapColor blockMapColorIn) {
		super(name, materialIn, blockMapColorIn);
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
}
