package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.mecha.BlockTFOven;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityTFOven extends TileEntityProcessorBaseInventoried implements ISidedInventory {

    private static final int POWER = 10;

    public TileEntityTFOven() {
        //slot 0 -> input, 1 -> output
        super(5000, 2);
        this.maxTime = 200;
    }

    @Override
    public boolean canWork() {
        if (energy >= POWER) { //If energy is suitable
            if (((ItemStack) this.inventory.get(0)).isEmpty()) {
                return false;
            } else {
                ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0));

                if (itemstack.isEmpty()) {
                    return false;
                } else {
                    ItemStack itemstack1 = this.inventory.get(1);

                    if (itemstack1.isEmpty()) {
                        return true;
                    } else if (!itemstack1.isItemEqual(itemstack)) {
                        return false;
                    } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                    {
                        return true;
                    } else {
                        return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onWork() {
        //Valid, drain power and increase process time.
        processTime++;
        if (this.blockType instanceof BlockTFOven)
            BlockTFOven.setState(true, this.getWorld(), pos);
        drain(POWER, false);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        if (this.blockType instanceof BlockTFOven)
            BlockTFOven.setState(false, this.getWorld(), pos);
        processTime = 0;
        if (getEnergyStored() <= POWER) {
            fuseTime = 100;
        }
        markDirty();
    }

    @Override
    public void done() {
        //Output outputs in the cached recipe, and do everything needed.
        ItemStack itemstack = this.inventory.get(0);
        ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
        ItemStack itemstack2 = this.inventory.get(1);

        if (itemstack2.isEmpty()) {
            this.inventory.set(1, itemstack1.copy());
        } else if (itemstack2.getItem() == itemstack1.getItem()) {
            itemstack2.grow(itemstack1.getCount());
        }

        itemstack.shrink(1);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper((ISidedInventory) this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper((ISidedInventory) this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper((ISidedInventory) this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName() + ".name", new Object());
    }

    @Override
    public String getName() {
        return "container.tofucraft.tfoven";
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{0};
    private static final int[] SLOTS_SIDES = new int[]{1};

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN) {
            return SLOTS_BOTTOM;
        } else {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) return true;
        return false;
    }
}
