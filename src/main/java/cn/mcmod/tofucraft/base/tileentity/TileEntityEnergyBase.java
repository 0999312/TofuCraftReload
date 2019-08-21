package cn.mcmod.tofucraft.base.tileentity;

import cn.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TileEntityEnergyBase extends TileEntity implements ITofuEnergy {

    public static final String TAG_ENERGY = "tf_energy";
    public static final String TAG_ENERGY_MAX = "tf_energy_max";
    public static final String TAG_UUID = "tf_uuid";
    public static final String TAG_PRIOR = "tf_priority";

    protected String uuid = "";

    protected int energy;
    protected int energyMax;

    protected int priority;

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
        compound.setString(TAG_UUID, uuid);
        compound.setInteger(TAG_ENERGY_MAX, energyMax);
        compound.setInteger(TAG_PRIOR, priority);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.energyMax = compound.getInteger(TAG_ENERGY_MAX);
        this.energy = compound.getInteger(TAG_ENERGY);
        this.uuid = compound.getString(TAG_UUID);
        this.priority = compound.getInteger(TAG_PRIOR);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean canReceive(int priority) {
        return this.priority < priority &&  energy < energyMax;
    }

    @Override
    public boolean canDrain(int priority) {
        return this.priority > priority && energy>0;
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

    @Override
    public void validate() {
        super.validate();
        readFromNBT(getUpdateTag());
        if (!world.isRemote){
            if (uuid.isEmpty()) uuid = UUID.randomUUID().toString();
            TofuNetwork.Instance.register(uuid, this);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (!world.isRemote)
            TofuNetwork.Instance.unload(uuid);
    }
}
