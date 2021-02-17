package com.burnert.bacacraft.core.event;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.item.ItemDevItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
public class PlayerEventHandler {
	@Mod.EventBusSubscriber
	@SuppressWarnings("unused")
	public static class ItemEventHandler {
		@SubscribeEvent
		@SuppressWarnings("unused")
		public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		}

		@SubscribeEvent
		@SuppressWarnings("unused")
		public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
			if (event.getItemStack().getItem().equals(BacaCraftItemRegistry.DEV_ITEM)) {
				BlockPos pos = event.getPos();
				BacaCraft.LOGGER.info("Used Dev Item on: " + event.getWorld().getBlockState(pos) + " : " + pos);
				ItemDevItem.useItemOnWorld(event.getWorld(), pos, event.getEntityPlayer(), event.getItemStack());
			}
		}
	}
}
