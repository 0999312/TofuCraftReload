package cn.mcmod.tofucraft.compat.ct;

import cn.mcmod.tofucraft.RecipeLoader;
import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import cn.mcmod_mmf.mmlib.util.OredictItemStack;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@ZenClass("mods.tofucraft.tofuAdvancedAggregator")
@ZenRegister
public class CTTFAdvancedAggregator {
    @ZenMethod
    public static void removeRecipe(IIngredient[] input) {
        ArrayList<Object> converted = new ArrayList<>();
        for (IIngredient i : input) {
            if (i instanceof IItemStack) {
                converted.add(CraftTweakerMC.getItemStack(i));
            } else if (i instanceof IOreDictEntry) {
                converted.add(new OredictItemStack(((IOreDictEntry) i).getName(), 1));
            }
        }
        RecipeLoader.actions.add(new Removal(converted));
    }


    @ZenMethod
    public static void addRecipe(IIngredient[] input, IItemStack output) {
        if (input.length > 0) {
            ArrayList<Object> converted = new ArrayList<>();
            for (IIngredient i : input) {
                if (i instanceof IItemStack) {
                    converted.add(CraftTweakerMC.getItemStack(i));
                } else if (i instanceof IOreDictEntry) {
                    converted.add(new OredictItemStack(((IOreDictEntry) i).getName(), i.getAmount()));
                }
            }
            RecipeLoader.actions.add(new Addition(converted.toArray(), CraftTweakerMC.getItemStack(output)));
        }
    }

    @ZenMethod
    public static void clearAllRecipe() {
        RecipeLoader.actions.add(new ClearAllRecipe());
    }

    private static final class Removal implements IAction {
        private final ArrayList<Object> itemInput;

        private Removal(ArrayList<Object> itemInput) {
            this.itemInput = itemInput;
        }

        @Override
        public void apply() {
            AdvancedAggregatorRecipes.instance().ClearRecipe(itemInput.toArray());
        }

        @Override
        public String describe() {
            return "Removing a recipe for ToFu Aggregator";
        }
    }

    private static final class Addition implements IAction {
        private final Object[] itemInput;
        private final ItemStack itemOutput;

        private Addition(Object[] itemInput, ItemStack itemOutput) {
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
        }

        @Override
        public void apply() {
            AdvancedAggregatorRecipes.instance().addRecipes(itemOutput, itemInput);
        }

        @Override
        public String describe() {
            return "Adding a recipe for ToFu Aggregator";
        }
    }


    private static final class ClearAllRecipe implements IAction {
        @Override
        public void apply() {
            AdvancedAggregatorRecipes.instance().ClearAllRecipe();
        }

        @Override
        public String describe() {
            return "Removing all recipes from ToFu Aggregator";
        }
    }
}
