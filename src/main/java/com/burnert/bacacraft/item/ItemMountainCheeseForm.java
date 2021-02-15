package com.burnert.bacacraft.item;

import com.burnert.bacacraft.core.BacaCraftCreativeTabs;

public class ItemMountainCheeseForm extends AbstractItemBase {
	public ItemMountainCheeseForm() {
		super("mountain_cheese_form");
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
		this.setMaxStackSize(1);
		this.setContainerItem(this);
	}
}
