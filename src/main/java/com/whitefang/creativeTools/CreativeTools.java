package com.whitefang.creativeTools;

import com.whitefang.creativeTools.handler.ConfigurationHandler;
import com.whitefang.creativeTools.init.ModBlocks;
import com.whitefang.creativeTools.init.ModItems;
import com.whitefang.creativeTools.proxy.IProxy;
import com.whitefang.creativeTools.reference.Reference;
import com.whitefang.creativeTools.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY_CLASS)
public class CreativeTools {
	@Mod.Instance(Reference.MOD_ID)
	public static CreativeTools instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		LogHelper.info("Pre Initialization Complete!");

		ModItems.init();

		ModBlocks.init();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		LogHelper.info("Initialization Complete!");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Post Initialization Complete!");
	}
}