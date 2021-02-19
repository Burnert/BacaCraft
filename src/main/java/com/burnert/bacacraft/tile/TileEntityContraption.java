package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	private String customName;

	// ITileNameable:

	@Override
	public void setCustomName(String name) {
		if (!name.isEmpty())
			this.customName = name;
	}

	@Override
	public String getCustomName() {
		return this.customName;
	}

	// End of ITileNameable
}
