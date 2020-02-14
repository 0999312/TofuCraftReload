package cn.mcmod.tofucraft.tileentity.tofuenergy.sender;

import cn.mcmod.tofucraft.api.recipes.TofuEnergyMap;
import cn.mcmod.tofucraft.base.item.energyItem.IEnergyExtractable;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;
import cn.mcmod.tofucraft.block.mecha.BlockTFStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityTFStorage extends TileEntitySenderBase implements IInventory {

    /*
     * Comment:
     * This is a rewritten version of Tofu Force Storage, to fix some bugs and make it fit to the bigger skeleton.
     * Most part of working mechanism is rewritten, but there are codes that I can't even understand...
     *
     * Program logic:
     * 1. Check if the machine is idle.
     * 2. If true, then check if there's proper material to consume, then add to workload.
     * 3. If false, work and give out energy
     *
     * */

    private static final int POWER = 10;
    private FluidTank tank = new TFStorageTank(2000);
    protected NonNullList<ItemStack> inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    private int workload = 0;
    private int current_workload = 0;

    public TileEntityTFStorage() {
        super(5000);
    }

    @Override
    public void update() {
        boolean worked = false;

        //Update energy sender logic
        super.update();

        if (this.world.isRemote) return;

        //Transform workload to power
        if (workload > 0 && getEnergyStored() < getMaxEnergyStored()) {
            workload -= receive(Math.min(workload, POWER), false);
            worked = true;
        }

        //Consume beans inside machine
        ItemStack from = this.inventory.get(0);
        FluidStack milk = getTank().getFluid();
        if (workload == 0) {
            if (from.getItem() instanceof IEnergyExtractable) {
                IEnergyExtractable symbol = (IEnergyExtractable) from.getItem();
                workload += symbol.drain(from, POWER * 20, false);
            } else if (TofuEnergyMap.getFuel(from) != -1) {
                workload += TofuEnergyMap.getFuel(from);
                from.shrink(1);
            }

            if (milk != null) {
                Map.Entry<FluidStack, Integer> recipe = TofuEnergyMap.getLiquidFuel(milk);
                if (recipe != null) {
                    tank.drain(recipe.getKey().amount, true);
                    workload += recipe.getValue();
                }
            }
            current_workload = workload;
        }

        final IBlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.withProperty(BlockTFStorage.LIT, worked));
        this.markDirty();
    }

    public FluidTank getTank() {
        return this.tank;
    }

    protected void refresh() {
        if (hasWorld() && !world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), state, state, 11);
        }
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

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(inventory, index, count);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

        inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {

            return false;

        } else {

            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;

        }
    }

    @Override
    public void openInventory(EntityPlayer player) {
        this.markDirty();
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int par1) {
        return this.current_workload == 0 ? 0 : ((this.current_workload - this.workload) * par1 / this.current_workload);
    }

    public int getField(int id) {

        switch (id) {
            case 0:
                return energy;
            case 1:
                return workload;
            case 2:
                return current_workload;
            default:
                return 0;

        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.energy = value;
                break;
            case 1:
                this.workload = value;
                break;
            case 2:
                this.current_workload = value;
                break;
        }
    }

    public int getFieldCount() {

        return 1;

    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public String getName() {
        return "container.tofucraft.tfstorage";
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName() + ".name", new Object());
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound) {
        NBTTagCompound ret = super.writeToNBT(par1nbtTagCompound);
        writePacketNBT(ret);
        return ret;
    }

    @Nonnull
    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        readPacketNBT(par1nbtTagCompound);
    }

    public void writePacketNBT(NBTTagCompound cmp) {
        ItemStackHelper.saveAllItems(cmp, this.inventory);
        cmp.setInteger("workload", this.workload);
        cmp.setInteger("current", this.current_workload);

        NBTTagCompound tankTag = this.tank.writeToNBT(new NBTTagCompound());

        cmp.setTag("Tank", tankTag);
    }

    public void readPacketNBT(NBTTagCompound cmp) {
        this.inventory =
                NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(cmp, this.inventory);

        this.workload = cmp.getInteger("workload");
        this.current_workload = cmp.getInteger("current");

        this.tank.readFromNBT(cmp.getCompoundTag("Tank"));
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writePacketNBT(tag);
        return new SPacketUpdateTileEntity(pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        readPacketNBT(packet.getNbtCompound());
    }

    private class TFStorageTank extends FluidTank {
        TFStorageTank(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return super.canFillFluidType(fluid) && TofuEnergyMap.getLiquidFuel(fluid) != null;
        }

        @Override
        protected void onContentsChanged() {
            TileEntityTFStorage.this.refresh();
        }
    }

}