package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoTest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FlintLockAmmoMap {
    private static HashMap<ItemStack, EntityAmmoBase> mapping = new HashMap<>();

    public static HashMap<ItemStack, EntityAmmoBase> getMapping() {
        return mapping;
    }

    public static void init(){
        register(new ItemStack(Items.GLASS_BOTTLE, 1), new EntityAmmoTest(null));
    }

    public static void register(ItemStack key, EntityAmmoBase value) {
        mapping.put(key, value);
    }

    public static Map.Entry<ItemStack, EntityAmmoBase> findAmmo(ItemStack stack) {
        for (Map.Entry<ItemStack, EntityAmmoBase> entry : mapping.entrySet()) {
            if (entry.getKey().isItemEqual(stack) && stack.getCount() >= entry.getKey().getCount())
                return entry;
        }
        return null;
    }
}
