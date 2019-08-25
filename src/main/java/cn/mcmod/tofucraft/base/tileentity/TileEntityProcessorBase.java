package cn.mcmod.tofucraft.base.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntityProcessorBase extends TileEntityWorkerBase implements ITickable {

    /*
     * Comment:
     * The processor base represents a series of processor, which has a process time, a total time,
     * and execute a group of code each time it worked, finished work, or failed to work.
     *
     * Use furnace as example:
     * If furnace has proper item and fuel, then it means that furnace can start the processing (canWork)
     * Each tick the furnace can work, furnace will update the process time (onWork)
     * If the item is not valid during the progress, then the progress will be reset to 0 (failed)
     * Once the process time reached the max time, then the furnace will consume items and give out output. (done)
     *
     * The code logic here is quite simple, but has great advantage on making code easy to read.
     * And you don't need to set the tag every time.
     * */

    public static final String TAG_TIME = "tf_processtime";
    public static final String TAG_TOTAL = "tf_totaltime";
    protected int processTime;
    protected int maxTime;


    public TileEntityProcessorBase(int energyMax) {
        super(energyMax);
    }

    public abstract boolean canWork();

    public abstract void onWork();

    public abstract void failed();

    public abstract void done();

    @Override
    public void update() {
        if (canWork()) {
            onWork();
            if (processTime >= maxTime) {
                processTime = 0;
                done();
            }
        } else
            failed();


    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_TIME, processTime);
        compound.setInteger(TAG_TOTAL, maxTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        processTime = compound.getInteger(TAG_TIME);
        maxTime = compound.getInteger(TAG_TOTAL);
    }
}
