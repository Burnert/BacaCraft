package com.burnert.bacacraft.core.property;

import javax.annotation.Nonnull;

public class NBTPropertyNull extends NBTProperty {

	public NBTPropertyNull() {
		super("", false);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.NULL;
	}
}
