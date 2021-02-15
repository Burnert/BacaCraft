package com.burnert.bacacraft.core;

import com.burnert.bacacraft.item.*;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BacaCraftItemRegistry {
	public static final List<Item> itemList = new ArrayList<>();

	public static final ItemMountainCheese MOUNTAIN_CHEESE = new ItemMountainCheese();
	public static final ItemMountainCheeseSmoked MOUNTAIN_CHEESE_SMOKED = new ItemMountainCheeseSmoked();
	public static final ItemMountainCheeseOversmoked MOUNTAIN_CHEESE_OVERSMOKED = new ItemMountainCheeseOversmoked();
	public static final ItemMountainCheeseForm MOUNTAIN_CHEESE_FORM = new ItemMountainCheeseForm();
	public static final ItemDevItem DEV_ITEM = new ItemDevItem();

	static {

	}
}
