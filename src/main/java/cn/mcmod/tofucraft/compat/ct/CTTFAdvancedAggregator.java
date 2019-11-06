package cn.mcmod.tofucraft.compat.ct;

import java.util.List;

import cn.mcmod.tofucraft.RecipeLoader;
import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tofucraft.tofuAdvancedAggregator")
@ZenRegister
public class CTTFAdvancedAggregator {
	@ZenMethod
	public static void RemoveRecipe(IIngredient input) {
		Object itemInput=null;
		if (input instanceof IItemStack) {
			itemInput=CraftTweakerMC.getItemStack(input);
		} 
		else if(input instanceof IOreDictEntry) {
			itemInput=((IOreDictEntry)input).getName();
		}
		if(itemInput!=null)
			RecipeLoader.actions.add(new Removal(itemInput));
	}
	
	private final static NonNullList<ItemStack> getItemStacks(IIngredient input) {
		if (input == null)
        {
            return NonNullList.create();
        }
        else
        {
            List<IItemStack> list = input.getItems();
            NonNullList<ItemStack> result = NonNullList.create();
            for (IItemStack in : list)
            {
                result.add(CraftTweakerMC.getItemStack(in));
            }
            return result;
        }
	}
	
	@ZenMethod
	public static void AddRecipe(IIngredient input,IItemStack output) {
		NonNullList<ItemStack> itemInput = getItemStacks(input);
		ItemStack[] array = new ItemStack[itemInput.size()];
	    for(int i = 0; i < itemInput.size();i++){
	        array[i] = itemInput.get(i);
	    }
		RecipeLoader.actions.add(new Addition(array,CraftTweakerMC.getItemStack(output)));
	}
	
	@ZenMethod
	public static void ClearAllRecipe() {
		RecipeLoader.actions.add(new ClearAllRecipe());
	}
	
    private static final class Removal implements IAction
    {
        private final Object itemInput;

        private Removal(Object itemInput)
        {
            this.itemInput = itemInput;
        }

        @Override
        public void apply()
        {
        	AdvancedAggregatorRecipes.ClearRecipe(itemInput);
        }

        @Override
        public String describe()
        {
            return "Removing a recipe for ToFu Aggregator";
        }
    }
	
    private static final class Addition implements IAction
    {
        private final Object[] itemInput;
        private final ItemStack itemOutput;

        private Addition(Object[] itemInput, ItemStack itemOutput)
        {
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
        }

        @Override
        public void apply()
        {
        	AdvancedAggregatorRecipes.addRecipe(itemInput, itemOutput);
        }

        @Override
        public String describe()
        {
            return "Adding a recipe for ToFu Aggregator";
        }
    }

	
	private static final class ClearAllRecipe implements IAction
    {
        @Override
        public void apply()
        {
        	AdvancedAggregatorRecipes.ClearAllRecipe();
        }

        @Override
        public String describe()
        {
            return "Removing all recipes from ToFu Aggregator";
        }
    }
}
