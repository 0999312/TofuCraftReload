package cn.mcmod.tofucraft.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.mcmod.tofucraft.api.recipes.AggregatorRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public final class AggregatorRecipeMaker {
	  public static List<SimpleRecipe> getRecipes(IJeiHelpers helpers)
	  {
	    IStackHelper stackHelper = helpers.getStackHelper(); 
	    List<SimpleRecipe> recipes = new ArrayList<SimpleRecipe>();

	    for (Map.Entry<Object, ItemStack> entry : AggregatorRecipes.recipesList.entrySet()) {
			Object input = entry.getKey();
			ItemStack output = entry.getValue();
			List<ItemStack> inputs = stackHelper.toItemStackList(input);
			SimpleRecipe newrecipe = new SimpleRecipe(Collections.singletonList(inputs),output);
	    	recipes.add(newrecipe);
        }
	    return recipes;
	  }
}
