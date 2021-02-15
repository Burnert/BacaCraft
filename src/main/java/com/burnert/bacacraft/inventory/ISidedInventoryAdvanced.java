package com.burnert.bacacraft.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ISidedInventoryAdvanced extends IInventory {
    int[] getSlotsForFace(EnumFacing side);

    int getSlotStackLimit(int slot);

    boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction);

    boolean canExtractItem(int index, ItemStack stack, EnumFacing direction);
}
