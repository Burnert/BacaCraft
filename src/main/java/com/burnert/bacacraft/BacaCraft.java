package com.burnert.bacacraft;

import com.burnert.bacacraft.block.BlockContraption;
import com.burnert.bacacraft.core.proxy.CommonProxy;
import com.burnert.bacacraft.core.registry.BacaCraftTERegistry;
import com.burnert.bacacraft.core.util.GuiHandler;
import com.burnert.bacacraft.item.ItemBlockContraption;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(
		modid = BacaCraft.MOD_ID,
		name = BacaCraft.NAME,
		version = BacaCraft.VERSION,
		acceptedMinecraftVersions = BacaCraft.ACCEPTED_VERSIONS)
public class BacaCraft {
	public static final String MOD_ID = "bacacraft";
	public static final String NAME = "BacaCraft";
	public static final String VERSION = "0.2-Alpha";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";

	public static final String CLIENT_PROXY_CLASS = "com.burnert.bacacraft.core.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.burnert.bacacraft.core.proxy.CommonProxy";
	public static final int GUI_SMOKEHOUSE = 0;

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	@Mod.Instance(BacaCraft.MOD_ID)
	public static BacaCraft instance;

	public BacaCraft() {
	}

	@SidedProxy(clientSide = BacaCraft.CLIENT_PROXY_CLASS, serverSide = BacaCraft.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.EventHandler
	@SuppressWarnings("unused")
	public static void preInit(FMLPreInitializationEvent event) {
		BacaCraftTERegistry.registerTileEntities();
	}

	@Mod.EventHandler
	@SuppressWarnings("unused")
	public static void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(BacaCraft.instance, new GuiHandler());

		// Add milk bucket to ore dict if it isn't there yet
		List<ItemStack> listAllmilk = OreDictionary.getOres("listAllmilk");
		if (listAllmilk.isEmpty()) {
			OreDictionary.registerOre("listAllmilk", Items.MILK_BUCKET);
		}
	}

	@Mod.EventHandler
	@SuppressWarnings("unused")
	public static void postInit(FMLPostInitializationEvent event) {

	}
}
