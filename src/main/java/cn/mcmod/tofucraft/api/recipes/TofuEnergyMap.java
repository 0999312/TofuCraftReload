package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class TofuEnergyMap {
    private static HashMap<ItemStack, Integer> recipes = new HashMap<>();

    public static void init() {
        for (int i = 0; i <= 11; i++)
            register(new ItemStack(ItemLoader.tofu_food, 1, i), 50);
        register(new ItemStack(ItemLoader.material, 1, 18), 100);
        register(new ItemStack(ItemLoader.material, 1, 19), 120);
        register(new ItemStack(ItemLoader.material, 1, 25), 200);
        register(new ItemStack(ItemLoader.material, 1, 26), 200);
        register(new ItemStack(ItemLoader.material, 1, 27), 250);

    }

    public static void register(ItemStack item, int loader) {
        recipes.put(item, loader);
    }

    public static int getFuel(ItemStack item) {
        for (ItemStack rep : recipes.keySet()) {
            if (rep.getItem().equals(item.getItem()) && rep.getMetadata() == item.getMetadata())
                return recipes.get(rep);
        }
        return -1;
    }
}
