package com.burnert.bacacraft.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackHelper extends net.minecraft.inventory.ItemStackHelper {

	public static void lazyInitTagCompound(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	public static ItemStack setStackDisplayName(ItemStack stack, String displayName) {
		lazyInitTagCompound(stack);
		NBTTagCompound compound = stack.getTagCompound();
		if (!compound.hasKey("display")) {
			compound.setTag("display", new NBTTagCompound());
		}
		NBTTagCompound display = (NBTTagCompound)compound.getTag("display");
		display.setString("Name", displayName);
		return stack;
	}

	private ItemStackHelper() { }
}
