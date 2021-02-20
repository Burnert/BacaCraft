package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.inventory.IGuiProvider;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityCooker extends TileEntityContraption implements IGuiProvider {

	// IGuiProvider:

	@Override
	public boolean openGui(EntityPlayer player) {
		return false;
	}

	// End of IGuiProvider
}
