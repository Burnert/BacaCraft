package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.core.tile.ITileNameable;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityContraption extends TileEntityCore implements ITileNameable {

	private String customName;

	private EnumFacing facing = EnumFacing.NORTH;

	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

	public EnumFacing getFacing() {
		return facing;
	}

	// ITileNameable:

	@Override
	public void setCustomName(String name) {
		if (!name.isEmpty())
			this.customName = name;
	}

	@Override
	public String getCustomName() {
		return this.customName;
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null;
	}

	// End of ITileNameable

	// TileEntity:

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey("Facing")) {
			int facingIndex = compound.getInteger("Facing");
			facing = EnumFacing.getHorizontal(facingIndex);
		}
		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("Facing", this.facing.getHorizontalIndex());
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();

		compound.setInteger("Facing", this.facing.getHorizontalIndex());
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	// End of TileEntity
}
