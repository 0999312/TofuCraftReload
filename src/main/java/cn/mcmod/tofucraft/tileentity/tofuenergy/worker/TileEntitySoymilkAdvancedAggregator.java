package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockAdvancedAggregator;
import cn.mcmod.tofucraft.util.ItemUtils;
import cn.mcmod.tofucraft.util.OredictItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileEntitySoymilkAdvancedAggregator extends TileEntityProcessorBaseInventoried {

    public static final String TAG_TANK = "tf_tank";
    private static final int POWER_PASSIVE = 10;
    private static final int POWER = 15;

    public FluidTank input = new FluidTank(1000);

    private AdvancedAggregatorRecipes cachedRecipe = null;

    public TileEntitySoymilkAdvancedAggregator() {
        //slot 0 1 2 3 -> input, 4 -> output
        super(10000, 5);
        this.maxTime = 100;
    }

    @Override
    public boolean canWork() {
        if (energy >= POWER) { //If energy is suitable
            FluidStack drained = this.input.drain(10, false);

            cachedRecipe = AdvancedAggregatorRecipes.getBestRecipe(this.inventory);
            if (cachedRecipe == null) {
                return false;
            }

            this.maxTime = cachedRecipe.processTime;
            ItemStack output = inventory.get(4);

            if (output != ItemStack.EMPTY) {
                if (!ItemStack.areItemsEqual(output, cachedRecipe.resultItem)) {
                    return false;
                }
                if (output.getCount() + cachedRecipe.resultItem.getCount() >= output.getMaxStackSize()) {
                    return false;
                }
            }

            return drained != null && drained.amount == 10;
        }
        return false;
    }

    @Override
    public void onWork() {
        //Valid, drain power and increase process time.
        processTime++;
        drain(POWER, false);
        this.input.drain(10, true);
        if (this.blockType instanceof BlockAdvancedAggregator)
            BlockAdvancedAggregator.setState(true, this.getWorld(), pos);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        if (this.blockType instanceof BlockAdvancedAggregator)
            BlockAdvancedAggregator.setState(false, this.getWorld(), pos);
        processTime = 0;

        //Panic!
        if (getEnergyStored() < POWER + POWER_PASSIVE || this.input.getFluidAmount() <= 10) {
            fuseTime = 100;
        }
        markDirty();
    }

    @Override
    public void done() {
        ItemUtils.growOrSetInventoryItem(this.inventory, cachedRecipe.resultItem, 4);

        for (Object consume : cachedRecipe.inputItems) {
            for (int i = 0; i < 4; i++) {
                if (consume instanceof ItemStack) {
                    ItemStack is = (ItemStack) consume;
                    if (ItemStack.areItemsEqual(is, this.inventory.get(i))) {
                        this.inventory.get(i).shrink(is.getCount());
                        break;
                    }
                } else if (consume instanceof OredictItemStack) {
                    OredictItemStack odis = (OredictItemStack) consume;
                    if (odis.isMatchingItemStack(this.inventory.get(i)) && odis.getCount() <= this.inventory.get(i).getCount()) {
                        this.inventory.get(i).shrink(odis.getCount());
                        break;
                    }
                }
            }
        }

        markDirty();
    }

    @Override
    public void general() {
        if (this.energy > POWER_PASSIVE) {
            if (this.input.getFluidAmount() < this.input.getCapacity()) {
                this.drain(POWER_PASSIVE, false);
                this.input.fill(new FluidStack(BlockLoader.SOYMILK_FLUID, 1), true);
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName() + ".name", new Object());
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

    public FluidTank getTank() {
        return this.input;
    }
}
