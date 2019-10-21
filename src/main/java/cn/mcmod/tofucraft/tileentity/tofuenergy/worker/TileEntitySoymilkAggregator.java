package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.SoymilkAggregationMap;
import cn.mcmod.tofucraft.api.recipes.recipe.SoymilkAggregate;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntitySoymilkAggregator extends TileEntityProcessorBaseInventoried {

    public static final String TAG_TANK = "tf_tank";
    private static final int POWER = 10;
    public FluidTank input = new SoyTank(1000);
    private Map.Entry<Object, SoymilkAggregate> cached;

    public TileEntitySoymilkAggregator() {
        //slot 0 -> input, 1 -> output
        super(5000, 2);
    }


    @Override
    public boolean canWork() {

        if (energy >= POWER) { //If energy is suitable
            Map.Entry<Object, SoymilkAggregate> recipe = SoymilkAggregationMap.getPossibleRecipe(inventory.get(0), input.getFluid());

            if (recipe != null) { //If recipe is valid
                if (cached != null && !cached.getValue().getResult().isItemEqual(recipe.getValue().getResult()))
                    processTime = 0;
                maxTime = recipe.getValue().getEnergy() / POWER;
                cached = recipe;
                markDirty();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onWork() {
        //Valid, drain power and increase process time.
        processTime++;
        drain(POWER, false);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        processTime = 0;
        markDirty();
    }

    @Override
    public void done() {
        //Output outputs in the cached recipe, and do everything needed.
        inventory.set(0, SoymilkAggregationMap.castToSuitableItemstack(cached.getKey(), inventory.get(0)));
        if (inventory.get(1).isEmpty())
            inventory.set(1, cached.getValue().getResult());
        else
            inventory.get(1).setCount(inventory.get(1).getCount() + cached.getValue().getResult().getCount());
        input.drain(cached.getValue().getInput(), true);
        markDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(input);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == 0;
    }

    @Override
    public String getName() {
        return "container.tofucraft.aggregator";
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag(TAG_TANK, input.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        input.readFromNBT(compound.getCompoundTag(TAG_TANK));
    }

    private static final class SoyTank extends FluidTank {

        SoyTank(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return SoymilkAggregationMap.getFluidValid(fluid);
        }
    }
}
