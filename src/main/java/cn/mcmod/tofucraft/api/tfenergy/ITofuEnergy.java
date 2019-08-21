package cn.mcmod.tofucraft.api.tfenergy;

public interface ITofuEnergy {

    int getEnergyStored();
    int getMaxEnergyStored();

    int receive(int energy, boolean simulate);
    int drain(int energy, boolean simulate);

    boolean canReceive();
    boolean canDrain();
}
