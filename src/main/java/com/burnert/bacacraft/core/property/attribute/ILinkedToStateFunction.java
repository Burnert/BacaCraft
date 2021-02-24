package com.burnert.bacacraft.core.property.attribute;

import com.burnert.bacacraft.core.property.tile.NBTProperty;

public interface ILinkedToStateFunction<T extends Comparable<T>> {
	T updateState(NBTProperty<T> nbtProperty);
}
