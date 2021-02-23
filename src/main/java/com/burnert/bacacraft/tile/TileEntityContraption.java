package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.property.tile.NBTProperty.NBTPropertyAttribute;
import com.burnert.bacacraft.core.property.tile.NBTPropertyInt;
import com.burnert.bacacraft.core.property.tile.NBTPropertyString;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	public NBTPropertyString customNameProperty = new NBTPropertyString("CustomName",
			NBTPropertyAttribute.SEND_TO_CLIENT,
			NBTPropertyAttribute.DISPLAY_NAME);
	public NBTPropertyInt facingProperty = new NBTPropertyInt("Facing",
			NBTPropertyAttribute.SEND_TO_CLIENT);

	public void setFacing(EnumFacing facing) {
		this.facingProperty.setValue(facing.getHorizontalIndex());
	}

	public EnumFacing getFacing() {
		return EnumFacing.getHorizontal(this.facingProperty.getValue());
	}

	// ITileNameable:

	@Override
	public void setCustomName(String name) {
		if (!name.isEmpty()) {
			this.customNameProperty.setValue(name);
		}
	}

	@Override
	public String getCustomName() {
		return this.customNameProperty.getValue();
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
