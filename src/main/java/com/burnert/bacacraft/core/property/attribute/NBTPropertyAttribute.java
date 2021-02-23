package com.burnert.bacacraft.core.property.attribute;

public class NBTPropertyAttribute {

	public NBTPropertyAttribute(EnumAttributeType type) {
		this.type = type;
	}

	public EnumAttributeType getType() {
		return this.type;
	}

	private final EnumAttributeType type;
}
