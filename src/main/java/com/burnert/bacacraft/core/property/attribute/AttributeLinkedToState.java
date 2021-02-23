package com.burnert.bacacraft.core.property.attribute;

import net.minecraft.block.properties.IProperty;

public class AttributeLinkedToState<T extends Comparable<T>> extends NBTPropertyAttribute {

	public AttributeLinkedToState(IProperty<T> property) {
		super(EnumAttributeType.LINKED_TO_STATE);
		this.property = property;
	}

	public AttributeLinkedToState(IProperty<T> property, ILinkedToStateFunction function) {
		this(property);
		this.stateFunction = function;
	}

	public boolean hasCustomStateFunction() {
		return this.stateFunction != null;
	}

	public ILinkedToStateFunction getStateFunction() {
		return this.stateFunction;
	}

	private final IProperty<T> property;

	private ILinkedToStateFunction stateFunction;

	public IProperty<T> getProperty() {
		return this.property;
	}
}
