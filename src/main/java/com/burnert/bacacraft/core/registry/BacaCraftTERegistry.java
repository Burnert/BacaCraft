package com.burnert.bacacraft.core.registry;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BacaCraftTERegistry {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySmokehouse.class, new ResourceLocation(BacaCraft.MOD_ID, "smokehouse"));
	}
}
