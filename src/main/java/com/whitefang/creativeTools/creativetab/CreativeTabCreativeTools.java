package com.whitefang.creativetools.creativetab;

import com.whitefang.creativetools.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabCreativeTools {
	public static final CreativeTabs CT_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
		@Override
		public Item getTabIconItem() {
			return Items.diamond_pickaxe;
		}
	};
}
