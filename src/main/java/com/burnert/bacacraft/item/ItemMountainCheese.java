package com.burnert.bacacraft.item;

import com.burnert.bacacraft.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.item.ItemFoodCore;

public class ItemMountainCheese extends ItemFoodCore {
	public ItemMountainCheese() {
		super("mountain_cheese", 3, 0.4f);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}
}
