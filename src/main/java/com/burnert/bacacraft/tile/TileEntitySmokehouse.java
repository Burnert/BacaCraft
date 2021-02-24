package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.inventory.IGuiProvider;
import com.burnert.bacacraft.core.property.tile.NBTPropertyContainer;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntitySmokehouse extends TileEntityContraption implements IGuiProvider {

	// TileEntityCore:

	@Override
	public NBTPropertyContainer createNBTProperties() {
		return new NBTPropertyContainer(super.createNBTProperties());
	}

	// End ot TileEntityCore

	// IGuiProvider:

	@Override
	public boolean openGui(EntityPlayer player) {
		// TODO: Fix this obviously
		// InventoryHelper.openGui(this, player, BacaCraft.GUI_SMOKEHOUSE);
		return true;
	}

	// End of IGuiProvider
}
