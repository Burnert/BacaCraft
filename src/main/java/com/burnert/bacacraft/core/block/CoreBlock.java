package com.burnert.bacacraft.core.block;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.registry.BacaCraftBlockRegistry;
import com.burnert.bacacraft.core.util.IItemModelRegister;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoreBlock extends Block implements IItemModelRegister {

	public CoreBlock(String name, Material materialIn) {
		this(name, materialIn, materialIn.getMaterialMapColor());
	}

	public CoreBlock(String name, Material materialIn, MapColor blockMapColorIn) {
		super(materialIn, blockMapColorIn);
		this.setUnlocalizedName(BacaCraft.MOD_ID + "." + name);
		this.setRegistryName(new ResourceLocation(BacaCraft.MOD_ID, name));

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
