package com.burnert.bacacraft.core.item;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.registry.IModelRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

public class ItemFoodCore extends ItemFood implements IModelRegister {

	public ItemFoodCore(String name, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(BacaCraft.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraft.MOD_ID + ":" + name));

		BacaCraftItemRegistry.itemList.add(this);
	}

	public ItemFoodCore(String name, int amount, float saturation) {
		this(name, amount, saturation, false);
	}

	@Override
	public void registerModel() {
		BacaCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
