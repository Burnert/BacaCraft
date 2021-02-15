package com.burnert.bacacraft.item;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.util.IItemModelRegister;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractItemBase extends Item implements IItemModelRegister {

	public AbstractItemBase(String name) {
		this.setUnlocalizedName(BacaCraftReference.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraftReference.MOD_ID + ":" + name));

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
