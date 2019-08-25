package cn.mcmod.tofucraft.api.tfenergy;

import net.minecraft.tileentity.TileEntity;

public interface ITofuEnergy {

    int getEnergyStored();

    int getMaxEnergyStored();

    int receive(int energy, boolean simulate);

    int drain(int energy, boolean simulate);

    //Can the machine gets energy
    boolean canReceive(TileEntity from);

    //Can the machine gives out energy
    boolean canDrain(TileEntity to);

    default boolean isEnergyFull() {
        return getEnergyStored() == getMaxEnergyStored();
    }

    default boolean isEnergyEmpty() {
        return getEnergyStored() == 0;
    }
}
