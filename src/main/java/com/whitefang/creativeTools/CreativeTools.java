package com.whitefang.creativetools;

import com.whitefang.creativetools.handler.ConfigurationHandler;
import com.whitefang.creativetools.init.ModBlocks;
import com.whitefang.creativetools.init.ModItems;
import com.whitefang.creativetools.proxy.IProxy;
import com.whitefang.creativetools.reference.Reference;
import com.whitefang.creativetools.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY_CLASS)
public class CreativeTools {
	@Mod.Instance(Reference.MOD_ID)
	public static CreativeTools instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	public static final Item.ToolMaterial CREATIVE_MATERIAL = EnumHelper.addToolMaterial("CREATIVE_MATERIAL", 0, 100,
			50.0F, 40.0F, 0);

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