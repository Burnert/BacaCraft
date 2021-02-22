package com.burnert.bacacraft.core.property;

import javax.annotation.Nonnull;

public class NBTPropertyInt extends NBTProperty {

	private int value;

	public NBTPropertyInt(String name) {
		super(name);
	}
	public NBTPropertyInt(String name, NBTPropertyAttribute... attributes) {
		super(name, attributes);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.set = true;
		this.value = value;
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.INT;
	}
}
