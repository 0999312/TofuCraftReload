package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsTofu extends CreativeTabs {

	public CreativeTabsTofu() {
		super(TofuMain.MODID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getTabIconItem() {
		// TODO Auto-generated method stub
		return new ItemStack(ItemLoader.tofu_food,1,0);
	}

}
