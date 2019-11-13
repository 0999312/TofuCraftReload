package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;
import java.util.TreeSet;

public class TofuOreDictLoader {
	public static final Set<String> OreList = new TreeSet<>();
	public TofuOreDictLoader() {
		registerOre("seedRice",ItemLoader.riceseed);
		registerOre("cropRice",ItemLoader.rice);
	    registerOre("listAllgrain", ItemLoader.rice);
		registerOre("cropLeek", new ItemStack(ItemLoader.material,1,16));
	    registerOre("dustSalt", new ItemStack(ItemLoader.material,1,0));
	    registerOre("itemSalt", new ItemStack(ItemLoader.material,1,0));
	    
	    registerOre("listAlltofu", new ItemStack(ItemLoader.tofu_food,1,0));
	    registerOre("listAlltofu", new ItemStack(ItemLoader.tofu_food,1,1));
	    
	    registerOre("foodZunda", new ItemStack(ItemLoader.material,1,4));
	    
	    registerOre("listAlltofuBlock", new ItemStack(BlockLoader.MOMENTOFU));
	    registerOre("listAlltofuBlock", new ItemStack(BlockLoader.KINUTOFU));
	    
	    registerOre("cropChilipepper", new ItemStack(ItemLoader.material,1,31));
	    registerOre("cropLemon", new ItemStack(ItemLoader.material,1,30));
	    
	    registerOre("cropSoybean", new ItemStack(ItemLoader.soybeans));
	    registerOre("cropAlmond", new ItemStack(ItemLoader.material,1,9));
	    
	    registerOre("foodSoysauce", Soysauce);
	    registerOre("foodOliveoil", Soyoil);
	    registerOre("foodMayo", Mayonnaise);
        registerOre("foodApricotjelly", ApricotJerry);
        registerOre("foodStrawberryjelly", StrawberryJam);
	    
	    registerVanillaFoods();
	}
	
	private static void registerVanillaFoods() {
		  registerOre("listAllchickenraw", Items.CHICKEN);
		  registerOre("listAllegg", Items.EGG);
		  registerOre("listAllchickencooked", Items.COOKED_CHICKEN);
		  registerOre("listAllporkraw", Items.PORKCHOP);
		  registerOre("listAllporkcooked", Items.COOKED_PORKCHOP);
		  registerOre("listAllbeefraw", Items.BEEF);
		  registerOre("listAllbeefcooked", Items.COOKED_BEEF);
		  registerOre("listAllmuttonraw", Items.MUTTON);
		  registerOre("listAllmuttoncooked", Items.COOKED_MUTTON);
		  registerOre("listAllrabbitraw", Items.RABBIT);
		  registerOre("listAllrabbitcooked", Items.COOKED_RABBIT);
		  registerOre("bread", Items.BREAD);
		  registerOre("foodBread", Items.BREAD);
		    
		  registerOre("cropCarrot", Items.CARROT);
		  registerOre("cropPotato", Items.POTATO);
		  registerOre("cropPumpkin", Blocks.PUMPKIN);
		  registerOre("cropWheat", Items.WHEAT);
		  registerOre("cropBeet", Items.BEETROOT);
		    
		  registerOre("listAllgrain", Items.WHEAT);
		  registerOre("cropApple", Items.APPLE);
		  registerOre("listAllfruit", Items.APPLE);
		  registerOre("listAllfruit", Items.CHORUS_FRUIT);
		    
		  registerOre("listAllmeatraw", Items.BEEF);
		  registerOre("listAllmeatraw", Items.CHICKEN);
		  registerOre("listAllmeatraw", Items.PORKCHOP);
		  registerOre("listAllmeatraw", Items.MUTTON);
		  registerOre("listAllmeatraw", Items.RABBIT);
		  registerOre("listAllmeatcooked", Items.COOKED_BEEF);
		  registerOre("listAllmeatcooked", Items.COOKED_CHICKEN);
		  registerOre("listAllmeatcooked", Items.COOKED_PORKCHOP);
		  registerOre("listAllmeatcooked", Items.COOKED_MUTTON);
		  registerOre("listAllmeatcooked", Items.COOKED_RABBIT);

		  registerOre("listAllfishraw", new ItemStack(Items.FISH, 1, 32767));
		  registerOre("listAllfishfresh", new ItemStack(Items.FISH, 1, 32767));
		  registerOre("listAllfishcooked", Items.COOKED_FISH);
		  registerOre("listAllfishcooked", new ItemStack(Items.COOKED_FISH, 1, 1));
		  registerOre("salmonRaw", new ItemStack(Items.FISH, 1));
		    
		  registerOre("listAllsugar", Items.SUGAR);
		  registerOre("cropBeet", Items.BEETROOT);
		  registerOre("seedBeet", Items.BEETROOT_SEEDS);
		  if(!Loader.isModLoaded("sakura"))
			  registerOre("sakuraLeaves", new ItemStack(Items.DYE,1,9));
		  registerOre("listAllwater", Items.WATER_BUCKET);
		  registerOre("listAllmilk", Items.MILK_BUCKET);
	}
	private static void registerOre(String string, Block itemStack) {
		OreList.add(string);
		OreDictionary.registerOre(string, itemStack);
	}

	private static void registerOre(String string, ItemStack itemStack) {
		OreList.add(string);
		OreDictionary.registerOre(string, itemStack);
	}

	private static void registerOre(String string, Item itemStack) {
		OreList.add(string);
		OreDictionary.registerOre(string, itemStack);
	}
	public static final ItemStack Soysauce = new ItemStack(ItemLoader.soysauce_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Dashi = new ItemStack(ItemLoader.dashi_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Soyoil = new ItemStack(ItemLoader.soyoil_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Doubanjiang = new ItemStack(ItemLoader.doubanjiang_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack Mayonnaise = new ItemStack(ItemLoader.mayonnaise_bottle, 1, OreDictionary.WILDCARD_VALUE);
	public static final ItemStack RollingPin = new ItemStack(ItemLoader.RollingPin, 1, OreDictionary.WILDCARD_VALUE);
    public static final ItemStack ApricotJerry = new ItemStack(ItemLoader.apricotjerry_bottle, 1, OreDictionary.WILDCARD_VALUE);
    public static final ItemStack StrawberryJam = new ItemStack(ItemLoader.strawberryjam_bottle, 1, OreDictionary.WILDCARD_VALUE);
}
