package cn.mcmod.tofucraft.base.item.EnergyItem;

import net.minecraft.item.ItemStack;

public interface IEnergyContained {
    int getEnergy(ItemStack inst);
    int getEnergyMax(ItemStack inst);

    void setEnergy(ItemStack inst, int amount);
    void setEnergyMax(ItemStack inst, int amount);
}
