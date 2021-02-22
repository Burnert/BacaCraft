package com.burnert.bacacraft.item;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.core.item.ItemBlockCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBlockContraption extends ItemBlockCore {

	public ItemBlockContraption(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}

	public ItemStack withDefaultTag(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
//		stack.getTagCompound().setString("type", BlockContraption.getTypeFromMeta(stack.getMetadata()).getName());
		return stack;
	}

	// ItemBlock:

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + BlockContraption.Type.values()[stack.getMetadata()].getName();
	}

	// End of ItemBlock
}
