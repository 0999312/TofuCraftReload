package cn.mcmod.tofucraft.base.tileentity;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityWorkerBase extends TileEntityEnergyBase {

    /*
     * Comment:
     * This is the base of a TESTE which only consumes energy to do a certain things.
     * There's no much code to write.
     * */

    public TileEntityWorkerBase(int energyMax) {
        super(energyMax);
    }

    @Override
    public boolean canReceive(TileEntity from) {
        return true;
    }

    @Override
    public boolean canDrain(TileEntity to) {
        return false;
    }


}
