package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyBoolean extends NBTProperty<Boolean> {

	public NBTPropertyBoolean(String name) {
		super(name, false, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyBoolean(String name, NBTPropertyAttribute... attributes) {
		super(name, false, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BOOLEAN;
	}
}
