package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.core.property.attribute.AttributeLinkedToState;
import com.burnert.bacacraft.core.property.attribute.EnumAttributeType;
import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;
import com.burnert.bacacraft.core.property.tile.NBTPropertyBoolean;
import com.burnert.bacacraft.core.property.tile.NBTPropertyContainer;
import com.burnert.bacacraft.core.property.tile.NBTPropertyInt;
import com.burnert.bacacraft.core.property.tile.NBTPropertyString;
import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	private NBTPropertyString customNameProperty = new NBTPropertyString("CustomName",
			new NBTPropertyAttribute(EnumAttributeType.SEND_TO_CLIENT),
			new NBTPropertyAttribute(EnumAttributeType.DISPLAY_NAME));

	private NBTPropertyInt facingProperty = new NBTPropertyInt("Facing",
			new NBTPropertyAttribute(EnumAttributeType.SEND_TO_CLIENT),
			new AttributeLinkedToState<>(BlockContraption.FACING,
					(state, nbtProperty) -> state.withProperty(
							BlockContraption.FACING,
							EnumFacing.getHorizontal(nbtProperty.getIntValue()))));

	private NBTPropertyBoolean activeProperty = new NBTPropertyBoolean("Active",
			new NBTPropertyAttribute(EnumAttributeType.SEND_TO_CLIENT),
			new AttributeLinkedToState<>(BlockContraption.ACTIVE));


	public void setFacing(EnumFacing facing) {
		this.facingProperty.setValue(facing.getHorizontalIndex());
	}

	public EnumFacing getFacing() {
		return EnumFacing.getHorizontal(this.facingProperty.getValue());
	}

	public void setActive(boolean active) {
		this.activeProperty.setValue(active);
	}

	public boolean isActive() {
		return this.activeProperty.getValue();
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
	public NBTPropertyContainer createNBTProperties() {
		return new NBTPropertyContainer(customNameProperty, facingProperty, activeProperty);
	}

	// End ot TileEntityCore
}
