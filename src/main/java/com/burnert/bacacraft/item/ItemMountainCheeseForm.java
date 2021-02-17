package com.burnert.bacacraft.item;

import com.burnert.bacacraft.core.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.item.CoreItem;

public class ItemMountainCheeseForm extends CoreItem {
	public ItemMountainCheeseForm() {
		super("mountain_cheese_form");
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
	}
}
