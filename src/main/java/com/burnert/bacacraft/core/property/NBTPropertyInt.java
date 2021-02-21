package com.burnert.bacacraft.core.property;

import javax.annotation.Nonnull;

public class NBTPropertyInt extends NBTProperty {

	private int value;

	public NBTPropertyInt(String name) {
		super(name);
	}

	public NBTPropertyInt(String name, boolean sendToClient) {
		super(name, sendToClient);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.INT;
	}
}
