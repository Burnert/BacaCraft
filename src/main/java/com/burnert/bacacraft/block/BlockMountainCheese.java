package com.burnert.bacacraft.block;

import com.burnert.bacacraft.core.BacaCraftCreativeTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class BlockMountainCheese extends BlockRotatedPillar {
	public BlockMountainCheese() {
		this("mountain_cheese_block");
	}

	public BlockMountainCheese(String name) {
		super(name, Material.CAKE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
		this.setSound(SoundType.CLOTH);
		this.setHardness(0.8f);
		this.setResistance(0.6f);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}
}
