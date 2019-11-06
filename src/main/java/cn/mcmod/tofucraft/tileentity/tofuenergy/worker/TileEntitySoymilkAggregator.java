package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.AggregatorRecipes;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockAggregator;
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

public class TileEntitySoymilkAggregator extends TileEntityProcessorBaseInventoried {

    public static final String TAG_TANK = "tf_tank";
    private static final int POWER = 10;
    public FluidTank input = new FluidTank(5000);

    public TileEntitySoymilkAggregator() {
        //slot 0 -> input, 1 -> output
        super(5000, 2);
        this.maxTime = 200;
    }

    @Override
    public boolean canWork() {
        if (energy >= POWER) { //If energy is suitable
        	if(input.canFill()) return true;
        }
        return false;
    }

    @Override
    public void onWork() {
        //Valid, drain power and increase process time.
        processTime++;
        drain(POWER, false);
        if(this.blockType instanceof BlockAggregator)
        	BlockAggregator.setState(true, this.getWorld(), pos);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        if(this.blockType instanceof BlockAggregator)
        	BlockAggregator.setState(false, this.getWorld(), pos);
        processTime = 0;
        markDirty();
    }

    @Override
    public void done() {
    	input.fill(new FluidStack(BlockLoader.SOYMILK_FLUID, 100), true);
        markDirty();
    }

    @Override
    public void general() {
    	DrainInput();
    }
    private void DrainInput() {
    	 //Output outputs in the cached recipe, and do everything needed.
        ItemStack itemstack = this.inventory.get(0);
        ItemStack itemstack1 = AggregatorRecipes.getResult(this.inventory.get(0));
        ItemStack itemstack2 = this.inventory.get(1);
        boolean not_full=(itemstack2.getCount()<itemstack2.getMaxStackSize()
            	 &&itemstack2.getCount()+itemstack1.getCount()<=itemstack2.getMaxStackSize());
        if(!(itemstack.isEmpty())&&!(itemstack1.isEmpty())
        &&not_full&&(itemstack2.isEmpty()||itemstack2.getItem() == itemstack1.getItem())){ 
	        if(input.getFluidAmount()>=1000){
		        if (itemstack2.isEmpty())
		        {
		            this.inventory.set(1, itemstack1.copy());
		        }
		        else if (itemstack2.getItem() == itemstack1.getItem())
		        {
		            itemstack2.grow(itemstack1.getCount());
		        }
		
		        if(!itemstack.getItem().hasContainerItem(itemstack))
		        	itemstack.shrink(1);
		        else  this.inventory.set(0, new ItemStack(itemstack.getItem().getContainerItem()));
		        input.drain(1000, true);
	        }

    	}
	}
    
    @Override
    public ITextComponent getDisplayName() {
    	return new TextComponentTranslation(getName()+".name", new Object());
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
