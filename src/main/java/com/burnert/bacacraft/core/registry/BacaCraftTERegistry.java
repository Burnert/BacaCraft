package com.burnert.bacacraft.core.registry;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.tile.TileEntityCooker;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import com.burnert.bacacraft.tile.TileEntitySmokehouseLegacy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BacaCraftTERegistry {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySmokehouseLegacy.class, new ResourceLocation(BacaCraft.MOD_ID, "smokehouse_legacy"));
		GameRegistry.registerTileEntity(TileEntitySmokehouse.class, new ResourceLocation(BacaCraft.MOD_ID, "smokehouse"));
		GameRegistry.registerTileEntity(TileEntityCooker.class, new ResourceLocation(BacaCraft.MOD_ID, "cooker"));
	}
}
