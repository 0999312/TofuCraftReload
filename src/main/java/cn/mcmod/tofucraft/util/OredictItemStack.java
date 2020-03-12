package cn.mcmod.tofucraft.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class OredictItemStack {
    private String ore;
    private int count;

    public OredictItemStack(String ore, int count) {
        this.ore = ore;
        this.count = count;
    }

    public String getOre() {
        return ore;
    }

    public int getCount() {
        return count;
    }

    public boolean isMatchingItemStack(ItemStack stack) {
        NonNullList<ItemStack> oreStacks = OreDictionary.getOres(this.ore);
        return !ore.isEmpty() && OreDictionary.containsMatch(false, oreStacks, stack);
    }

    public boolean isMatchingSomething(Object i) {
        if (i instanceof OredictItemStack) {
            return this.getOre().equals(((OredictItemStack) i).getOre());
        } else if (i instanceof ItemStack) {
            return isMatchingItemStack((ItemStack) i);
        }

        return false;
    }

    public ItemStack getSomeItemStackForOutput() {
        NonNullList<ItemStack> oreStacks = OreDictionary.getOres(this.ore);
        if (!ore.isEmpty()) {
            ItemStack stack = oreStacks.get(0).copy();
            stack.setCount(this.count);
            return stack;
        }
        return ItemStack.EMPTY;
    }
}
