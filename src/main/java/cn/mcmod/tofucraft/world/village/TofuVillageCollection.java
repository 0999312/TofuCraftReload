package cn.mcmod.tofucraft.world.village;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class TofuVillageCollection extends WorldSavedData {
    private World world;
    /**
     * This is a black hole. You can add data to this list through a public interface, but you can't query that
     * information in any way and it's not used internally either.
     */
    private final List<BlockPos> villagerPositionsList = Lists.<BlockPos>newArrayList();
    private final List<VillageDoorInfo> newDoors = Lists.<VillageDoorInfo>newArrayList();
    private final List<TofuVillage> villageList = Lists.<TofuVillage>newArrayList();
    private int tickCounter;

    public TofuVillageCollection(String name) {
        super(name);
    }

    public TofuVillageCollection(World worldIn) {
        super(fileNameForProvider(worldIn.provider));
        this.world = worldIn;
        this.markDirty();
    }

    public void setWorldsForAll(World worldIn) {
        this.world = worldIn;

        for (TofuVillage village : this.villageList) {
            village.setWorld(worldIn);
        }
    }

    public void addToVillagerPositionList(BlockPos pos) {
        if (this.villagerPositionsList.size() <= 64) {
            if (!this.positionInList(pos)) {
                this.villagerPositionsList.add(pos);
            }
        }
    }

    /**
     * Runs a single tick for the village collection
     */
    public void tick() {
        ++this.tickCounter;

        for (TofuVillage village : this.villageList) {
            village.tick(this.tickCounter);
        }

        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();

        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }

    private void removeAnnihilatedVillages() {
        Iterator<TofuVillage> iterator = this.villageList.iterator();

        while (iterator.hasNext()) {
            TofuVillage village = iterator.next();

            if (village.isAnnihilated()) {
                iterator.remove();
                this.markDirty();
            }
        }
    }

    /**
     * Get a list of villages.
     */
    public List<TofuVillage> getVillageList() {
        return this.villageList;
    }

    public TofuVillage getNearestVillage(BlockPos doorBlock, int radius) {
        TofuVillage village = null;
        double d0 = 3.4028234663852886E38D;

        for (TofuVillage village1 : this.villageList) {
            double d1 = village1.getCenter().distanceSq(doorBlock);

            if (d1 < d0) {
                float f = (float) (radius + village1.getTofuVillageRadius());

                if (d1 <= (double) (f * f)) {
                    village = village1;
                    d0 = d1;
                }
            }
        }

        return village;
    }

    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addDoorsAround(this.villagerPositionsList.remove(0));
        }
    }

    private void addNewDoorsToVillageOrCreateVillage() {
        for (int i = 0; i < this.newDoors.size(); ++i) {
            VillageDoorInfo villagedoorinfo = this.newDoors.get(i);
            TofuVillage village = this.getNearestVillage(villagedoorinfo.getDoorBlockPos(), 32);

            if (village == null) {
                village = new TofuVillage(this.world);
                this.villageList.add(village);
                this.markDirty();
            }

            village.addVillageDoorInfo(villagedoorinfo);
        }

        this.newDoors.clear();
    }

    private void addDoorsAround(BlockPos central) {
        if (!this.world.isAreaLoaded(central, 16))
            return; // Forge: prevent loading unloaded chunks when checking for doors
        int i = 16;
        int j = 4;
        int k = 16;

        for (int l = -16; l < 16; ++l) {
            for (int i1 = -4; i1 < 4; ++i1) {
                for (int j1 = -16; j1 < 16; ++j1) {
                    BlockPos blockpos = central.add(l, i1, j1);

                    if (this.isWoodDoor(blockpos)) {
                        VillageDoorInfo villagedoorinfo = this.checkDoorExistence(blockpos);

                        if (villagedoorinfo == null) {
                            this.addToNewDoorsList(blockpos);
                        } else {
                            villagedoorinfo.setLastActivityTimestamp(this.tickCounter);
                        }
                    }
                }
            }
        }
    }

    /**
     * returns the VillageDoorInfo if it exists in any village or in the newDoor list, otherwise returns null
     */
    @Nullable
    private VillageDoorInfo checkDoorExistence(BlockPos doorBlock) {
        for (VillageDoorInfo villagedoorinfo : this.newDoors) {
            if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1) {
                return villagedoorinfo;
            }
        }

        for (TofuVillage village : this.villageList) {
            VillageDoorInfo villagedoorinfo1 = village.getExistedDoor(doorBlock);

            if (villagedoorinfo1 != null) {
                return villagedoorinfo1;
            }
        }

        return null;
    }

    private void addToNewDoorsList(BlockPos doorBlock) {
        EnumFacing enumfacing = BlockDoor.getFacing(this.world, doorBlock);
        EnumFacing enumfacing1 = enumfacing.getOpposite();
        int i = this.countBlocksCanSeeSky(doorBlock, enumfacing, 5);
        int j = this.countBlocksCanSeeSky(doorBlock, enumfacing1, i + 1);

        if (i != j) {
            this.newDoors.add(new VillageDoorInfo(doorBlock, i < j ? enumfacing : enumfacing1, this.tickCounter));
        }
    }

    /**
     * Check five blocks in the direction. The centerPos will not be checked.
     */
    private int countBlocksCanSeeSky(BlockPos centerPos, EnumFacing direction, int limitation) {
        int i = 0;

        for (int j = 1; j <= 5; ++j) {
            if (this.world.canSeeSky(centerPos.offset(direction, j))) {
                ++i;

                if (i >= limitation) {
                    return i;
                }
            }
        }

        return i;
    }

    private boolean positionInList(BlockPos pos) {
        for (BlockPos blockpos : this.villagerPositionsList) {
            if (blockpos.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    private boolean isWoodDoor(BlockPos doorPos) {
        IBlockState iblockstate = this.world.getBlockState(doorPos);
        Block block = iblockstate.getBlock();

        if (block instanceof BlockDoor) {
            return iblockstate.getMaterial() == Material.WOOD;
        } else {
            return false;
        }
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound nbt) {
        this.tickCounter = nbt.getInteger("Tick");
        NBTTagList nbttaglist = nbt.getTagList("TofuVillages", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            TofuVillage village = new TofuVillage();
            village.readTofuVillageDataFromNBT(nbttagcompound);
            this.villageList.add(village);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Tick", this.tickCounter);
        NBTTagList nbttaglist = new NBTTagList();

        for (TofuVillage village : this.villageList) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            village.writeTofuVillageDataToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }

        compound.setTag("TofuVillages", nbttaglist);
        return compound;
    }

    public static String fileNameForProvider(WorldProvider provider) {
        return "tofuvillages" + provider.getDimensionType().getSuffix();
    }
}