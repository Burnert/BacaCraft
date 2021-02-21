package com.burnert.bacacraft.core.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockHelper {

	private BlockHelper() { }

	public static boolean isBlockTopObstructed(World worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos.up()).getBlock().equals(Blocks.AIR);
	}

	public static String getBlockHarvestTool(Block block, int metadata) {
		return block.getHarvestTool(block.getStateFromMeta(metadata));
	}

	public static String getBlockHarvestTool(Block block) {
		return getBlockHarvestTool(block, 0);
	}
}
