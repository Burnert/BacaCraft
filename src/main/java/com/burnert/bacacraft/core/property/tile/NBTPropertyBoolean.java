package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyBoolean extends NBTProperty<Boolean> {

	public NBTPropertyBoolean(String name) {
		this(name, false, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyBoolean(String name, boolean defaultValue) {
		super(name, defaultValue);
	}

	public NBTPropertyBoolean(String name, NBTPropertyAttribute... attributes) {
		this(name, false, attributes);
	}

	public NBTPropertyBoolean(String name, boolean defaultValue, NBTPropertyAttribute... attributes) {
		super(name, defaultValue, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BOOLEAN;
	}
}
