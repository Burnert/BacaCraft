package com.burnert.bacacraft.core;

import com.burnert.bacacraft.block.BlockBase;
import com.burnert.bacacraft.block.BlockMountainCheese;
import com.burnert.bacacraft.block.BlockSmokehouse;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BacaCraftBlockRegistry {
	public static final List<Block> blockList = new ArrayList<>();

	public static final BlockMountainCheese MOUNTAIN_CHEESE_BLOCK = new BlockMountainCheese();
	public static final BlockSmokehouse SMOKEHOUSE = new BlockSmokehouse();

	static {

	}
}
