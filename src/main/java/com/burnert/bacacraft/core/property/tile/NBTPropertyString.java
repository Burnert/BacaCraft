package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyString extends NBTProperty<String> {

	public NBTPropertyString(String name) {
		super(name, "", new NBTPropertyAttribute[0]);
	}

	public NBTPropertyString(String name, NBTPropertyAttribute... attributes) {
		super(name, "", attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.STRING;
	}
}
