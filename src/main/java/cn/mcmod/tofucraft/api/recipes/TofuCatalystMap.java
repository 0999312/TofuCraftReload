package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TofuCatalystMap {
    private static HashMap<ItemStack, Float> catalystEffMap = new HashMap<>();

    public static void init() {
        addCatalyst(new ItemStack(ItemLoader.tofu_material, 1, 25), 1.5f);
    }

    public static void addCatalyst(ItemStack catalyst, Float efficiency) {
        ItemStack copy = catalyst.copy();
        copy.setCount(1);
        catalystEffMap.put(copy, efficiency);
    }

    public static void removeCatalyst(ItemStack catalyst) {
        for (ItemStack key : catalystEffMap.keySet()) {
            if (ItemStack.areItemsEqual(key, catalyst)) {
                catalystEffMap.remove(key);
                break;
            }
        }
    }

    public static float getPossibleEfficiency(ItemStack catalyst) {
        for (Map.Entry<ItemStack, Float> mapping : catalystEffMap.entrySet()) {
            if (ItemStack.areItemsEqual(mapping.getKey(), catalyst)) {
                return mapping.getValue();
            }
        }
        return 0;
    }
}
