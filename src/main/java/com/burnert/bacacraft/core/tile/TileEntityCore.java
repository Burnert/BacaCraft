package com.burnert.bacacraft.core.tile;

import com.burnert.bacacraft.core.property.NBTProperty;
import com.burnert.bacacraft.core.property.NBTPropertyNull;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Map;

public abstract class TileEntityCore extends TileEntity {

	private final Map<String, NBTProperty> nbtProperties = Maps.newHashMap();

	/**
	 * Override this method in derived class to create properties.
	 * Call {@link TileEntityCore#addNBTProperty(NBTProperty)} inside of it to add an {@link NBTProperty} to the list.
	 */
	public void createNBTProperties() { }

	public void addNBTProperty(NBTProperty property) {
		this.nbtProperties.put(property.getName(), property);
	}

	public boolean hasNBTProperty(String name) {
		return this.nbtProperties.containsKey(name);
	}

	public NBTProperty getNBTProperty(String name) {
		return this.nbtProperties.getOrDefault(name, new NBTPropertyNull());
	}

//	public void addNBTProperty(String name, EnumNBTPropertyType tagType) {
//		NBTProperty nbtProperty = new NBTProperty(name, tagType);
//		this.addNBTProperty(nbtProperty);
//	}

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

	// TileEntity:


	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.createNBTProperties();
		super.handleUpdateTag(tag);
	}

	@Override
	protected void setWorldCreate(World worldIn) {
		super.setWorldCreate(worldIn);
		this.createNBTProperties();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		for (String name : compound.getKeySet()) {
			if (this.hasNBTProperty(name)) {
				NBTProperty property = this.getNBTProperty(name);
				readNBTProperty(property, compound);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		for (Map.Entry<String, NBTProperty> propertyEntry : nbtProperties.entrySet()) {
			NBTProperty property = propertyEntry.getValue();
			if (property.isSet()) {
				writeNBTProperty(property, compound);
			}
		}

		return compound;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();

		for (Map.Entry<String, NBTProperty> propertyEntry : nbtProperties.entrySet()) {
			NBTProperty property = propertyEntry.getValue();
			if (property.isSet() && property.shouldSendToClient()) {
				writeNBTProperty(property, compound);
			}
		}

		return compound;
	}

	// End of TileEntity
}
