package com.burnert.bacacraft.core;

import com.burnert.bacacraft.core.config.BacaCraftReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BacaCraftCreativeTabs {
	private BacaCraftCreativeTabs() { }

	public static final CreativeTabs MAIN = new CreativeTabs(BacaCraftReference.MOD_ID) {
		@Override
		@Nonnull
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BacaCraftBlockRegistry.SMOKEHOUSE), 1);
		}

//		@Override
//		@Nonnull
//		@SideOnly(Side.CLIENT)
//		public ItemStack getTabIconItem() {
//			return new ItemStack(Item.getItemFromBlock(BacaCraftBlockRegistry.SMOKEHOUSE), 1);
//		}
	};
}
