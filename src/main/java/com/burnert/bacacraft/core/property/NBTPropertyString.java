package com.burnert.bacacraft.core.property;

import javax.annotation.Nonnull;

public class NBTPropertyString extends NBTProperty {

	private String value;

	public NBTPropertyString(String name) {
		super(name);
	}

	public NBTPropertyString(String name, boolean sendToClient) {
		super(name, sendToClient);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.STRING;
	}
}
