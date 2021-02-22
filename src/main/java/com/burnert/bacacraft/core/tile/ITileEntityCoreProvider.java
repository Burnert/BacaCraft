package com.burnert.bacacraft.core.tile;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface ITileEntityCoreProvider extends ITileEntityProvider {

	@Nullable
	TileEntityCore createNewTileEntityCore(World worldIn, int meta);
}
