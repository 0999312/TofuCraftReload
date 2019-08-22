package cn.mcmod.tofucraft.base.item.EnergyItem;

import cn.mcmod.tofucraft.util.NBTUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemTofuEnergyContained extends Item implements IEnergyExtractable, IEnergyInsertable, IEnergyContained {

    /*
     * Comment:
     * This is the base class of a TF Energy Item which can insert energy to, extract energy from and store energy.
     * There are some exceptions, so I divided the code into three interfaces.
     * Codes here are highly overridable, but can have function without any overrides.
     * */

    public static final String TAG_TF = "tf_energy";
    public static final String TAG_TFMAX = "tf_energymax";

    @Override
    public int drain(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) - calculated);
        return calculated;
    }

    @Override
    public int fill(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergyMax(inst) - getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) + calculated);
        return calculated;
    }

    @Override
    public int getEnergy(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTagCompound(), TAG_TF, 0);
    }

    //This acts as a way to modify the max energy an item can hold.
    //You can override it to strictly declare how much an item should hold.
    @Override
    public int getEnergyMax(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTagCompound(), TAG_TFMAX, 0);
    }

    @Override
    public void setEnergy(ItemStack inst, int amount) {
        inst.setTagCompound(NBTUtil.setInteger(inst.getTagCompound(), TAG_TF, amount));
    }

    @Override
    public void setEnergyMax(ItemStack inst, int amount) {
        inst.setTagCompound(NBTUtil.setInteger(inst.getTagCompound(), TAG_TFMAX, amount));
    }
}
