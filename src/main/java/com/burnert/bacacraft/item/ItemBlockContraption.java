package com.burnert.bacacraft.item;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.core.item.ItemBlockCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockContraption extends ItemBlockCore {

	public ItemBlockContraption(Block block) {
		super(block);
	}

	public ItemStack withDefaultTag(ItemStack stack) {
//		stack.setTagInfo();
		return stack;
	}

	// ItemBlock:

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + BlockContraption.Type.values()[stack.getMetadata()].getName();
	}

	// End of ItemBlock
}
