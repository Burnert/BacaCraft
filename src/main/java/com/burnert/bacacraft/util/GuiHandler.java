package com.burnert.bacacraft.util;

import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.gui.inventory.GuiSmokehouse;
import com.burnert.bacacraft.inventory.ContainerSmokehouse;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == BacaCraftReference.GUI_SMOKEHOUSE) {
			return new ContainerSmokehouse(player.inventory, (TileEntitySmokehouse) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == BacaCraftReference.GUI_SMOKEHOUSE) {
			return new GuiSmokehouse(player.inventory, (TileEntitySmokehouse) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
}
