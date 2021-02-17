package com.burnert.bacacraft.item;

import com.burnert.bacacraft.core.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.item.CoreItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;

public class ItemDevItem extends CoreItem {

	public ItemDevItem() {
		super("mountain_cheese_devitem");
		this.setHasSubtypes(true);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}

	@Override
	protected int[] getAvailableSubtypeMetadata() {
		return new int[]{ 0, 1, 2 };
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
			items.add(new ItemStack(this, 1, 1));
			items.add(new ItemStack(this, 1, 2));
		}
	}

	public static void useItemOnWorld(World worldIn, BlockPos blockPos, EntityPlayer player, ItemStack itemStack) {
		TileEntity tileEntity = worldIn.getTileEntity(blockPos);
		// Generate loot tables based on dev item's metadata
		if (tileEntity instanceof TileEntityLockableLoot) {
			if (!worldIn.isRemote) {
				TileEntityLockableLoot tileEntityLoot = (TileEntityLockableLoot)tileEntity;
				ResourceLocation lootTableLocation = LootTableList.CHESTS_VILLAGE_BLACKSMITH;
				switch (itemStack.getMetadata()) {
					case 1:
						lootTableLocation = LootTableList.CHESTS_ABANDONED_MINESHAFT;
						break;
					case 2:
						lootTableLocation = LootTableList.CHESTS_SIMPLE_DUNGEON;
						break;
				}
				tileEntityLoot.setLootTable(lootTableLocation, worldIn.rand.nextLong());
				tileEntityLoot.fillWithLoot(player);
			}
		}
	}
}
