package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockTFStorage;
import cn.mcmod.tofucraft.item.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityTFStorage extends TileEntity implements ITickable, IInventory, IEnergyStorage {
    public FluidTank tank = new FluidTank(BlockLoader.SOYMILK_FLUID, 0, 2000) {
        @Override
        protected void onContentsChanged() {
            TileEntityTFStorage.this.refresh();
        }
    };

    private int prosseceTime;
    public int tfAmount = 0;

    public int tfCapacity = 5000;

    @Override
    public void update() {
        if (this.world.isRemote) {
            return;
        }

        if (0 < prosseceTime && prosseceTime < 200) {
            ++prosseceTime;
        }

        if (prosseceTime == 0 && tfAmount < tfCapacity) {
            ItemStack processStack = this.inventory.get(0);
            FluidStack fluidStack = getTank().getFluid();


            if (fluidStack != null && fluidStack.amount >= 100) {
                this.tank.drain(100, true);
                prosseceTime = 1;
            }

            if (processStack.getItem() == ItemLoader.tofu_food) {
                processStack.shrink(1);
                prosseceTime = 1;
            }
        }

        if (this.tfAmount >= 50) {
            ItemStack processStack = this.inventory.get(0);
            if (processStack.getItem() instanceof ItemAxeBasic || processStack.getItem() instanceof ItemPickaxeBasic || processStack.getItem() instanceof ItemShovelBasic || processStack.getItem() instanceof ItemSwordBasic) {
                if (this.world.getWorldTime() % 100 == 0) {
                    final IBlockState state = this.world.getBlockState(this.pos);


                    if (processStack.getItemDamage() < processStack.getMaxDamage()) {
                        processStack.setItemDamage(processStack.getItemDamage() - 1);
                        tfAmount -= 50;
                        this.markDirty();

                        this.world.setBlockState(this.pos, state.withProperty(BlockTFStorage.LIT, this.isProsseced()));

                        this.validate();
                        this.world.setTileEntity(this.pos, this);
                    }
                }
            }
        }

        final IBlockState state = this.world.getBlockState(this.pos);

        if (state.getBlock() instanceof BlockTFStorage && state.getValue(BlockTFStorage.LIT) != this.isProsseced()) {
            this.markDirty();

            this.world.setBlockState(this.pos, state.withProperty(BlockTFStorage.LIT, this.isProsseced()));

            this.validate();
            this.world.setTileEntity(this.pos, this);
        }

        if (prosseceTime >= 200) {
            prosseceTime = 0;
            if (tfAmount < tfCapacity) {
                tfAmount += 100;
            }
        }
    }

    public boolean isProsseced() {
        return this.prosseceTime > 0;
    }

    public int getProsseceTime() {
        return this.prosseceTime;
    }

    @SideOnly(Side.CLIENT)
    public FluidTank getClientTank() {
        return this.tank;
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
    public void markDirty() {
        super.markDirty();
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
        return this.prosseceTime * par1 / 200;
    }

    public int getField(int id) {

        switch (id) {
            case 0:
                return this.prosseceTime;
            default:
                return 0;

        }
    }


    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.prosseceTime = value;
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

    protected NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

    @Override
    public String getName() {
        return "container.tofucraft.tfstorage";
    }

    @Nullable
    public ITextComponent getDisplayName() {
        return new TextComponentString(this.getName());
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
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }


    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        } else if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
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
        cmp.setInteger("ProsseceTime", (short) this.prosseceTime);

        cmp.setInteger("TFAmount", (short) this.tfAmount);

        NBTTagCompound tankTag = this.tank.writeToNBT(new NBTTagCompound());

        cmp.setTag("Tank", tankTag);
    }

    public void readPacketNBT(NBTTagCompound cmp) {
        this.inventory =
                NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(cmp, this.inventory);

        this.prosseceTime = cmp.getInteger("ProsseceTime");
        this.tfAmount = cmp.getInteger("TFAmount");

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

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!simulate) {
            return 20;
        } else {
            return 0;
        }
    }

    @Override
    public int getEnergyStored() {
        return this.tfAmount;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.tfCapacity;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}