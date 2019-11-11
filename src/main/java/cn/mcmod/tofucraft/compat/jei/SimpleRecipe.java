package cn.mcmod.tofucraft.compat.jei;

import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class SimpleRecipe implements IRecipeWrapper {
	private final List<List<ItemStack>> inputs;
	private final ItemStack output;

//	public SimpleRecipe(List<ItemStack> inputs, ItemStack output) {
//		this.inputs = Collections.singletonList(inputs);
//		this.output = output;
//	}

	public SimpleRecipe(List<List<ItemStack>> inputs, ItemStack output) {
		this.inputs = inputs;
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}

}
