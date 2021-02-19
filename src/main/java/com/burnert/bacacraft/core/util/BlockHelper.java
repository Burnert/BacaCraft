package com.burnert.bacacraft.core.util;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockHelper {

	private BlockHelper() { }

	public static boolean isBlockTopObstructed(World worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos.up()).getBlock().equals(Blocks.AIR);
	}
}
