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
	    List<SimpleRecipe> recipes = new ArrayList<SimpleRecipe>();
	    
	    for (AdvancedAggregatorRecipes recipe : AdvancedAggregatorRecipes.mortarRecipesList) {
	    	List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
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
