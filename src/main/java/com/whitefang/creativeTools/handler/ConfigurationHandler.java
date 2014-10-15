package com.whitefang.creativetools.handler;

import com.whitefang.creativetools.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
	public static Configuration configuration;
	public static int xRange = 1;
	public static int yRange = 1;
	public static int zRange = 1;

	public static void init(File configFile) {
		// Create the configuration object from the given configuration file
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	private static void loadConfiguration() {
		xRange = configuration.getInt("xRange", Configuration.CATEGORY_GENERAL, 1, 0, 9, "");
		yRange = configuration.getInt("yRange", Configuration.CATEGORY_GENERAL, 1, 0, 9, "");
		zRange = configuration.getInt("zRange", Configuration.CATEGORY_GENERAL, 1, 0, 9, "");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}