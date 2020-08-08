package cn.mcmod.tofucraft.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import cn.mcmod_mmf.mmlib.util.RecipesUtil;

public class AdvancedAggregatorRecipes {

    public final Map<Object[], ItemStack> RecipesList = Maps.newHashMap();
	private static final AdvancedAggregatorRecipes RECIPE_BASE = new AdvancedAggregatorRecipes();
	private AdvancedAggregatorRecipes() {
	}
    public static AdvancedAggregatorRecipes instance() {
        return RECIPE_BASE;
    }

    public void addRecipes(ItemStack result, Object[] main) {
        RecipesList.put(main, result);
    }

    public ItemStack getResult(List<ItemStack> inputs) {
        ItemStack retStack = ItemStack.EMPTY;

        for(Entry<Object[], ItemStack> entry : RecipesList.entrySet()){
            boolean flg1 = true;
            if ((inputs.size() != entry.getKey().length)) 
                continue;
        	for (Object obj1 : entry.getKey()) {
        		boolean flg2 = false;
        		for (ItemStack input:inputs) {
        			if(input.isEmpty()) break;
                	if(obj1 instanceof ItemStack){
                		ItemStack stack1 = (ItemStack) obj1;
    	                if (RecipesUtil.getInstance().compareItems(input, stack1)) {
    	                	inputs.remove(input);
    	                    flg2 = true;
    	                    break;
    	                }
                    }else if(obj1 instanceof String){
                    	NonNullList<ItemStack> ore = OreDictionary.getOres((String) obj1);
                    	if (!ore.isEmpty()&&RecipesUtil.getInstance().containsMatch(false, ore, input)) {
    	                	inputs.remove(input);
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

            if (flg1) {
                return entry.getValue();
            }
        }
        
        return retStack;
    }

    public void ClearRecipe(Object[] inputs) {
    	RecipesList.remove(inputs);
	}
	
    public void ClearAllRecipe() {
    	RecipesList.clear();
	}
}
