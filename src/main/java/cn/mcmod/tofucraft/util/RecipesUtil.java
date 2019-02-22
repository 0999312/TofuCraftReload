package cn.mcmod.tofucraft.util;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesUtil {
	public static void addRecipe(Item item, IRecipe value) {
	addRecipe(item.getRegistryName().toString().replaceAll(":", "."), value);
	}

	public static void addRecipe(String key, IRecipe value) {
        if(value.getRegistryName() == null)
            value.setRegistryName(new ResourceLocation(TofuMain.MODID,key));
        ForgeRegistries.RECIPES.register(value);
    	
	}
	public static void addOreDictionarySmelting(String ore,ItemStack output,float exp){
		for(ItemStack item :OreDictionary.getOres(ore)){
			GameRegistry.addSmelting(item, output, exp);
		}
	}
	public static void addOreDictionarySmelting(String ore,ItemStack output){
		for(ItemStack item :OreDictionary.getOres(ore)){
			GameRegistry.addSmelting(item, output, 0F);
		}
	}
}
