package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.inventory.IGuiProvider;
import com.burnert.bacacraft.core.util.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntitySmokehouse extends TileEntityContraption implements IGuiProvider {

	public TileEntitySmokehouse() {
		super();
		BacaCraft.LOGGER.info("TileEntitySmokehouse constructed!");
	}

	// IGuiProvider:

	@Override
	public boolean openGui(EntityPlayer player) {
		// TODO: Fix this obviously
		// InventoryHelper.openGui(this, player, BacaCraft.GUI_SMOKEHOUSE);
		return true;
	}

	// End of IGuiProvider
}
