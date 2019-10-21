package cn.mcmod.tofucraft.api.recipes;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CatalystEfficiencyMap {

    private static HashMap<ItemStack, Double> efficiency = new HashMap<>();

    public static HashMap<ItemStack, Double> getEfficiencyMap() {
        return efficiency;
    }

    public static void register(ItemStack stack, double eff) {
        efficiency.put(stack, eff);
    }

    public static double getEfficiency(ItemStack stack) {
        for (Map.Entry<ItemStack, Double> s : efficiency.entrySet()) {
            if (s.getKey().isItemEqual(stack) && s.getKey().getCount() <= stack.getCount())
                return s.getValue();
        }
        return 0;
    }
}
