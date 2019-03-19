package cn.mcmod.tofucraft.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TofuOreDictLoader {
	public TofuOreDictLoader() {
		OreDictionary.registerOre("seedRice",ItemLoader.riceseed);
		OreDictionary.registerOre("cropRice",ItemLoader.rice);
	}
	public static final ItemStack Soysause = new ItemStack(ItemLoader.soysauce_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Dashi = new ItemStack(ItemLoader.dashi_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Soyold = new ItemStack(ItemLoader.soyoil_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Doubanjiang = new ItemStack(ItemLoader.doubanjiang_bottle, 1, OreDictionary.WILDCARD_VALUE);
}
