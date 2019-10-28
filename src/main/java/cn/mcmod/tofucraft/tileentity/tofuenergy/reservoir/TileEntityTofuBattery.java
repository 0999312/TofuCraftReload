package cn.mcmod.tofucraft.tileentity.tofuenergy.reservoir;

import cn.mcmod.tofucraft.api.recipes.TofuEnergyStoragedFluidMap;
import cn.mcmod.tofucraft.api.recipes.recipe.TofuEnergyStoragedFluid;
import cn.mcmod.tofucraft.base.tileentity.TileEntityReservoirBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityTofuBattery extends TileEntityReservoirBase implements IInventory {
    public static final String TAG_INPUT_TANK = "storage_fluid";
    public static final String TAG_OUTPUT_TANK = "energized_fluid";
    public FluidTank inputTank = new FluidTankSoy(2000);
    public FluidTank outputTank = new FluidTankEnergizedSoy(2000);

    protected NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);


    public TileEntityTofuBattery() {
        super(5000);
    }

    @Override
    public void update() {

        if (!world.isRemote) {

            if (inputTank.getFluid() != null && TofuEnergyStoragedFluidMap.isEnergyStorageFluid(inputTank.getFluid())) {
                Map.Entry<FluidStack, TofuEnergyStoragedFluid> result = TofuEnergyStoragedFluidMap.getSufficientRecipe(inputTank.getFluid());
                if (inputTank.getFluid().amount >= result.getKey().amount)
                    if (energy >= energyMax / 2 + result.getValue().getEnergy()) {
                        inputTank.drain(result.getKey(), true);
                        outputTank.fill(result.getValue().getOutput(), true);
                        drain(result.getValue().getEnergy(), false);
                    }
            }
            if (outputTank.getFluid() != null && TofuEnergyStoragedFluidMap.isEnergyContainedFluid(outputTank.getFluid())) {
                Map.Entry<FluidStack, TofuEnergyStoragedFluid> result = TofuEnergyStoragedFluidMap.getReversedRecipe(outputTank.getFluid());
                if (outputTank.getFluid().amount >= result.getValue().getOutput().amount)
                    if (energy <= energyMax / 2 - result.getValue().getEnergy()) {
                        inputTank.fill(result.getKey(), true);
                        outputTank.drain(result.getValue().getOutput(), true);
                        receive(result.getValue().getEnergy(), false);
                    }
            }
        }
        //Energy sender logic
        super.update();

        this.refresh();
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

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
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
        return "container.tofucraft.tfbattery";
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inputTank.readFromNBT(compound.getCompoundTag(TAG_INPUT_TANK));
        outputTank.readFromNBT(compound.getCompoundTag(TAG_OUTPUT_TANK));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound input = inputTank.writeToNBT(new NBTTagCompound());
        NBTTagCompound output = outputTank.writeToNBT(new NBTTagCompound());
        compound.setTag(TAG_INPUT_TANK, input);
        compound.setTag(TAG_OUTPUT_TANK, output);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (facing == null) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
            switch (facing) {
                case UP:
                case EAST:
                case WEST:
                case NORTH:
                case SOUTH:
                    return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
                case DOWN:
                    return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputTank);
            }
        }
        return super.getCapability(capability, facing);
    }

    private class FluidTankSoy extends FluidTank {

        FluidTankSoy(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            if (outputTank.getFluid() == null) {
                return TofuEnergyStoragedFluidMap.isEnergyStorageFluid(fluid);
            } else {
                Map.Entry<FluidStack, TofuEnergyStoragedFluid> recipe = TofuEnergyStoragedFluidMap.getSufficientRecipe(fluid);
                return recipe != null && recipe.getValue().getOutput().getFluid() == outputTank.getFluid().getFluid();
            }
        }
    }

    private class FluidTankEnergizedSoy extends FluidTank {

        FluidTankEnergizedSoy(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            if (inputTank.getFluid() == null) {
                return TofuEnergyStoragedFluidMap.isEnergyContainedFluid(fluid);
            } else {
                Map.Entry<FluidStack, TofuEnergyStoragedFluid> recipe = TofuEnergyStoragedFluidMap.getReversedRecipe(fluid);
                return recipe != null && recipe.getKey().getFluid() == inputTank.getFluid().getFluid();
            }
        }
    }
}
