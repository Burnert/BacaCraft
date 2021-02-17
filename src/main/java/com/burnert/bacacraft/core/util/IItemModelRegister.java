package com.burnert.bacacraft.core.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelRegister {
	@SideOnly(Side.CLIENT)
	void registerModel();
}
