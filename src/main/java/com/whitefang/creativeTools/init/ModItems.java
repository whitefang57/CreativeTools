package com.whitefang.creativetools.init;

import com.whitefang.creativetools.item.ItemCreativeTool;
import com.whitefang.creativetools.item.tools.EasyBreak;
import com.whitefang.creativetools.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	public static final ItemCreativeTool easyBreak = new EasyBreak();

	public static void init() {
		GameRegistry.registerItem(easyBreak, "easyBreak");
	}
}
