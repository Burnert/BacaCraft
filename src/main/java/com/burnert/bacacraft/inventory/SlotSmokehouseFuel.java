package com.burnert.bacacraft.inventory;

import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSmokehouseFuel extends Slot {

    public SlotSmokehouseFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TileEntitySmokehouse.isItemFuel(stack);
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }
}
