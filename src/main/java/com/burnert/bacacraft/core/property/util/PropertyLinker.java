package com.burnert.bacacraft.core.property.util;

import com.burnert.bacacraft.core.property.tile.EnumNBTPropertyType;
import com.burnert.bacacraft.core.property.tile.NBTProperty;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PropertyLinker<T extends Comparable<T>> {

	private final World world;
	private final BlockPos blockPos;
	private final NBTProperty<T> nbtProperty;
	private final IProperty<T> property;

	public PropertyLinker(World world, BlockPos blockPos, IProperty<T> property, NBTProperty<T> nbtProperty) {
		this.property = property;
		this.world = world;
		this.blockPos = blockPos;
		this.nbtProperty = nbtProperty;
	}

	public <V extends T> V getPropertyValue() {
		EnumNBTPropertyType type = this.nbtProperty.getTagType();
		switch (type) {
			case BOOLEAN:
				return (V) Boolean.valueOf(this.nbtProperty.getBooleanValue());
			case INT:
				return (V) Integer.valueOf(this.nbtProperty.getIntValue());
		}
		return null;
	}

	public IProperty<T> getLinkedProperty() {
		return this.property;
	}

	public void onPropertyChanged() {
//		IBlockState state = this.world.getBlockState(this.blockPos);
//
//		switch (this.nbtProperty.getTagType()) {
//			case BOOLEAN:
//				state = state.withProperty((PropertyBool)this.property, ((NBTPropertyBoolean)this.nbtProperty).getValue());
//				break;
//			case BYTE:
//			case INT:
//			case STRING:
//		}
//		this.world.setBlockState(this.blockPos, state);
	}
}
