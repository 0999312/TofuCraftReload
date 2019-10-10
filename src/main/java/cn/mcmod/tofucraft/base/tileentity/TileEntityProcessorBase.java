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

    /*
    * The working condition for a machine, e.g. a proper recipe
    * */
    public abstract boolean canWork();

    /*
    * What will the machine do when working, like draining energy, or something else
    * */
    public abstract void onWork();

    /*
    * What will the machine do if the working condition is not met, like resetting working status
    * */
    public abstract void failed();

    /*
    * What will the machine do if a working cycle is finished, like output items.
    * */
    public abstract void done();

    /*
    * A general function for the machine, if there's anything needed
    * */
    public void general(){};

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

        general();
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
