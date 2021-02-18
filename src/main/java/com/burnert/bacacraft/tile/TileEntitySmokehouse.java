package com.burnert.bacacraft.tile;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.block.BlockSmokehouse;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.tile.TileCore;
import com.burnert.bacacraft.core.util.ArrayHelper;
import com.burnert.bacacraft.core.util.InventoryHelper;
import com.burnert.bacacraft.inventory.ContainerSmokehouse;
import com.burnert.bacacraft.core.inventory.ISidedInventoryAdvanced;
import com.burnert.bacacraft.core.inventory.SidedInventoryWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

import static com.burnert.bacacraft.block.BlockSmokehouse.isBlockTopObstructed;

public class TileEntitySmokehouse extends TileCore implements ITickable, ISidedInventoryAdvanced {

//	private static final int[] SLOTS_TOP = new int[]{};
	private static final int[] SLOTS_SIDES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final int[] SLOTS_BOTTOM = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
	/**
	 * 0 - fuel slot
	 * 1-8 - smoking slots
	 */
	private NonNullList<ItemStack> smokehouseItemStacks = NonNullList.withSize(9, ItemStack.EMPTY);
	// Fuel burn time
	private int smokehouseBurnTime;
	private int currentItemBurnTime;
	// Array of slots smoking times
	private int[] smokeTime = new int[8];
	private int[] totalSmokeTime = new int[8];
	private String smokehouseCustomName;

	public TileEntitySmokehouse() {
		super();
		BacaCraft.LOGGER.info(this.getName() + " constructed");
	}

	public int getSlotSmokeTime(int slot) {
		return this.smokeTime[slot];
	}

	public int getSlotTotalSmokeTime(int slot) {
		return this.totalSmokeTime[slot];
	}

	private void setSlotSmoketime(int slot, int time) {
		this.smokeTime[slot] = time;
	}

	private void setSlotTotalSmoketime(int slot, int time) {
		this.totalSmokeTime[slot] = time;
	}

	private void fillSlotSmoketime(int[] times) {
		System.arraycopy(times, 0, this.smokeTime, 0, times.length);
	}

