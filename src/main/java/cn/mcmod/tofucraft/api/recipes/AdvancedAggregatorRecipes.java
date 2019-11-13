package cn.mcmod.tofucraft.api.recipes;

import java.util.ArrayList;
import cn.mcmod.tofucraft.util.RecipesUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class AdvancedAggregatorRecipes {
    public ItemStack resultItem = ItemStack.EMPTY;
    public ArrayList<Object> inputItems = new ArrayList<Object>();
    public boolean enchantment = false;
    public static ArrayList<AdvancedAggregatorRecipes> mortarRecipesList = new ArrayList<AdvancedAggregatorRecipes>();

    public AdvancedAggregatorRecipes(ItemStack result, Object[] main) {
        this.clear();
    	for (Object o : main) {
			if(o instanceof ItemStack||o instanceof String){
				inputItems.add(o);
			}
			else throw new IllegalArgumentException("Not a itemStack or Ore Dictionary");
        }
        resultItem = result.copy();
    }

    /**
     * 初期化
     */
    public void clear() {
        resultItem = ItemStack.EMPTY;
        inputItems = new ArrayList<Object>();
    }

    public ItemStack getResultItemStack() {
        return resultItem.copy();
    }


    public ItemStack getResult(IInventory inventory) {
        ItemStack retStack = ItemStack.EMPTY;

        ArrayList<ItemStack> inventoryList = new ArrayList<ItemStack>();
        for (int i = 0; i < 4; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                inventoryList.add(inventory.getStackInSlot(i).copy());
            }
        }

        if (!inputItems.isEmpty() && !inventoryList.isEmpty() && inventoryList.size() != inputItems.size()) {
            return retStack;
        }
        
        boolean flg1 = true;
        for (Object obj1 : inputItems) {
            boolean flg2 = false;
            for (int i = 0; i < inventoryList.size(); i++) {
            	if(obj1 instanceof ItemStack){
            		ItemStack stack1 = (ItemStack) obj1;
	                if (ItemStack.areItemsEqual(stack1, inventoryList.get(i))) {
	                    inventoryList.remove(obj1);
	                    flg2 = true;
	                    break;
	                }
                }else if(obj1 instanceof String){
                	NonNullList<ItemStack> ore = OreDictionary.getOres((String) obj1);
                	if (!ore.isEmpty()&&RecipesUtil.containsMatch(false, ore, inventoryList.get(i))) {
                        inventoryList.remove(obj1);
                        flg2 = true;
                        break;
                    }
                }
            }
            if (!flg2) {
                flg1 = false;
                break;
            }
        }

        if (!flg1) {
            return retStack;
        }
        
        return resultItem.copy();
    }

    public static void addRecipe(Object[] main,ItemStack result) {
        mortarRecipesList.add(new AdvancedAggregatorRecipes(result, main));
    }
    
    public static void addRecipe(Object main,ItemStack result) {
        mortarRecipesList.add(new AdvancedAggregatorRecipes(result, new Object[]{main}));
    }
    
    public static void ClearRecipe(Object input) {
    	if(input instanceof ItemStack||input instanceof String){
    		for (AdvancedAggregatorRecipes recipes : mortarRecipesList) {
				if(input instanceof ItemStack&&ItemStack.areItemStacksEqual((ItemStack)input, recipes.resultItem)) 
					mortarRecipesList.remove(recipes);
				if(input instanceof String) {
					NonNullList<ItemStack> ore = OreDictionary.getOres((String) input);
					if(RecipesUtil.containsMatch(false, ore, recipes.resultItem))
					mortarRecipesList.remove(recipes);
				}
			}
		}
		else throw new IllegalArgumentException("Not a itemStack or Ore Dictionary");
	}
    
    public static void ClearAllRecipe() {
    	mortarRecipesList.clear();
	}
}
