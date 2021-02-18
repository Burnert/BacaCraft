package com.burnert.bacacraft.item;

import com.burnert.bacacraft.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.item.ItemCore;

public class ItemMountainCheeseForm extends ItemCore {
	public ItemMountainCheeseForm() {
		super("mountain_cheese_form");
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
	}
}
