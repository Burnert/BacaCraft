package com.burnert.bacacraft.util;

import com.burnert.bacacraft.core.BacaCraftBlockRegistry;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryEventHandler {
	@SubscribeEvent
	public static void onBlocksRegister(final RegistryEvent.Register<Block> event) {
		for (Block block : BacaCraftBlockRegistry.blockList) {
			event.getRegistry().register(block);
		}
	}

	@SubscribeEvent
	public static void onItemsRegister(final RegistryEvent.Register<Item> event) {
		for (Item item : BacaCraftItemRegistry.itemList) {
			event.getRegistry().register(item);
		}
	}

	private static void registerModel(Object obj) {
		if (obj instanceof IItemModelRegister) {
			((IItemModelRegister) obj).registerModel();
		}
	}

	@SubscribeEvent
	public static void onModelRegister(final ModelRegistryEvent event) {
		for (Item item : BacaCraftItemRegistry.itemList) registerModel(item);
		for (Block block : BacaCraftBlockRegistry.blockList) registerModel(block);
	}
}