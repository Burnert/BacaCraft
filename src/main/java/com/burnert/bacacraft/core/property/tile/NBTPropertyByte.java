package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyByte extends NBTProperty<Byte> {

	public NBTPropertyByte(String name) {
		this(name, (byte)0, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyByte(String name, byte defaultValue) {
		super(name, defaultValue);
	}

	public NBTPropertyByte(String name, NBTPropertyAttribute... attributes) {
		this(name, (byte)0, attributes);
	}

	public NBTPropertyByte(String name, byte defaultValue, NBTPropertyAttribute... attributes) {
		super(name, defaultValue, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.BYTE;
	}
}
