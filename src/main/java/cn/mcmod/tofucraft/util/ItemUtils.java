package cn.mcmod.tofucraft.util;

import net.minecraft.item.ItemStack;

public class ItemUtils {
    /*
    * Comment:
    * Cute bagu-chan, you should inspect the vanilla code more before you trying to write something to damage the ItemStack.*/
    public static void damageItemStack(ItemStack stack, int amount) {
        if (stack.isItemStackDamageable()) {
            stack.setItemDamage(stack.getItemDamage() + amount);
            if (stack.getItemDamage() > stack.getMaxDamage()) {
                stack.shrink(1);
            }
        }
    }

}
