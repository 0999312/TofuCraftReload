package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.api.recipes.recipe.SoymilkAggregate;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class SoymilkAggregationMap {
    private static HashMap<Object, SoymilkAggregate> recipes = new HashMap<>();

    public static void init() {
        register("stone", new SoymilkAggregate(
                1000,
                new ItemStack(Items.APPLE, 1),
                new FluidStack(BlockLoader.NIGARI_FLUID, 1)));
    }

    public static void register(Object key, SoymilkAggregate value) {
        recipes.put(key, value);
    }

    public static Map.Entry<Object, SoymilkAggregate> getPossibleRecipe(ItemStack stack, FluidStack fluid) {
        for (Map.Entry<Object, SoymilkAggregate> entry : recipes.entrySet()) {
            if (entry.getKey() instanceof String) {
                NonNullList<ItemStack> ore = OreDictionary.getOres((String) entry.getKey());
                if (!ore.isEmpty() && OreDictionary.containsMatch(true, ore, stack))
                    if (entry.getValue().getInput().getFluid() == fluid.getFluid() &&
                            entry.getValue().getInput().amount <= fluid.amount)
                        return entry;
            }

            if (entry.getKey() instanceof ItemStack) {
                ItemStack item = (ItemStack) entry.getKey();
                if (item.isItemEqual(stack))
                    if (entry.getValue().getInput().getFluid() == fluid.getFluid() &&
                            entry.getValue().getInput().amount <= fluid.amount)
                        return entry;
            }
        }
        return null;
    }

    public static ItemStack castToSuitableItemstack(Object o, ItemStack cpy) {
        ItemStack rep = cpy.copy();
        if (o instanceof String) {
            rep.shrink(1);
            return rep;
        }
        if (o instanceof ItemStack) {
            rep.setCount(rep.getCount() - ((ItemStack) o).getCount());
            return rep;
        }

        return ItemStack.EMPTY;
    }

    public static boolean getFluidValid(FluidStack fluid) {
        for (SoymilkAggregate agg : recipes.values()) {
            if (agg.getInput().getFluid() == fluid.getFluid())
                return true;
        }
        return false;
    }
}
