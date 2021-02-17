package com.burnert.bacacraft.inventory;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import com.burnert.bacacraft.core.util.ContainerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSmokehouse extends Container {

	private final TileEntitySmokehouse tileSmokehouse;

	// Fuel burn time
	private int smokehouseBurnTime;
	private int currentItemBurnTime;
	// Array of slots smoking times
	private int[] smokeTime = new int[8];
	private int[] totalSmokeTime = new int[8];

	public ContainerSmokehouse(InventoryPlayer inventoryPlayer, TileEntitySmokehouse smokehouseInventory) {
		this.tileSmokehouse = smokehouseInventory;
		// Fuel
		this.addSlotToContainer(new SlotSmokehouseFuel(smokehouseInventory, 0, 80, 62));
		// Smoke slots
		for (int i = 0; i < 8; ++i) {
			this.addSlotToContainer(new SlotSmokehouse(smokehouseInventory, i + 1, 17 + i * 18, 20));
		}
		// Player Inventory
		for (Slot slot : ContainerHelper.createStandardPlayerSlots(inventoryPlayer, 8, 93)) {
			this.addSlotToContainer(slot);
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileSmokehouse);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener iContainerListener = this.listeners.get(i);

			if (this.smokehouseBurnTime != this.tileSmokehouse.getField(0)) {
				iContainerListener.sendWindowProperty(this, 0, this.tileSmokehouse.getField(0));
			}

			if (this.currentItemBurnTime != this.tileSmokehouse.getField(1)) {
				iContainerListener.sendWindowProperty(this, 1, this.tileSmokehouse.getField(1));
			}

			for (int sti = 0; sti < this.smokeTime.length; ++sti) {
				if (this.smokeTime[sti] != this.tileSmokehouse.getField(2 + sti)) {
					iContainerListener.sendWindowProperty(this, 2 + sti, this.tileSmokehouse.getField(2 + sti));
				}
			}

			for (int tsti = 0; tsti < this.totalSmokeTime.length; ++tsti) {
				if (this.totalSmokeTime[tsti] != this.tileSmokehouse.getField(10 + tsti)) {
					iContainerListener.sendWindowProperty(this, 10  + tsti, this.tileSmokehouse.getField(10  + tsti));
				}
			}
		}

		this.smokehouseBurnTime = this.tileSmokehouse.getField(0);
		this.currentItemBurnTime = this.tileSmokehouse.getField(1);
		for (int sti = 0; sti < this.smokeTime.length; ++sti) {
			this.smokeTime[sti] = this.tileSmokehouse.getField(2 + sti);
		}
		for (int tsti = 0; tsti < this.totalSmokeTime.length; ++tsti) {
			this.totalSmokeTime[tsti] = this.tileSmokehouse.getField(10 + tsti);
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileSmokehouse.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileSmokehouse.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		BacaCraft.LOGGER.info("Slot#: " + slot.slotNumber + " index: " + index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// Smokehouse slots
			if (index < 9) {
				BacaCraft.LOGGER.info("From smokehouse");
				if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			// Player inventory
			else {
				BacaCraft.LOGGER.info("From inventory");
				if (itemstack1.getItem() == BacaCraftItemRegistry.MOUNTAIN_CHEESE) {
					if (!this.mergeItemStack(itemstack1, 1, 9, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntitySmokehouse.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}
