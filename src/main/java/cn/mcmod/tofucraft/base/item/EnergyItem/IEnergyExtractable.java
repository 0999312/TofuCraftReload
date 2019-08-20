package cn.mcmod.tofucraft.base.item.EnergyItem;

import net.minecraft.item.ItemStack;

public interface IEnergyExtractable {
    int drain(ItemStack inst, int amount, boolean simulate);
}
