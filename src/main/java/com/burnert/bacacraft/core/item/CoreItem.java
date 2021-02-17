package com.burnert.bacacraft.core.item;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.util.IItemModelRegister;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CoreItem extends Item implements IItemModelRegister {

	public CoreItem(String name) {
		this.setUnlocalizedName(BacaCraft.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraft.MOD_ID + ":" + name));

		BacaCraftItemRegistry.itemList.add(this);
	}

	protected int[] getAvailableSubtypeMetadata() {
		return new int[0];
	}

	@Override
	public void registerModel() {
		int[] subtypes = this.getAvailableSubtypeMetadata();
		if (subtypes.length > 0) {
			for (int type : subtypes) {
				BacaCraft.proxy.registerItemRenderer(this, type, "inventory");
			}
		}
		else {
			BacaCraft.proxy.registerItemRenderer(this, 0, "inventory");
		}
	}
}
