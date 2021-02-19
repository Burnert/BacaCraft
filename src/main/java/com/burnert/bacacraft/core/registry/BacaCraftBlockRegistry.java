package com.burnert.bacacraft.core.registry;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.block.BlockMountainCheese;
import com.burnert.bacacraft.block.BlockSmokehouseLegacy;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BacaCraftBlockRegistry {
	static final List<Block> blockList = new ArrayList<>();

	public static final BlockMountainCheese MOUNTAIN_CHEESE_BLOCK = new BlockMountainCheese();
	public static final BlockSmokehouseLegacy SMOKEHOUSE = new BlockSmokehouseLegacy();
	public static final BlockContraption CONTRAPTION = new BlockContraption();

	public static void register(Block block) {
		blockList.add(block);
	}

	static {

	}
}
