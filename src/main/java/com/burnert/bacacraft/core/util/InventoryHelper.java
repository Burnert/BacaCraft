package com.burnert.bacacraft.core.util;

import com.burnert.bacacraft.BacaCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public final class InventoryHelper extends net.minecraft.inventory.InventoryHelper {

	private InventoryHelper() { }

	/**
	 * Returns a subarray from an ItemStack array (inventory slots)
	 * @param slots inventory array
	 * @param startIndex first slot in the array (inclusive)
	 * @param endIndex last slot in the array (exclusive)
	 * @return new subarray
	 */
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

	public static void openGui(TileEntity entity, EntityPlayer player, int id) {
		BlockPos pos = entity.getPos();
		player.openGui(BacaCraft.MOD_ID, id, entity.getWorld(), pos.getX(), pos.getY(), pos.getZ());
	}
}
