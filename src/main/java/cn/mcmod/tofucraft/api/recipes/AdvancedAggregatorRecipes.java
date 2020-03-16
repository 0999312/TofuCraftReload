package cn.mcmod.tofucraft.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import java.util.ArrayList;

import cn.mcmod_mmf.mmlib.util.OredictItemStack;

public class AdvancedAggregatorRecipes {
    public ItemStack resultItem;
    public ArrayList<Object> inputItems = new ArrayList<>();
    public int processTime;
    public boolean enchantment = false;
    public static ArrayList<AdvancedAggregatorRecipes> aggregatorRecipesList = new ArrayList<>();

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
        for (AdvancedAggregatorRecipes recipe : aggregatorRecipesList) {
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


    public static void addRecipe(Object[] inputs, ItemStack result) {
        addRecipe(inputs, result, 100);
    }

    public static void addRecipe(Object input, ItemStack result) {
        addRecipe(input, result, 100);
    }

    public static void addRecipe(Object[] inputs, ItemStack result, int processTime) {
        aggregatorRecipesList.add(new AdvancedAggregatorRecipes(result, inputs, processTime));
    }

    public static void addRecipe(Object input, ItemStack result, int processTime) {
        aggregatorRecipesList.add(new AdvancedAggregatorRecipes(result, new Object[]{input}, processTime));
    }

    public static void removeRecipe(Object[] inputs) {
        AdvancedAggregatorRecipes find = null;
        for (AdvancedAggregatorRecipes agg : aggregatorRecipesList) {
            boolean unmatched = false;
            for (Object i : agg.inputItems) {
                boolean found = false;
                if (i instanceof ItemStack) {
                    for (Object l : inputs) {
                        if (l instanceof ItemStack) {
                            if (ItemStack.areItemsEqual((ItemStack) i, (ItemStack) l)) {
                                found = true;
                                break;
                            }
                        }
                    }
                } else if (i instanceof OredictItemStack) {
                    for (Object l : inputs) {
                        if (l instanceof OredictItemStack) {
                            if (((OredictItemStack) i).getOre().equals(((OredictItemStack) l).getOre())) {
                                found = true;
                                break;
                            }
                        }
                    }
                }
                if (!found) {
                    unmatched = true;
                    break;
                }
            }
            if (!unmatched) {
                if (inputs.length == agg.inputItems.size()) {
                    find = agg;
                    break;
                }
            }
        }

        if (find != null) {
            aggregatorRecipesList.remove(find);
        }
    }

    public static void clearRecipes() {
        aggregatorRecipesList.clear();
    }
}
