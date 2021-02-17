package com.burnert.bacacraft.item;

import com.burnert.bacacraft.core.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.item.CoreItemFood;

public class ItemMountainCheese extends CoreItemFood {
	public ItemMountainCheese() {
		super("mountain_cheese", 3, 0.4f);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}
}
