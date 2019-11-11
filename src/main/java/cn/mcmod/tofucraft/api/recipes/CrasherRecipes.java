package cn.mcmod.tofucraft.api.recipes;

import com.google.common.collect.Maps;

import cn.mcmod.tofucraft.util.RecipesUtil;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class CrasherRecipes
{
    /** The list of smelting results. */
    public final static Map<Object, ItemStack> recipesList = Maps.<Object, ItemStack>newHashMap();

    public CrasherRecipes()
    {
//      addRecipe("gravel", new ItemStack(Items.FLINT));
    }
    /**
     * Adds a recipe using an ItemStack as the input for the recipe.
     */
    public static void addRecipe(Object input, ItemStack stack)
    {
        if(findResult(input)){
        	net.minecraftforge.fml.common.FMLLog.log.info("Ignored Tofu Crasher recipe with conflicting input: {} = {}", input, stack);
        	return;
        }
        recipesList.put(input, stack);
    }

    public static boolean findResult(Object input) {
        for (Entry<Object, ItemStack> entry : recipesList.entrySet())
        {
            if(input instanceof ItemStack){
                if (RecipesUtil.compareItems(entry.getKey(), (ItemStack) input))
                {
                    return true;
                }
            }else if(input instanceof String){
            	if(entry.getKey() instanceof String){
            		return (input.equals(entry.getKey())) ?true:false;
            	}
            }else throw new IllegalArgumentException("Not a ItemStack or Ore Dictionary");
        }
		return false;
	}
    
    /**
     * Returns the smelting result of an item.
     */
    public static ItemStack getResult(ItemStack stack)
    {
        for (Entry<Object, ItemStack> entry : recipesList.entrySet())
        {
            if (RecipesUtil.compareItems(entry.getKey(), stack))
            {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }

    public static Map<Object, ItemStack> getRecipeList()
    {
        return recipesList;
    }
    
    public static void ClearRecipe(Object input) {
    	recipesList.remove(input);
	}
    
    public static void ClearAllRecipe() {
    	recipesList.clear();
	}
}