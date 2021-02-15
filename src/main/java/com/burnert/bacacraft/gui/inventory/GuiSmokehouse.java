package com.burnert.bacacraft.gui.inventory;

import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.inventory.ContainerSmokehouse;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSmokehouse extends GuiContainer {
	private static final ResourceLocation SMOKEHOUSE_GUI_TEXTURES = new ResourceLocation(BacaCraftReference.MOD_ID, "textures/gui/container/smokehouse.png");
	private final InventoryPlayer inventoryPlayer;
	private final TileEntitySmokehouse tileSmokehouse;

	public GuiSmokehouse(InventoryPlayer inventory, TileEntitySmokehouse tileEntity) {
		super(new ContainerSmokehouse(inventory, tileEntity));
		this.xSize = 176;
		this.ySize = 175;
		this.inventoryPlayer = inventory;
		this.tileSmokehouse = tileEntity;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String guiCaption = this.tileSmokehouse.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(guiCaption, xSize / 2 - this.fontRenderer.getStringWidth(guiCaption) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(SMOKEHOUSE_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (TileEntitySmokehouse.isBurning(this.tileSmokehouse)) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 81, j + 46 + 12 - k, this.xSize, 12 - k, 14, 14 - (12 - k));
		}

		for (int k = 0; k < 8; ++k) {
			if (TileEntitySmokehouse.isSmokingSlot(this.tileSmokehouse, k)) {
				int l = this.getSmokeProgressForSlotScaled(16, k);
				this.drawTexturedModalRect(i + 17 + k * 18, j + 38, this.xSize, 14, 16 - (15 - l), 4);
			}
		}
//
//		int l = this.getCookProgressScaled(24);
//		this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
	}

	private int getSmokeProgressForSlotScaled(int pixels, int slot) {
		int smokeTime = this.tileSmokehouse.getField(2 + slot);
		int totalSmokeTime = this.tileSmokehouse.getField(10 + slot);
		return totalSmokeTime != 0 && smokeTime != 0 ? smokeTime * pixels / totalSmokeTime : 0;
	}

	private int getBurnLeftScaled(int pixels) {
		int totalBurnTime = this.tileSmokehouse.getField(1);
		int burnTimeRem = this.tileSmokehouse.getField(0);
//		if (fuelBurnTime == 0) {
//			fuelBurnTime = 200;
//		}
		if (totalBurnTime == 0) {
			//throw new IllegalStateException("Wtf?");
			return 0;
		}
		return burnTimeRem * pixels / totalBurnTime;
	}
}
