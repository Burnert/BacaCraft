package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyBoolean extends NBTProperty {

	private boolean value;

	public NBTPropertyBoolean(String name) {
		super(name);
	}
	public NBTPropertyBoolean(String name, NBTPropertyAttribute... attributes) {
		super(name, attributes);
	}

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.set = true;
		this.value = value;
		this.updateTileEntity();
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BOOLEAN;
	}
}
