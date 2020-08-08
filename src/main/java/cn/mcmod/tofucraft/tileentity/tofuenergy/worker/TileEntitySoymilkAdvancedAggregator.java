package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockAdvancedAggregator;
import cn.mcmod.tofucraft.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

public class TileEntitySoymilkAdvancedAggregator extends TileEntityProcessorBaseInventoried {

    public static final String TAG_TANK = "tf_tank";
    private static final int POWER_PASSIVE = 10;
    private static final int POWER = 15;

    public FluidTank input = new FluidTank(1000);

    public TileEntitySoymilkAdvancedAggregator() {
        //slot 0 1 2 3 -> input, 4 -> output
        super(10000, 5);
        this.maxTime = 100;
    }

    private ItemStack getResult() {
        ArrayList<ItemStack> inventoryList = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
          if (!this.inventory.get(i).isEmpty()) {
            inventoryList.add(this.inventory.get(i).copy());
          }
        }
    	return AdvancedAggregatorRecipes.instance().getResult(inventoryList);
	}
    
    @Override
    public boolean canWork() {
        return energy >= POWER &&
                input.getFluidAmount() >= 10 &&
                !getResult().isEmpty() &&
                (inventory.get(4).isEmpty() ||
                        (ItemStack.areItemsEqual(inventory.get(4), getResult()) &&
                                inventory.get(4).getCount() < inventory.get(4).getMaxStackSize()));
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
        ItemUtils.growOrSetInventoryItem(this.inventory, getResult(), 4);
	    for(int i=0;i<4;i++){
	    	if(!(this.inventory.get(i).getItem().getContainerItem(this.inventory.get(i)).isEmpty())){
	    		if(this.inventory.get(i).getCount()==1){
	    		this.inventory.set(i, this.inventory.get(i).getItem().getContainerItem(this.inventory.get(i)).copy());
	    		}
	    		else this.decrStackSize(i, 1);
	    		Block.spawnAsEntity(getWorld(), getPos(), this.inventory.get(i).getItem().getContainerItem(this.inventory.get(i).copy()));
	    	}else this.decrStackSize(i, 1);
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
