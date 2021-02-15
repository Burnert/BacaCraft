package com.burnert.bacacraft.inventory;

import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSmokehouse extends Slot {
	public SlotSmokehouse(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == BacaCraftItemRegistry.MOUNTAIN_CHEESE;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return this.getSlotStackLimit();
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
