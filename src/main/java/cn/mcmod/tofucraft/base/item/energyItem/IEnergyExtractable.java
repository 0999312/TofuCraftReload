package cn.mcmod.tofucraft.base.item.energyItem;

import net.minecraft.item.ItemStack;

public interface IEnergyExtractable {
    int drain(ItemStack inst, int amount, boolean simulate);
}
