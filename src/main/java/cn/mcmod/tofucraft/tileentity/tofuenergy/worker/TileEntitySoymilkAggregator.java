package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.AggregatorRecipes;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockAggregator;
import cn.mcmod.tofucraft.util.ItemUtils;
import cn.mcmod.tofucraft.util.RecipesUtil;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntitySoymilkAggregator extends TileEntityProcessorBaseInventoried implements ISidedInventory {

    public static final String TAG_TANK = "tf_tank";
    private static final int POWER_PASSIVE = 10;
    private static final int POWER = 5;

    public FluidTank input = new FluidTank(1000);
    private Map.Entry<Object, ItemStack> cachedRecipe;

    private IItemHandler handlerInput = new SidedInvWrapper(this, EnumFacing.UP);
    private IItemHandler handlerOutput = new SidedInvWrapper(this, EnumFacing.DOWN);

    public TileEntitySoymilkAggregator() {
        //slot 0 -> input, 1 -> output
        super(5000, 2);
        this.maxTime = 500;
    }

    @Override
    public boolean canWork() {
        if (energy >= POWER) { //If energy is suitable
            if (cachedRecipe != null) {
                //Check if current recipe is the same as cached, prevent force refreshing.
                if (!RecipesUtil.compareItems(cachedRecipe.getKey(), this.inventory.get(0))) {
                    cachedRecipe = AggregatorRecipes.getResult(this.inventory.get(0));
                    processTime = 0;
                }
            } else {
                cachedRecipe = AggregatorRecipes.getResult(this.inventory.get(0));
            }
        }
        // If:
        // 1. have suitable power
        // 2. have suitable recipe
        // 3. is the recipe output equals to the current output
        // 4. Is the current output not full.
        return energy >= POWER + POWER_PASSIVE &&
                cachedRecipe != null &&
                input.getFluidAmount() >= 10 &&
                (inventory.get(1).isEmpty() ||
                        (ItemStack.areItemsEqual(cachedRecipe.getValue(), inventory.get(1)) &&
                                inventory.get(1).getCount() < inventory.get(1).getMaxStackSize()));
    }

    @Override
    public void onWork() {
        //Valid, drain power and increase process time.
        processTime++;
        drain(POWER, false);
        input.drain(1, true);
        if (this.blockType instanceof BlockAggregator)
            BlockAggregator.setState(true, this.getWorld(), pos);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        if (this.blockType instanceof BlockAggregator)
            BlockAggregator.setState(false, this.getWorld(), pos);
        processTime = 0;
        markDirty();

        //Panic!
        if (getEnergyStored() <= POWER || input.getFluidAmount() <= 10) {
            fuseTime = 100;
        }
    }

    @Override
    public void done() {
        ItemUtils.growOrSetInventoryItem(this.inventory, cachedRecipe.getValue(), 1);
        this.inventory.get(0).shrink(ItemUtils.getSomeAmount(cachedRecipe.getKey()));
        markDirty();
    }

    @Override
    public void general() {
        if (getEnergyStored() >= POWER_PASSIVE) {
            if (this.input.getFluidAmount() < this.input.getCapacity()) {
                drain(POWER_PASSIVE, false);
                input.fill(new FluidStack(BlockLoader.SOYMILK_FLUID, 1), true);
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName() + ".name", new Object());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == null)
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

        switch (facing) {
            case SOUTH:
            case NORTH:
            case EAST:
            case WEST:
                return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
            default:
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == null)
            return (T) handlerInput;

        switch (facing) {
            case WEST:
            case EAST:
            case NORTH:
            case SOUTH:
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(input);
            case DOWN:
                return (T) handlerOutput;
            default:
                return (T) handlerInput;
        }
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

    @Override
    public int[] getSlotsForFace(EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN) {
            return new int[]{1};
        }
        return new int[]{0};
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, EnumFacing enumFacing) {
        return isItemValidForSlot(i, itemStack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, EnumFacing enumFacing) {
        return isItemValidForSlot(i, itemStack);
    }
}
