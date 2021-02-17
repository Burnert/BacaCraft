package com.burnert.bacacraft.core.registry;

import com.burnert.bacacraft.block.BlockMountainCheese;
import com.burnert.bacacraft.block.BlockSmokehouse;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BacaCraftBlockRegistry {
	public static final List<Block> blockList = new ArrayList<>();

	public static final BlockMountainCheese MOUNTAIN_CHEESE_BLOCK = new BlockMountainCheese();
	public static final BlockSmokehouse SMOKEHOUSE = new BlockSmokehouse();

	static {

	}
}
