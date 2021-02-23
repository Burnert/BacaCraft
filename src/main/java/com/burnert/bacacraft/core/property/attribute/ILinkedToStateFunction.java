package com.burnert.bacacraft.core.property.attribute;

import com.burnert.bacacraft.core.property.tile.NBTProperty;
import net.minecraft.block.state.IBlockState;

public interface ILinkedToStateFunction {
	IBlockState updateState(IBlockState state, NBTProperty nbtProperty);
}
