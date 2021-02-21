package com.burnert.bacacraft.core.property;

import javax.annotation.Nonnull;

public class NBTPropertyByte extends NBTProperty {

	private byte value;

	public NBTPropertyByte(String name) {
		super(name);
	}

	public NBTPropertyByte(String name, boolean sendToClient) {
		super(name, sendToClient);
	}

	public byte getValue() {
		return this.value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BYTE;
	}
}
