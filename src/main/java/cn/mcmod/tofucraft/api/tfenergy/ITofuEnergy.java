package cn.mcmod.tofucraft.api.tfenergy;

public interface ITofuEnergy {

    int getEnergyStored();
    int getMaxEnergyStored();

    int receive(int energy, boolean simulate);
    int drain(int energy, boolean simulate);

    int getPriority();
    void setPriority(int priority);

    //Can the machine gets energy
    boolean canReceive(int priority);
    //Can the machine gives out energy
    boolean canDrain(int priority);


}
