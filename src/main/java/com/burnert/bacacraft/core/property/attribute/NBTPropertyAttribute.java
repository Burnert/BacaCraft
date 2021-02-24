package com.burnert.bacacraft.core.property.attribute;

import net.minecraft.block.properties.IProperty;

public class NBTPropertyAttribute {

	public NBTPropertyAttribute(EnumAttributeType type) {
		this.type = type;
	}

	public EnumAttributeType getType() {
		return this.type;
	}

	private final EnumAttributeType type;

	public static class SendToClient extends NBTPropertyAttribute {
		public SendToClient() {
			super(EnumAttributeType.SEND_TO_CLIENT);
		}
	}

	public static class Persistent extends NBTPropertyAttribute {
		public Persistent() {
			super(EnumAttributeType.PERSISTENT);
		}
	}

	public static class DisplayName extends NBTPropertyAttribute {
		public DisplayName() {
			super(EnumAttributeType.DISPLAY_NAME);
		}
	}

	public static class LinkedToState<T extends Comparable<T>> extends NBTPropertyAttribute {
		public LinkedToState(IProperty<T> property) {
			super(EnumAttributeType.LINKED_TO_STATE);
			this.property = property;
		}

		public LinkedToState(IProperty<T> property, ILinkedToStateFunction function) {
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
}
