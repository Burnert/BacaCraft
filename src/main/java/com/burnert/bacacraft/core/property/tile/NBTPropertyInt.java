package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyInt extends NBTProperty<Integer> {

	public NBTPropertyInt(String name) {
		super(name, 0, new NBTPropertyAttribute[0]);
	}

	public NBTPropertyInt(String name, NBTPropertyAttribute... attributes) {
		super(name, 0, attributes);
	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.INT;
	}
}
