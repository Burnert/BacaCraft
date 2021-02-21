package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.property.NBTPropertyInt;
import com.burnert.bacacraft.core.property.NBTPropertyString;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	public NBTPropertyString customNameProperty = new NBTPropertyString("CustomName");
	public NBTPropertyInt facingProperty = new NBTPropertyInt("Facing", true);

	public void setFacing(EnumFacing facing) {
		this.facingProperty.setIntValue(facing.getHorizontalIndex());
	}

	public EnumFacing getFacing() {
		return EnumFacing.getHorizontal(this.facingProperty.getIntValue());
	}

	// ITileNameable:

	@Override
	public void setCustomName(String name) {
		if (!name.isEmpty()) {
			this.customNameProperty.setStringValue(name);
		}
	}

	@Override
	public String getCustomName() {
		return this.customNameProperty.getStringValue();
	}

	@Override
	public boolean hasCustomName() {
		return this.customNameProperty.isSet();
	}

	// End of ITileNameable

	// TileEntityCore:

	@Override
	public void createNBTProperties() {
		this.addNBTProperty(customNameProperty);
		this.addNBTProperty(facingProperty);
	}

	// End ot TileEntityCore
}
