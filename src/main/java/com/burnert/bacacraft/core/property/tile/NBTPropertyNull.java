package com.burnert.bacacraft.core.property.tile;

import javax.annotation.Nonnull;

public class NBTPropertyNull extends NBTProperty {

	public NBTPropertyNull() {
		super("");
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.NULL;
	}
}
