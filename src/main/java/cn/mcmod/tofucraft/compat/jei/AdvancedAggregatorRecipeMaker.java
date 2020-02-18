package cn.mcmod.tofucraft.compat.jei;

import java.util.ArrayList;
import java.util.List;
import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public final class AdvancedAggregatorRecipeMaker {
	  public static List<SimpleRecipe> getRecipes(IJeiHelpers helpers)
	  {
	    IStackHelper stackHelper = helpers.getStackHelper(); 
	    List<SimpleRecipe> recipes = new ArrayList<>();
	    
	    for (AdvancedAggregatorRecipes recipe : AdvancedAggregatorRecipes.aggregatorRecipesList) {
	    	List<List<ItemStack>> inputs = new ArrayList<>();
	    	for (Object obj : recipe.inputItems) {
	    		List<ItemStack> subinputs = stackHelper.toItemStackList(obj);
		    	inputs.add(subinputs);
			}
	    	ItemStack output = recipe.resultItem;
	    	SimpleRecipe newrecipe = new SimpleRecipe(inputs,output);
	    	recipes.add(newrecipe);
        }
	    return recipes;
	  }
}
