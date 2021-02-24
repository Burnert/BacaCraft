package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;

import javax.annotation.Nonnull;

public class NBTPropertyString extends NBTProperty<String> {

	public NBTPropertyString(String name) {
		this(name, "", new NBTPropertyAttribute[0]);
	}

	public NBTPropertyString(String name, String defaultValue) {
		super(name, defaultValue);
	}

	public NBTPropertyString(String name, NBTPropertyAttribute... attributes) {
		this(name, "", attributes);
	}

	public NBTPropertyString(String name, String defaultValue, NBTPropertyAttribute... attributes) {
		super(name, defaultValue, attributes);
	}

//	public String getValue() {
//		return this.value;
//	}
//
//	public void setValue(String value) {
//		this.set = true;
//		this.value = value;
//		this.updateTileEntity();
//	}

	@Nonnull
	@Override
	public EnumNBTPropertyType getTagType() {
		return EnumNBTPropertyType.STRING;
	}
}
