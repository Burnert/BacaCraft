package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyInt extends NBTProperty<Integer> {

	public NBTPropertyInt(String name) {
		this(name, 0, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyInt(String name, int defaultValue) {
		super(name, defaultValue);
	}

	public NBTPropertyInt(String name, NBTPropertyAttribute... attributes) {
		this(name, 0, attributes);
	}

	public NBTPropertyInt(String name, int defaultValue, NBTPropertyAttribute... attributes) {
		super(name, defaultValue, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.INT;
	}
}
