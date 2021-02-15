package com.burnert.bacacraft.core;

import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BacaCraftTERegistry {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySmokehouse.class, new ResourceLocation(BacaCraftReference.MOD_ID, "smokehouse"));
	}
}
