package com.burnert.bacacraft.util;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.BacaCraftBlockRegistry;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.item.ItemDevItem;
import com.burnert.bacacraft.item.ItemMountainCheese;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
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
//			final Item itemCrafted = event.crafting.getItem();
//			final EntityPlayer player = event.player;
//
//			if (!player.world.isRemote) {
//				// Drop BlockOscypek when Oscypek is crafted.
//				if (itemCrafted instanceof ItemMountainCheese) {
//					System.out.println("test");
//					InventoryHelper.spawnItemStack(player.getEntityWorld(), player.posX, player.posY, player.posZ, new ItemStack(BacaCraftBlockRegistry.MOUNTAIN_CHEESE_BLOCK));
//				}
//			}
		}

		@SubscribeEvent
		@SuppressWarnings("unused")
		public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
			if (event.getItemStack().getItem().equals(BacaCraftItemRegistry.DEV_ITEM)) {
				BlockPos pos = event.getPos();
				//event.setUseBlock(Event.Result.DENY);
				//event.setUseItem(Event.Result.DENY);
				BacaCraft.LOGGER.info("Used Dev Item on: " + event.getWorld().getBlockState(pos) + " : " + pos);
				ItemDevItem.useItemOnWorld(event.getWorld(), pos, event.getEntityPlayer(), event.getItemStack());
			}
		}
	}
}
