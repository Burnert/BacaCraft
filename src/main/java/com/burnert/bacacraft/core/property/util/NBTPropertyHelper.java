package com.burnert.bacacraft.core.property.util;

import com.burnert.bacacraft.core.property.tile.NBTProperty;
import net.minecraft.nbt.NBTTagCompound;

public class NBTPropertyHelper {

	public static void readNBTProperty(NBTProperty property, NBTTagCompound compound) {
		switch (property.getTagType()) {
			case BYTE:
				property.setByteValue(compound.getByte(property.getName()));
				break;
			case INT:
				property.setIntValue(compound.getInteger(property.getName()));
				break;
			case STRING:
				property.setStringValue(compound.getString(property.getName()));
				break;
		}
	}

	public static void writeNBTProperty(NBTProperty property, NBTTagCompound compound) {
		switch (property.getTagType()) {
			case BYTE:
				compound.setByte(property.getName(), property.getByteValue());
				break;
			case INT:
				compound.setInteger(property.getName(), property.getIntValue());
				break;
			case STRING:
				compound.setString(property.getName(), property.getStringValue());
				break;
		}
	}

	private NBTPropertyHelper() { }
}
