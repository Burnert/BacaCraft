package com.burnert.bacacraft.core.property;

import com.burnert.bacacraft.BacaCraft;

import javax.annotation.Nonnull;

public abstract class NBTProperty {

	// TODO: Add all the NBT types
	
	private final String name;
	private boolean sendToClient = false;
	private boolean set;

	public NBTProperty(String name) {
		this.name = name;
	}

	public NBTProperty(String name, boolean sendToClient) {
		this(name);
		this.setSendToClient(sendToClient);
	}

	public static EnumNBTPropertyType getTypeFromId(byte id) {
		return EnumNBTPropertyType.values()[id];
	}

	public boolean isSet() {
		return this.set;
	}

	// Property Value Getters:

	public byte getByteValue() {
		if (this.set && this instanceof NBTPropertyByte) {
			return ((NBTPropertyByte)this).getValue();
		}
		if (this.set)
			BacaCraft.LOGGER.error("Cannot read property " + this.getName() + " because it is not of type Byte!");
		return 0;
	}

	public int getIntValue() {
		if (this.set && this instanceof NBTPropertyInt) {
			return ((NBTPropertyInt)this).getValue();
		}
		if (this.set)
			BacaCraft.LOGGER.error("Cannot read property " + this.getName() + " because it is not of type Int!");
		return 0;
	}

	public String getStringValue() {
		if (this.set && this instanceof NBTPropertyString) {
			return ((NBTPropertyString)this).getValue();
		}
		if (this.set)
			BacaCraft.LOGGER.error("Cannot read property " + this.getName() + " because it is not of type String!");
		return "";
	}

	// Property Value Setters:

	public void setByteValue(byte value) {
		this.set = true;
		if (this instanceof NBTPropertyByte) {
			((NBTPropertyByte)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not an Integer!");
		}
	}

	public void setIntValue(int value) {
		this.set = true;
		if (this instanceof NBTPropertyInt) {
			((NBTPropertyInt)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not an Integer!");
		}
	}

	public void setStringValue(String value) {
		this.set = true;
		if (this instanceof NBTPropertyString) {
			((NBTPropertyString)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not a String!");
		}
	}


	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public abstract EnumNBTPropertyType getTagType();

	public void setSendToClient(boolean sendToClient) {
		this.sendToClient = sendToClient;
	}

	public boolean shouldSendToClient() {
		return this.sendToClient;
	}
}
