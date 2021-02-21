package com.burnert.bacacraft.core.proxy;

import com.burnert.bacacraft.BacaCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id, String resourceName) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(BacaCraft.MOD_ID + ":" + resourceName, id));
	}
}
