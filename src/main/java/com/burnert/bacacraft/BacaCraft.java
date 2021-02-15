package com.burnert.bacacraft;

import com.burnert.bacacraft.core.config.BacaCraftReference;
import com.burnert.bacacraft.core.proxy.CommonProxy;
import com.burnert.bacacraft.core.BacaCraftTERegistry;
import com.burnert.bacacraft.util.GuiHandler;
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
		modid = BacaCraftReference.MOD_ID,
		name = BacaCraftReference.NAME,
		version = BacaCraftReference.VERSION,
		acceptedMinecraftVersions = BacaCraftReference.ACCEPTED_VERSIONS)
public class BacaCraft {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	@Mod.Instance(BacaCraftReference.MOD_ID)
	public static BacaCraft instance;

	public BacaCraft() {
	}

	@SidedProxy(clientSide = BacaCraftReference.CLIENT_PROXY_CLASS, serverSide = BacaCraftReference.COMMON_PROXY_CLASS)
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
