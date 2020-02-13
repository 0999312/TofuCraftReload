package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.util.OredictItemStack;
import cn.mcmod.tofucraft.util.RecipesUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class AdvancedAggregatorRecipes {
    public ItemStack resultItem;
    public ArrayList<Object> inputItems = new ArrayList<>();
    public int processTime;
    public boolean enchantment = false;
    public static ArrayList<AdvancedAggregatorRecipes> mortarRecipesList = new ArrayList<>();

    public AdvancedAggregatorRecipes(ItemStack result, Object[] main, int processTime) {
        for (Object o : main) {
            if (o instanceof ItemStack || o instanceof OredictItemStack) {
                this.inputItems.add(o);
            } else if (o instanceof String) {
                this.inputItems.add(new OredictItemStack((String) o, 1));
            } else throw new IllegalArgumentException("Not a itemStack or Ore Dictionary");
        }
        this.resultItem = result.copy();
        this.processTime = processTime;
    }

    public static AdvancedAggregatorRecipes getBestRecipe(NonNullList<ItemStack> inventory) {
        AdvancedAggregatorRecipes bestRecipe = null;
        for (AdvancedAggregatorRecipes recipe : mortarRecipesList) {
            boolean match = true;
            for (Object i : recipe.inputItems) {
                boolean found = false;
                if (i instanceof ItemStack) {
                    ItemStack is = (ItemStack) i;
                    for (ItemStack have : inventory) {
                        if (ItemStack.areItemsEqual(have, is) && have.getCount() >= is.getCount()) {
                            found = true;
                            break;
                        }
                    }
                } else if (i instanceof OredictItemStack) {
                    OredictItemStack odis = (OredictItemStack) i;
                    for (ItemStack have : inventory) {
                        if (odis.isMatchingItemStack(have) && odis.getCount() <= have.getCount()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    match = false;
                    break;
                }
            }
            if (match) {
                if (bestRecipe == null || bestRecipe.inputItems.size() < recipe.inputItems.size()) {
                    bestRecipe = recipe;
                }
            }
        }

        return bestRecipe;
    }

    public ItemStack getResultItemStack() {
        return resultItem.copy();
    }


    public static void addRecipe(Object[] main, ItemStack result) {
        addRecipe(main, result, 100);
    }

    public static void addRecipe(Object main, ItemStack result) {
        addRecipe(main, result, 100);
    }

    public static void addRecipe(Object[] main, ItemStack result, int processTime) {
        mortarRecipesList.add(new AdvancedAggregatorRecipes(result, main, processTime));
    }

    public static void addRecipe(Object main, ItemStack result, int processTime) {
        mortarRecipesList.add(new AdvancedAggregatorRecipes(result, new Object[]{main}, processTime));
    }

    public static void ClearRecipe(Object input) {
        if (input instanceof ItemStack || input instanceof String) {
            for (AdvancedAggregatorRecipes recipes : mortarRecipesList) {
                if (input instanceof ItemStack && ItemStack.areItemStacksEqual((ItemStack) input, recipes.resultItem))
                    mortarRecipesList.remove(recipes);
                if (input instanceof String) {
                    NonNullList<ItemStack> ore = OreDictionary.getOres((String) input);
                    if (RecipesUtil.containsMatch(false, ore, recipes.resultItem))
                        mortarRecipesList.remove(recipes);
                }
            }
        } else throw new IllegalArgumentException("Not a itemStack or Ore Dictionary");
    }

    public static void ClearAllRecipe() {
        mortarRecipesList.clear();
    }
}
