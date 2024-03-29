package com.burnert.bacacraft.core.property.tile;

import com.google.common.collect.ImmutableMap;
import com.sun.istack.internal.NotNull;

import java.util.Iterator;
import java.util.Map;

public class NBTPropertyContainer implements Iterable<NBTProperty<?>> {

	public NBTPropertyContainer(NBTProperty<?>... nbtProperties) {
		ImmutableMap.Builder<String, NBTProperty<?>> mapBuilder = ImmutableMap.builder();
		for (NBTProperty<?> property : nbtProperties) {
			mapBuilder.put(property.getName(), property);
		}
		this.nbtProperties = mapBuilder.build();
	}

	public NBTPropertyContainer(NBTPropertyContainer container, NBTProperty<?>... nbtProperties) {
		ImmutableMap.Builder<String, NBTProperty<?>> mapBuilder = ImmutableMap.builder();
		if (container != null) {
			mapBuilder.putAll(container.nbtProperties);
		}
		for (NBTProperty<?> property : nbtProperties) {
			mapBuilder.put(property.getName(), property);
		}
		this.nbtProperties = mapBuilder.build();
	}

	private NBTPropertyContainer() {
		this.nbtProperties = ImmutableMap.of();
	}

	public static NBTPropertyContainer createEmpty() {
		return new NBTPropertyContainer();
	}

	public boolean hasNBTProperty(String name) {
		return this.nbtProperties.containsKey(name);
	}

	public NBTProperty getNBTProperty(String name) {
		return this.nbtProperties.getOrDefault(name, null);
	}

	private final Map<String, NBTProperty<?>> nbtProperties;

	@Override
	@NotNull
	public Iterator<NBTProperty<?>> iterator() {
		return this.nbtProperties.values().iterator();
	}
}
