package cn.mcmod.tofucraft.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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

    public static void growOrSetInventoryItem(NonNullList<ItemStack> inventory, ItemStack toSet, int index) {
        ItemStack stack = inventory.get(index);
        if (stack.isEmpty()) {
            inventory.set(index, toSet);
        } else {
            stack.grow(toSet.getCount());
        }
    }

    public static int getSomeAmount(Object i) {
        if (i instanceof ItemStack) {
            return ((ItemStack) i).getCount();
        } else if (i instanceof OredictItemStack) {
            return ((OredictItemStack) i).getCount();
        }
        return 0;
    }
}
