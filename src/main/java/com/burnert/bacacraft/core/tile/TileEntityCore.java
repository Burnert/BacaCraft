package com.burnert.bacacraft.core.tile;

import com.burnert.bacacraft.core.property.attribute.AttributeLinkedToState;
import com.burnert.bacacraft.core.property.attribute.EnumAttributeType;
import com.burnert.bacacraft.core.property.tile.NBTProperty;
import com.burnert.bacacraft.core.property.tile.NBTPropertyContainer;
import com.burnert.bacacraft.core.property.util.NBTPropertyHelper;
import com.burnert.bacacraft.core.property.util.PropertyLinker;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.properties.IProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Set;

public abstract class TileEntityCore extends TileEntity {

	// NBT Properties:

	public void initProperties() {
		this.nbtPropertyContainer = this.createNBTProperties();
		for (NBTProperty nbtProperty : this.nbtPropertyContainer) {
			nbtProperty.setTileEntity(this);
		}
		this.createPropertyLinkers();
	}

	/**
	 * Override this method in derived class to create properties.
	 * Return a {@link NBTPropertyContainer} with all the {@link NBTProperty} objects used in the Entity as parameters.
	 */
	public NBTPropertyContainer createNBTProperties() {
		return NBTPropertyContainer.createEmpty();
	}

	public NBTPropertyContainer getNBTPropertyContainer() {
		return this.nbtPropertyContainer;
	}

	private NBTPropertyContainer nbtPropertyContainer;

	// Property Linkers:

	public Set<PropertyLinker> getPropertyLinkers() {
		return ImmutableSet.copyOf(propertyLinkers);
	}

	private void createPropertyLinkers() {
		for (NBTProperty nbtProperty : this.getNBTPropertyContainer()) {
			AttributeLinkedToState attribute = (AttributeLinkedToState) nbtProperty.getAttribute(EnumAttributeType.LINKED_TO_STATE);
			if (attribute != null) {
				IProperty property = attribute.getProperty();
				PropertyLinker linker = new PropertyLinker<>(this.getWorld(), this.getPos(), property, nbtProperty);
				nbtProperty.setPropertyLinker(linker);
				propertyLinkers.add(linker);
			}
		}
	}

	private Set<PropertyLinker> propertyLinkers = Sets.newHashSet();

	// TileEntity:

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.initProperties();
		super.handleUpdateTag(tag);
	}

	@Override
	protected void setWorldCreate(World worldIn) {
		this.initProperties();
		super.setWorldCreate(worldIn);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		for (String name : compound.getKeySet()) {
			if (this.nbtPropertyContainer.hasNBTProperty(name)) {
				NBTProperty property = this.nbtPropertyContainer.getNBTProperty(name);
				NBTPropertyHelper.readNBTProperty(property, compound);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		for (NBTProperty nbtProperty : this.getNBTPropertyContainer()) {
			if (nbtProperty.isSet()) {
				NBTPropertyHelper.writeNBTProperty(nbtProperty, compound);
			}
		}

		return compound;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();

		for (NBTProperty nbtProperty : this.getNBTPropertyContainer()) {
			if (nbtProperty.isSet() && nbtProperty.hasAttribute(EnumAttributeType.SEND_TO_CLIENT)) {
				NBTPropertyHelper.writeNBTProperty(nbtProperty, compound);
			}
		}

		return compound;
	}

	// End of TileEntity
}