	private void fillSlotTotalSmoketime(int[] times) {
		System.arraycopy(times, 0, this.smokeTime, 0, times.length);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN)
			return SLOTS_BOTTOM;
		else
			return SLOTS_SIDES;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction != EnumFacing.DOWN) {
			if (isItemFuel(stack))
				return true;

			NonNullList<ItemStack> mountainCheeseSlots = InventoryHelper.getSlotSubarray(smokehouseItemStacks, 1, 9);

			if (InventoryHelper.isInventoryNotFull(mountainCheeseSlots)) {
				return stack.getItem() == BacaCraftItemRegistry.MOUNTAIN_CHEESE;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN) {
			return stack.getItem().equals(BacaCraftItemRegistry.MOUNTAIN_CHEESE_SMOKED) ||
					stack.getItem().equals(BacaCraftItemRegistry.MOUNTAIN_CHEESE_OVERSMOKED);
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.smokehouseItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, this.smokehouseItemStacks);
		this.smokehouseBurnTime = compound.getInteger("BurnTime");

		int[] NBTSmokeTime = compound.getIntArray("SmokeTime");
		ArrayHelper.fillArray(this.smokeTime, NBTSmokeTime);

		int[] NBTSmokeTimeTotal = compound.getIntArray("SmokeTimeTotal");
		ArrayHelper.fillArray(this.totalSmokeTime, NBTSmokeTimeTotal);

		this.currentItemBurnTime = TileEntitySmokehouse.getItemBurnTime(smokehouseItemStacks.get(0));

		if (compound.hasKey("CustomName", 8)) {
			this.smokehouseCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", this.smokehouseBurnTime);
		compound.setIntArray("SmokeTime", this.smokeTime);
		compound.setIntArray("SmokeTimeTotal", this.totalSmokeTime);
		ItemStackHelper.saveAllItems(compound, this.smokehouseItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.smokehouseCustomName);
		}
		return compound;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.smokehouseCustomName : "container.bacacraft.smokehouse";
	}

	@Override
	public boolean hasCustomName() {
		return this.smokehouseCustomName != null && !this.smokehouseCustomName.isEmpty();
	}

	public void setCustomInventoryName(String customName) {
		this.smokehouseCustomName = customName;
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack.isEmpty())
			return 0;

		Item item = stack.getItem();
		if (item.equals(Items.WHEAT)) {
			return 3600;
		}
		return 0;
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	@Override
	public void update() {
		boolean wasBurning = this.isBurning();
		boolean dirty = false;

		if (this.isBurning()) {
			--this.smokehouseBurnTime;
		}
		if (!this.world.isRemote) {
			ItemStack[] smokeStacks = new ItemStack[8];
			ItemStack fuel = this.smokehouseItemStacks.get(0);

			boolean startBurning = false;
			for (int s = 0; s < 8; ++s) {
				smokeStacks[s] = this.smokehouseItemStacks.get(s);
				// Start burning if any slot can be smoked
				if (!this.isBurning() && !fuel.isEmpty() && this.canSmokeSlot(s)) {
					startBurning = true;
				}
			}
			if (startBurning) {
				BacaCraft.LOGGER.info(this.getName() + " started burning.");
				this.smokehouseBurnTime = getItemBurnTime(fuel);
				this.currentItemBurnTime = this.smokehouseBurnTime;
				// If the item is fuel, burn time is > 0, so it's burning.
				if (this.isBurning()) {
					dirty = true;

					if (!fuel.isEmpty()) {
						Item item = fuel.getItem();
						fuel.shrink(1);
						BacaCraft.LOGGER.info(this.getName() + " shrunk fuel stack.");
						// Container handling
						if (fuel.isEmpty()) {
							ItemStack container = item.getContainerItem(fuel);
							this.smokehouseItemStacks.set(0, container);
						}
					}
				}
			}
			if (this.isBurning()) {
				for (int s = 0; s < 8; ++s) {
					if (this.canSmokeSlot(s)) {
						++this.smokeTime[s];

						if (this.smokeTime[s] == this.totalSmokeTime[s]) {
							this.smokeTime[s] = 0;
							this.totalSmokeTime[s] = this.getSmokeTime(smokeStacks[s]);
							this.smokeSlot(s);
							dirty = true;
						}
					}
					else {
						this.smokeTime[s] = 0;
					}
				}
			}
			else {
				for (int s = 0; s < 8; ++s) {
					this.smokeTime[s] = 0;
				}
			}
			if (wasBurning != this.isBurning()) {
				dirty = true;
				BlockSmokehouse.setState(this.isBurning(), this.world, this.pos);
			}
		}
		if (dirty) {
			this.markDirty();
		}
	}

	private boolean canSmokeSlot(int slot) {
		if (this.smokehouseItemStacks.get(slot + 1).isEmpty()) {
			return false;
		}
		else if (!this.smokehouseItemStacks.get(slot + 1).getItem().equals(BacaCraftItemRegistry.MOUNTAIN_CHEESE)) {
			return false;
		}
		return true;
	}

	private void smokeSlot(int slot) {
		if (this.canSmokeSlot(slot)) {
			Item changeToItem = BacaCraftItemRegistry.MOUNTAIN_CHEESE_SMOKED;
			// Make different kind of mountain cheese when the block outlet is obstructed
			if (isBlockTopObstructed(this.world, this.getPos())) {
				changeToItem = BacaCraftItemRegistry.MOUNTAIN_CHEESE_OVERSMOKED;
			}
			this.smokehouseItemStacks.set(slot + 1, new ItemStack(changeToItem, 1));
		}
	}

	//@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerSmokehouse(playerInventory, this);
	}

	public String getGuiID() {
		return BacaCraft.MOD_ID + ":smokehouse";
	}

	@Nullable
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return this.smokehouseItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		return InventoryHelper.isInventoryEmpty(this.smokehouseItemStacks);
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.smokehouseItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.smokehouseItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.smokehouseItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.smokehouseItemStacks.get(index);
		boolean flag = stack.isEmpty() || !stack.isItemEqual(itemstack) || !ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.smokehouseItemStacks.set(index, stack);
		if (stack.getCount() > this.getSlotStackLimit(index)) {
			stack.setCount(this.getSlotStackLimit(index));
		}
		if (index > 0 && index < 9 && flag) {
			this.totalSmokeTime[index - 1] = this.getSmokeTime(stack);
			this.smokeTime[index - 1] = 0;
			this.markDirty();
		}
	}

	@Override
	public int getSlotStackLimit(int slot) {
		if (slot > 0) {
			return 1;
		}
		else {
			return 64;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		}
		else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	public boolean isBurning() {
		return this.smokehouseBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public boolean isSmokingSlot(int smokingSlot) {
		return this.smokeTime[smokingSlot] > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isSmokingSlot(IInventory inventory, int smokingSlot) {
		return inventory.getField(smokingSlot + 2) > 0;
	}

	public int getSmokeTime(ItemStack stack) {
		if (stack.getItem().equals(BacaCraftItemRegistry.MOUNTAIN_CHEESE)) {
			return 4000;
		}
		else {
			return 0;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0)
			return isItemFuel(stack);
		else if (smokehouseItemStacks.get(index).isEmpty())
			return stack.getItem() == BacaCraftItemRegistry.MOUNTAIN_CHEESE;
		return false;
	}

	/**
	 * 0 - burn time; 1 - current item burn time
	 * 2-9 - smoke time slots; 10-17 - total smoke time slots
	 */
	@Override
	public int getField(int id) {
		if (id >= 2 && id < 10) {
			return this.smokeTime[id - 2];
		}
		else if (id >= 10 && id < 18) {
			return this.totalSmokeTime[id - 10];
		}
		switch (id) {
			case 0:
				return this.smokehouseBurnTime;
			case 1:
				return this.currentItemBurnTime;
		}
		return 0;
		// Te metody tego typu są kurwa pijane jakieś
	}

	/**
	 * 0 - burn time; 1 - current item burn time
	 * 2-9 - smoke time slots; 10-17 - total smoke time slots
	 */
	@Override
	public void setField(int id, int value) {
		if (id >= 2 && id < 10) {
			this.smokeTime[id - 2] = value;
		}
		else if (id >= 10 && id < 18) {
			this.totalSmokeTime[id - 10] = value;
		}
		switch (id) {
			case 0:
				this.smokehouseBurnTime = value;
				break;
			case 1:
				this.currentItemBurnTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 18;
	}

	@Override
	public void clear() {
		this.smokehouseItemStacks.clear();
	}

//	private IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	private IItemHandler handlerBottom = new SidedInventoryWrapper(this, EnumFacing.DOWN);
	private IItemHandler handlerSides = new SidedInventoryWrapper(this, EnumFacing.WEST);

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else
				return (T) handlerSides;
		}
		return super.getCapability(capability, facing);
	}
}
