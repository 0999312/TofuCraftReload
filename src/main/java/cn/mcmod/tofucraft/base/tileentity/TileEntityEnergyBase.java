package cn.mcmod.tofucraft.base.tileentity;

import cn.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public abstract class TileEntityEnergyBase extends TileEntity implements ITofuEnergy {

    public static final String TAG_ENERGY = "tf_energy";
    public static final String TAG_ENERGY_MAX = "tf_energy_max";

    protected int energy;
    protected int energyMax;

    public TileEntityEnergyBase(int energyMax) {
        this.energyMax = energyMax;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return energyMax;
    }

    @Override
    public int receive(int toReceive, boolean simulate) {
        if (!simulate) energy = Math.min(energy + toReceive, energyMax);
        return Math.min(toReceive, energyMax - energy);
    }

    @Override
    public int drain(int toDrain, boolean simulate) {
        if (!simulate) energy = Math.max(energy - toDrain, 0);
        return Math.min(toDrain, energy);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_ENERGY, energy);
        compound.setInteger(TAG_ENERGY_MAX, energyMax);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.energyMax = compound.getInteger(TAG_ENERGY_MAX);
        this.energy = compound.getInteger(TAG_ENERGY);
    }

    @Override
    public boolean canReceive() {
        return energy<energyMax;
    }

    @Override
    public boolean canDrain() {
        return energy>0;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new SPacketUpdateTileEntity(pos, this.getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }
}
