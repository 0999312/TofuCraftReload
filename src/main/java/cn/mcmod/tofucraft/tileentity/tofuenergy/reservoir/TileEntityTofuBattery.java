package cn.mcmod.tofucraft.tileentity.tofuenergy.reservoir;

import cn.mcmod.tofucraft.api.recipes.TofuEnergyStoragedFluidMap;
import cn.mcmod.tofucraft.api.recipes.recipe.TofuEnergyStoragedFluid;
import cn.mcmod.tofucraft.base.tileentity.TileEntityReservoirBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityTofuBattery extends TileEntityReservoirBase {
    public static final String TAG_INPUT_TANK = "storage_fluid";
    public static final String TAG_OUTPUT_TANK = "energized_fluid";
    public FluidTank inputTank = new FluidTankSoy(2000);
    public FluidTank outputTank = new FluidTankEnergizedSoy(2000);

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

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (facing == null) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
            switch (facing) {
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
