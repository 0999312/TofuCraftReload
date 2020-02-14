package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.util.ItemUtils;
import cn.mcmod.tofucraft.util.OredictItemStack;
import com.google.common.collect.Maps;

import cn.mcmod.tofucraft.util.RecipesUtil;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class AggregatorRecipes {

    public final static Map<Object, ItemStack> recipesList = Maps.newHashMap();

    private AggregatorRecipes() {
//      addRecipe("gravel", new ItemStack(Items.FLINT));
    }

    /**
     * Adds a recipe using an ItemStack as the input for the recipe.
     */
    public static void addRecipe(Object input, ItemStack stack) {
        if (input instanceof String) {
            input = new OredictItemStack((String) input, 1);
        }
        if (findResult(input)) {
            net.minecraftforge.fml.common.FMLLog.log.info("Ignored Tofu Aggregator recipe with conflicting input: {} = {}", input, stack);
            return;
        }
        recipesList.put(input, stack);
    }

    public static boolean findResult(Object input) {
        for (Object key : recipesList.keySet()) {
            if (RecipesUtil.compareItems(key, input))
                return true;
        }
        return false;
    }

    public static Entry<Object, ItemStack> getResult(ItemStack stack) {
        for (Entry<Object, ItemStack> entry : recipesList.entrySet()) {
            if (RecipesUtil.compareItems(entry.getKey(), stack) &&
                    ItemUtils.getSomeAmount(entry.getKey()) <= stack.getCount()) {
                return entry;
            }
        }
        return null;
    }

    public static Map<Object, ItemStack> getRecipeList() {
        return recipesList;
    }

    public static void removeRecipe(Object input) {
        recipesList.remove(input);
    }

    public static void clearAllRecipes() {
        recipesList.clear();
    }
}