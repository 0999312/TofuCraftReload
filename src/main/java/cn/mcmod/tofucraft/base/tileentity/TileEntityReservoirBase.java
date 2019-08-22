package cn.mcmod.tofucraft.base.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityReservoirBase extends TileEntitySenderBase {

    /*
     * Comment:
     * This is the base of a TESTE which can extract energy from and insert energy to other TESTE.
     * So, there's a program logic of 'priority', to decide how the reservoir works.
     * If priority != priority, the higher one sucks the lower.
     * If priority == priority, they worked separately.
     * Their sender logic is similar to the SenderBase.
     * */

    public static final String TAG_PRIOR = "tf_prior";
    protected int priority;

    public TileEntityReservoirBase(int energyMax) {
        super(energyMax);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_PRIOR, priority);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        priority = compound.getInteger(TAG_PRIOR);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean canDrain(TileEntity to) {
        return !(to instanceof TileEntityReservoirBase) || ((TileEntityReservoirBase) to).getPriority() > this.priority;
    }

    @Override
    public boolean canReceive(TileEntity from) {
        return !(from instanceof TileEntityReservoirBase) || ((TileEntityReservoirBase) from).getPriority() < this.priority;
    }
}
