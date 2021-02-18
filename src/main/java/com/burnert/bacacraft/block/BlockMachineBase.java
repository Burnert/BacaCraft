package com.burnert.bacacraft.block;

import com.burnert.bacacraft.core.block.BlockTileCore;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockMachineBase extends BlockTileCore {
	public BlockMachineBase(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockMachineBase(String name, Material materialIn, MapColor blockMapColorIn) {
		super(name, materialIn, blockMapColorIn);
	}
}
