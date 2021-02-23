package com.burnert.bacacraft.core.property.tile;

import javax.annotation.Nonnull;

public class NBTPropertyByte extends NBTProperty {

	private byte value;

	public NBTPropertyByte(String name) {
		super(name);
	}
	public NBTPropertyByte(String name, NBTPropertyAttribute... attributes) {
		super(name, attributes);
	}

	public byte getValue() {
		return this.value;
	}

	public void setValue(byte value) {
		this.set = true;
		this.value = value;
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BYTE;
	}
}
