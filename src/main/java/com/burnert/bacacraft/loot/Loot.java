package com.burnert.bacacraft.loot;

import com.burnert.bacacraft.core.BacaCraftBlockRegistry;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.config.BacaCraftReference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = BacaCraftReference.MOD_ID)
@SuppressWarnings("unused")
public class Loot {

    public static final LootEntry[] NO_ENTRIES = new LootEntry[0];
    public static final LootCondition[] NO_CONDITION = new LootCondition[0];

    private @Nonnull static LootEntry createLootEntry(@Nonnull Item item, int minStackSize, int maxStackSize, float chance) {
        return createLootEntry(item, 0, minStackSize, maxStackSize, chance);
    }

    private @Nonnull static LootEntry createLootEntry(@Nonnull Item item, int meta, int minStackSize, int maxStackSize, float chance) {
        LootCondition[] lootCondition = new LootCondition[]{ new RandomChance(chance) };
        final ResourceLocation registryName = item.getRegistryName();

        if (registryName == null)
            throw new NullPointerException("Item hasn't been registered!");

        return new LootEntryItem(item, 1, 1,
                new LootFunction[]{
                        new SetCount(NO_CONDITION, new RandomValueRange(minStackSize, maxStackSize)),
                        new SetMetadata(NO_CONDITION, new RandomValueRange(meta, meta))
                }, lootCondition, registryName.toString() + ":" + meta);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLootTableLoad(@Nonnull LootTableLoadEvent event) {
        LootTable lootTable = event.getTable();
        LootPool lootPool = new LootPool(NO_ENTRIES, NO_CONDITION, new RandomValueRange(0, 3), new RandomValueRange(0, 0), BacaCraftReference.MOD_ID);
        if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_FORM, 1, 1, 1.0f));
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_SMOKED, 1, 3, 1.0f));
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_OVERSMOKED, 3, 9, 1.0f));
        }
        else if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_FORM, 1, 1, 1.0f));
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_OVERSMOKED, 2, 8, 1.0f));
        }
        else if (event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)) {
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_FORM, 1, 1, 1.0f));
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE_SMOKED, 1, 4, 1.0f));
            lootPool.addEntry(createLootEntry(BacaCraftItemRegistry.MOUNTAIN_CHEESE, 1, 4, 1.0f));
        }
        else {
            return;
        }

        if (lootTable.isFrozen()) {
            throw new RuntimeException("Loot table is frozen!");
        }

        lootTable.addPool(lootPool);
    }

}
