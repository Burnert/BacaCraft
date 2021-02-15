package com.burnert.bacacraft.item;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.util.IItemModelRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractItemFoodBase extends ItemFood implements IItemModelRegister {

	public AbstractItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(BacaCraftReference.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraftReference.MOD_ID + ":" + name));

		BacaCraftItemRegistry.itemList.add(this);
	}

	public AbstractItemFoodBase(String name, int amount, float saturation) {
		this(name, amount, saturation, false);
	}

	@Override
	public void registerModel() {
		BacaCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
