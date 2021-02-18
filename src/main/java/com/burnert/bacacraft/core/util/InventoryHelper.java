package com.burnert.bacacraft.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public final class InventoryHelper extends net.minecraft.inventory.InventoryHelper {

	private InventoryHelper() { }

	public static NonNullList<ItemStack> getSlotSubarray(NonNullList<ItemStack> slots, int startIndex, int endIndex) {
		return NonNullList.from(ItemStack.EMPTY, slots.subList(startIndex, endIndex).toArray(new ItemStack[0]));
	}

	public static boolean isInventoryEmpty(NonNullList<ItemStack> slots) {
		return slots.stream().allMatch(ItemStack::isEmpty);
	}

	public static boolean isInventoryNotEmpty(NonNullList<ItemStack> slots) {
		return !isInventoryEmpty(slots);
	}

	public static boolean isInventoryFull(NonNullList<ItemStack> slots) {
		return slots.stream().noneMatch(ItemStack::isEmpty);
	}

	public static boolean isInventoryNotFull(NonNullList<ItemStack> slots) {
		return !isInventoryFull(slots);
	}
}
