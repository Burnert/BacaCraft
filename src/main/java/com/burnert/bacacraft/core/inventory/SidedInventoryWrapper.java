package com.burnert.bacacraft.core.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SidedInventoryWrapper implements IItemHandlerModifiable {

    private ISidedInventoryAdvanced inventory;
    private EnumFacing facing;

    public SidedInventoryWrapper(ISidedInventoryAdvanced inv, EnumFacing side) {
        this.inventory = inv;
        this.facing = side;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SidedInventoryWrapper self = (SidedInventoryWrapper) o;

        return inventory.equals(self.inventory) && facing == self.facing;
    }

    @Override
    public int hashCode() {
        int result = inventory.hashCode();
        result = 31 * result + Objects.hashCode(facing);
        return result;
    }

    @Override
    public int getSlots() {
        return inventory.getSlotsForFace(facing).length;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        int i = getSlot(inventory, slot, facing);
        return i == -1 ? ItemStack.EMPTY : inventory.getStackInSlot(i);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        int slot1 = getSlot(inventory, slot, facing);

        if (slot1 != -1)
            setInventorySlotContents(slot1, stack);
    }

    public static int getSlot(ISidedInventoryAdvanced inv, int slot, EnumFacing side) {
        int[] slots = inv.getSlotsForFace(side);
        if (slot < slots.length)
            return slots[slot];
        return -1;
    }

    private void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.markDirty();
        inventory.setInventorySlotContents(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        int slot1 = getSlot(inventory, slot, facing);

        if (slot1 == -1)
            return stack;

        ItemStack stackInSlot = inventory.getStackInSlot(slot1);

        int m;
        if (!stackInSlot.isEmpty()) {
            if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getSlotLimit(slot)))
                return stack;

            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            if (!inventory.canInsertItem(slot1, stack, facing) || !inventory.isItemValidForSlot(slot1, stack))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - stackInSlot.getCount();

            if (stack.getCount() <= m) {
                if (!simulate) {
                    ItemStack copy = stack.copy();
                    copy.grow(stackInSlot.getCount());
                    setInventorySlotContents(slot1, copy);
                }

                return ItemStack.EMPTY;
            }
            else {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    ItemStack copy = stack.splitStack(m);
                    copy.grow(stackInSlot.getCount());
                    setInventorySlotContents(slot1, copy);
                    return stack;
                }
                else {
                    stack.shrink(m);
                    return stack;
                }
            }
        }
        else {
            if (!inventory.canInsertItem(slot1, stack, facing) || !inventory.isItemValidForSlot(slot1, stack))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
            if (m < stack.getCount()) {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    setInventorySlotContents(slot1, stack.splitStack(m));
                    return stack;
                }
                else {
                    stack.shrink(m);
                    return stack;
                }
            }
            else
            {
                if (!simulate)
                    setInventorySlotContents(slot1, stack);
                return ItemStack.EMPTY;
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        int slot1 = getSlot(inventory, slot, facing);

        if (slot1 == -1)
            return ItemStack.EMPTY;

        ItemStack stackInSlot = inventory.getStackInSlot(slot1);

        if (stackInSlot.isEmpty())
            return ItemStack.EMPTY;

        if (!inventory.canExtractItem(slot1, stackInSlot, facing))
            return ItemStack.EMPTY;

        if (simulate) {
            if (stackInSlot.getCount() < amount) {
                return stackInSlot.copy();
            }
            else {
                ItemStack copy = stackInSlot.copy();
                copy.setCount(amount);
                return copy;
            }
        }
        else {
            int m = Math.min(stackInSlot.getCount(), amount);
            ItemStack ret = inventory.decrStackSize(slot1, m);
            inventory.markDirty();
            return ret;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getSlotStackLimit(slot);
    }
}
