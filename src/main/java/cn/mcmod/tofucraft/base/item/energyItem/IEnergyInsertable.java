package cn.mcmod.tofucraft.base.item.energyItem;

import net.minecraft.item.ItemStack;

public interface IEnergyInsertable {
    int fill(ItemStack inst, int energy, boolean simulate);
}
