package cn.mcmod.tofucraft.util;

import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static void damageItemStack(ItemStack stack, int amount){
        if (stack.isItemStackDamageable()){
            stack.setItemDamage(stack.getItemDamage()+amount);
            if (stack.getItemDamage()>stack.getMaxDamage()){
                stack.shrink(1);
            }
        }
    }
}
