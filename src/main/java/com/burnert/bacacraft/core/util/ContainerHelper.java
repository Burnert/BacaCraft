package com.burnert.bacacraft.core.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.util.LinkedList;
import java.util.List;

public final class ContainerHelper {

	private ContainerHelper() { }

	public static List<Slot> createStandardPlayerSlots(InventoryPlayer inventoryPlayer, int offsetX, int offsetY) {
		List<Slot> slots = new LinkedList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				slots.add(new Slot(inventoryPlayer, j + i * 9 + 9, offsetX + j * 18, offsetY + i * 18));
			}
		}
		for (int k = 0; k < 9; ++k) {
			slots.add(new Slot(inventoryPlayer, k, offsetX + k * 18, offsetY + 58));
		}
		return slots;
	}
}
