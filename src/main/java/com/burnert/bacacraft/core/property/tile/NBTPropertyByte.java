package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyByte extends NBTProperty<Byte> {

	public NBTPropertyByte(String name) {
		super(name, (byte)0, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyByte(String name, NBTPropertyAttribute... attributes) {
		super(name, (byte)0, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BYTE;
	}
}
