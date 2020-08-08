package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TofuOreDictLoader {

	public TofuOreDictLoader() {
		OreDictionary.registerOre("seedRice",ItemLoader.riceseed);
		OreDictionary.registerOre("cropRice",ItemLoader.rice);
	    OreDictionary.registerOre("listAllgrain", ItemLoader.rice);
		OreDictionary.registerOre("cropLeek", new ItemStack(ItemLoader.material,1,16));
	    OreDictionary.registerOre("dustSalt", new ItemStack(ItemLoader.material,1,0));
	    OreDictionary.registerOre("itemSalt", new ItemStack(ItemLoader.material,1,0));
	    
	    OreDictionary.registerOre("listAlltofu", new ItemStack(ItemLoader.tofu_food,1,0));
	    OreDictionary.registerOre("listAlltofu", new ItemStack(ItemLoader.tofu_food,1,1));
	    OreDictionary.registerOre("listAlltofuFried", new ItemStack(ItemLoader.tofu_food,1,4));
	    OreDictionary.registerOre("listAlltofuFried", new ItemStack(ItemLoader.tofu_food,1,5));
	    
	    OreDictionary.registerOre("foodZunda", new ItemStack(ItemLoader.material,1,4));
	    OreDictionary.registerOre("foodMiso", new ItemStack(ItemLoader.material,1,2));
	    
	    OreDictionary.registerOre("listAlltofuBlock", new ItemStack(BlockLoader.MOMENTOFU));
	    OreDictionary.registerOre("listAlltofuBlock", new ItemStack(BlockLoader.KINUTOFU));
	    
	    OreDictionary.registerOre("cropChilipepper", new ItemStack(ItemLoader.material,1,31));
	    OreDictionary.registerOre("cropLemon", new ItemStack(ItemLoader.material,1,30));
	    
	    OreDictionary.registerOre("cropSoybean", new ItemStack(ItemLoader.soybeans));
	    OreDictionary.registerOre("cropAlmond", new ItemStack(ItemLoader.material,1,9));
	    
	    OreDictionary.registerOre("foodSoysauce", Soysauce);
	    OreDictionary.registerOre("foodDashi", Dashi);
	    OreDictionary.registerOre("foodOliveoil", Soyoil);
	    OreDictionary.registerOre("foodMayo", Mayonnaise);
        OreDictionary.registerOre("foodApricotjelly", ApricotJerry);
        OreDictionary.registerOre("foodStrawberryjelly", StrawberryJam);
	    
	}

	public static final ItemStack Soysauce = new ItemStack(ItemLoader.sauce_bottle, 1, 0);
	public static final ItemStack Dashi = new ItemStack(ItemLoader.sauce_bottle, 1, 1);
	public static final ItemStack Soyoil = new ItemStack(ItemLoader.sauce_bottle, 1, 2);
	public static final ItemStack Doubanjiang = new ItemStack(ItemLoader.sauce_bottle, 1,3);
	public static final ItemStack Mayonnaise = new ItemStack(ItemLoader.sauce_bottle, 1, 4);
	public static final ItemStack RollingPin = new ItemStack(ItemLoader.RollingPin, 1, OreDictionary.WILDCARD_VALUE);
    public static final ItemStack ApricotJerry = new ItemStack(ItemLoader.sauce_bottle, 1, 5);
    public static final ItemStack StrawberryJam = new ItemStack(ItemLoader.sauce_bottle, 1, 6);
}
