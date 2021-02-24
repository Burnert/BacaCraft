package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.core.property.attribute.*;
import com.burnert.bacacraft.core.property.tile.NBTPropertyBoolean;
import com.burnert.bacacraft.core.property.tile.NBTPropertyContainer;
import com.burnert.bacacraft.core.property.tile.NBTPropertyInt;
import com.burnert.bacacraft.core.property.tile.NBTPropertyString;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	private NBTPropertyString propertyCustomName = new NBTPropertyString("CustomName",
			new NBTPropertyAttribute.SendToClient(),
					new NBTPropertyAttribute.DisplayName());

	private NBTPropertyInt propertyFacing = new NBTPropertyInt("Facing",
			new NBTPropertyAttribute.SendToClient(),
			new NBTPropertyAttribute.LinkedToState<>(BlockContraption.FACING,
					nbtProperty -> EnumFacing.getHorizontal(nbtProperty.getIntValue())));

	private NBTPropertyBoolean propertyActive = new NBTPropertyBoolean("Active",
			new NBTPropertyAttribute.SendToClient(),
			new NBTPropertyAttribute.LinkedToState<>(BlockContraption.ACTIVE));


	public NBTPropertyString getPropertyCustomName() {
		return this.propertyCustomName;
	}

	public NBTPropertyInt getPropertyFacing() {
		return this.propertyFacing;
	}

	public NBTPropertyBoolean getPropertyActive() {
		return this.propertyActive;
	}

	public void setFacing(EnumFacing facing) {
		this.propertyFacing.setValue(facing.getHorizontalIndex());
	}

	public EnumFacing getFacing() {
		return EnumFacing.getHorizontal(this.propertyFacing.getValue());
	}

	public void setActive(boolean active) {
		this.propertyActive.setValue(active);
	}

	public boolean isActive() {
		return this.propertyActive.getValue();
	}

	// ITileNameable:

	@Override
	public void setCustomName(String name) {
		if (!name.isEmpty()) {
			this.propertyCustomName.setValue(name);
		}
	}

	@Override
	public String getCustomName() {
		return this.propertyCustomName.getValue();
	}

	@Override
	public boolean hasCustomName() {
		return this.propertyCustomName.isSet();
	}

	// End of ITileNameable

	// TileEntityCore:

	@Override
	public NBTPropertyContainer createNBTProperties() {
		return new NBTPropertyContainer(propertyCustomName, propertyFacing, propertyActive);
	}

	// End ot TileEntityCore
}
