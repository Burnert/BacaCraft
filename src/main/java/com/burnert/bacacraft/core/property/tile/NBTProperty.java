package com.burnert.bacacraft.core.property.tile;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.property.attribute.EnumAttributeType;
import com.burnert.bacacraft.core.property.attribute.NBTPropertyAttribute;
import com.burnert.bacacraft.core.property.util.PropertyLinker;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public abstract class NBTProperty<T extends Comparable<T>> {

	// TODO: Add all the NBT types
	
	public NBTProperty(String name) {
		this(name, new NBTPropertyAttribute[0]);
	}

	public NBTProperty(String name, T defaultValue) {
		this(name, defaultValue, new NBTPropertyAttribute[0]);
	}

	public NBTProperty(String name, NBTPropertyAttribute... attributes) {
		this(name, null, attributes);
	}

	public NBTProperty(String name, T defaultValue, NBTPropertyAttribute... attributes) {
		this.name = name;
		this.value = defaultValue;
		this.attributes = ImmutableSet.copyOf(attributes);
	}

	public static EnumNBTPropertyType getTypeFromId(byte id) {
		return EnumNBTPropertyType.values()[id];
	}

	/**
	 * Checks if the value of this Property has ever been set.
	 */
	public boolean isSet() {
		return this.set;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public abstract EnumNBTPropertyType getTagType();

	public void setTileEntity(TileEntityCore entity) {
		if (this.tileEntity == null) {
			this.tileEntity = entity;
		}
		else {
			throw new UnsupportedOperationException("Cannot set TileEntity of a Property after it has already been set!");
		}
	}

	protected void updateTileEntity() {
		if (this.tileEntity != null) {
			this.tileEntity.markDirty();
		}
	}

	// PropertyLinker specific:

	public PropertyLinker<T> getPropertyLinker() {
		return this.propertyLinker;
	}

	public void setPropertyLinker(PropertyLinker<T> linker) {
		if (this.propertyLinker == null) {
			this.propertyLinker = linker;
		}
	}

	// Property Value Getters:

	public T getValue() {
		return this.value;
	}

	public boolean getBooleanValue() {
		if (this.set && this instanceof NBTPropertyBoolean) {
			return ((NBTPropertyBoolean)this).getValue();
		}
		if (this.set)
			BacaCraft.LOGGER.error("Cannot read property " + this.getName() + " because it is not of type Boolean!");
		return false;
	}

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

	public void setValue(T value) {
		this.value = value;
		this.set = true;
		this.updateTileEntity();
	}

	public void setBooleanValue(boolean value) {
		if (this instanceof NBTPropertyBoolean) {
			((NBTPropertyBoolean)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not a Boolean!");
		}
	}

	public void setByteValue(byte value) {
		if (this instanceof NBTPropertyByte) {
			((NBTPropertyByte)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not a Byte!");
		}
	}

	public void setIntValue(int value) {
		if (this instanceof NBTPropertyInt) {
			((NBTPropertyInt)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not an Integer!");
		}
	}

	public void setStringValue(String value) {
		if (this instanceof NBTPropertyString) {
			((NBTPropertyString)this).setValue(value);
		}
		else {
			throw new UnsupportedOperationException(name + " is not a String!");
		}
	}

	// Attributes:

	@Nullable
	public NBTPropertyAttribute getAttribute(EnumAttributeType type) {
		return this.attributes.stream().filter(attr -> attr.getType().equals(type)).findFirst().orElse(null);
	}

	public boolean hasAttribute(EnumAttributeType type) {
		return this.attributes.stream().anyMatch(attr -> attr.getType().equals(type));
	}

	// Private Fields:

	private final String name;

	protected T value;

	private Set<NBTPropertyAttribute> attributes;

	protected boolean set;

	protected TileEntityCore tileEntity;

	private PropertyLinker<T> propertyLinker;
}
