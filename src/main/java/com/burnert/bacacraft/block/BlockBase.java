package com.burnert.bacacraft.block;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.BacaCraftBlockRegistry;
import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.util.IItemModelRegister;
import com.burnert.bacacraft.core.BacaCraftItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase extends Block implements IItemModelRegister {

	public BlockBase(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public BlockBase(String name, Material materialIn, MapColor blockMapColorIn) {
		super(materialIn, blockMapColorIn);
		this.setUnlocalizedName(BacaCraftReference.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraftReference.MOD_ID, name));

		BacaCraftBlockRegistry.blockList.add(this);
		BacaCraftItemRegistry.itemList.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public Block setSound(SoundType sound) {
		this.setSoundType(sound);
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		BacaCraft.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
