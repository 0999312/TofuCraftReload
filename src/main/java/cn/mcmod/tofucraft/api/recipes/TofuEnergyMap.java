package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class TofuEnergyMap {
    private static HashMap<ItemStack, Integer> recipes = new HashMap<>();
    private static HashMap<FluidStack, Integer> fluidRecipes = new HashMap<>();

    public static void init() {
        for (int i = 0; i <= 11; i++)
            register(new ItemStack(ItemLoader.tofu_food, 1, i), 50);
        register(new ItemStack(ItemLoader.material, 1, 18), 100);
        register(new ItemStack(ItemLoader.material, 1, 19), 120);
        register(new ItemStack(ItemLoader.material, 1, 25), 200);
        register(new ItemStack(ItemLoader.material, 1, 26), 200);
        register(new ItemStack(ItemLoader.material, 1, 27), 250);
        register(new ItemStack(ItemLoader.material, 1, 32), 50);
        register(new ItemStack(ItemLoader.tofuchinger_tooth, 1), 400);

        register(new FluidStack(BlockLoader.SOYMILK_FLUID, 100), 100);
        register(new FluidStack(BlockLoader.SOYMILKHELL_FLUID, 70), 200);
    }

    public static void register(ItemStack item, int loader) {
        recipes.put(item, loader);
    }

    public static void register(FluidStack fluid, int loader) {
        fluidRecipes.put(fluid, loader);
    }

    public static int getFuel(ItemStack item) {
        for (ItemStack rep : recipes.keySet()) {
            if (rep.getItem().equals(item.getItem()) && rep.getMetadata() == item.getMetadata())
                return recipes.get(rep);
        }
        return -1;
    }

    public static Map.Entry<FluidStack, Integer> getLiquidFuel(FluidStack fluid) {
        for (Map.Entry<FluidStack, Integer> rep : fluidRecipes.entrySet()) {
            if (rep.getKey().getFluid().equals(fluid.getFluid()))
                return rep;
        }
        return null;
    }
}
