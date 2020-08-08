package cn.mcmod.tofucraft.compat.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public final class AdvancedAggregatorRecipeMaker {
	  public static List<SimpleRecipe> getRecipes(IJeiHelpers helpers)
	  {
	    IStackHelper stackHelper = helpers.getStackHelper(); 
	    List<SimpleRecipe> recipes = new ArrayList<SimpleRecipe>();

	    for (Entry<Object[], ItemStack> entry : AdvancedAggregatorRecipes.instance().RecipesList.entrySet()) {
	    	List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
	    	for (Object obj : entry.getKey()) {
	    		List<ItemStack> subinputs = stackHelper.toItemStackList(obj);
		    	inputs.add(subinputs);
			}

	    	SimpleRecipe newrecipe = new SimpleRecipe(inputs,entry.getValue());
	    	recipes.add(newrecipe);
      }
	    return recipes;
	  }
}
