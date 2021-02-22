package com.burnert.bacacraft.core.tile;

import com.burnert.bacacraft.core.property.NBTProperty;
import com.burnert.bacacraft.core.property.NBTPropertyNull;
import com.burnert.bacacraft.core.property.util.NBTPropertyHelper;
import com.google.common.collect.ImmutableMap;
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

	public Map<String, NBTProperty> getNBTProperties() {
		return ImmutableMap.copyOf(this.nbtProperties);
	}

	public void addNBTProperty(NBTProperty property) {
		this.nbtProperties.put(property.getName(), property);
	}

	public boolean hasNBTProperty(String name) {
		return this.nbtProperties.containsKey(name);
	}

	public NBTProperty getNBTProperty(String name) {
		return this.nbtProperties.getOrDefault(name, new NBTPropertyNull());
	}

	// TileEntity:

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.createNBTProperties();
		super.handleUpdateTag(tag);
	}

	@Override
	protected void setWorldCreate(World worldIn) {
		this.createNBTProperties();
		super.setWorldCreate(worldIn);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		for (String name : compound.getKeySet()) {
			if (this.hasNBTProperty(name)) {
				NBTProperty property = this.getNBTProperty(name);
				NBTPropertyHelper.readNBTProperty(property, compound);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		for (Map.Entry<String, NBTProperty> propertyEntry : nbtProperties.entrySet()) {
			NBTProperty property = propertyEntry.getValue();
			if (property.isSet()) {
				NBTPropertyHelper.writeNBTProperty(property, compound);
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
				NBTPropertyHelper.writeNBTProperty(property, compound);
			}
		}

		return compound;
	}

	// End of TileEntity
}
