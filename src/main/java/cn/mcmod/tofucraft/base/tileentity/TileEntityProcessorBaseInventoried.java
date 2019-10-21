package cn.mcmod.tofucraft.base.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public abstract class TileEntityProcessorBaseInventoried extends TileEntityProcessorBase implements IInventory {

    /*
    * Comment:
    * This is an inventoried version of TileEntityProcessorBase, which cuts off most part of inventory handling in other
    * machines.
    * */
    protected NonNullList<ItemStack> inventory;
    protected int size;

    public TileEntityProcessorBaseInventoried(int energyMax, int size) {
        super(energyMax);
        this.size = size;
        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.get(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int i1) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(inventory, i, i1);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        return ItemStackHelper.getAndRemove(inventory, i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        inventory.set(i, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        if (this.world.getTileEntity(this.pos) != this) {

            return false;

        } else {

            return entityPlayer.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;

        }
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {
        markDirty();
    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {
        markDirty();
    }

    @Override
    public int getField(int i) {
        switch (i) {
            case 0:
                return processTime;
            case 1:
                return maxTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int i, int i1) {
        switch (i) {
            case 0:
                this.processTime = i1;
                break;
            case 1:
                this.processTime = i1;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
