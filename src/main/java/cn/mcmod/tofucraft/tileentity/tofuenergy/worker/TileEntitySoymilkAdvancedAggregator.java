package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import cn.mcmod.tofucraft.base.tileentity.TileEntityProcessorBaseInventoried;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.mecha.BlockAdvancedAggregator;
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
    private static final int POWER = 25;
    public FluidTank input = new FluidTank(10000);

    public TileEntitySoymilkAdvancedAggregator() {
        //slot 0 1 2 3 -> input, 4 -> output
        super(10000, 5);
        this.maxTime = 100;
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
        if(this.blockType instanceof BlockAdvancedAggregator)
        	BlockAdvancedAggregator.setState(true, this.getWorld(), pos);
        markDirty();
    }

    @Override
    public void failed() {
        //Reset the process time.
        if(this.blockType instanceof BlockAdvancedAggregator)
        	BlockAdvancedAggregator.setState(false, this.getWorld(), pos);
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
    
    /**
     * @return
     */
    protected AdvancedAggregatorRecipes getRecipesResult() {
        for (AdvancedAggregatorRecipes recipes : AdvancedAggregatorRecipes.mortarRecipesList) {
            ItemStack stack = recipes.getResult(this);
            if (!(stack.isEmpty())) {
                return recipes;
            }
        }
        return null;
    }

    /**
     * @return
     */
    protected boolean isRecipes() {
        if (getRecipesResult() == null) {
            return false;
        }
        return true;
    }

    private void DrainInput() {
    	 ItemStack input1 = this.inventory.get(0);
         ItemStack input2 = this.inventory.get(1);
         ItemStack input3 = this.inventory.get(2);
         ItemStack input4 = this.inventory.get(3);
         ItemStack output1 = this.inventory.get(4);
    	if(isRecipes()){
	        ItemStack itemstack = getRecipesResult().getResult(this);
	        boolean not_full=(output1.getCount()<output1.getMaxStackSize()
	             	 &&output1.getCount()+itemstack.getCount()<=output1.getMaxStackSize());
	        if(not_full&&(output1.isEmpty()||output1.getItem() == itemstack.getItem())){ 
		        if(input.getFluidAmount()>=1000){
			        if (output1.isEmpty())
			        {
			            this.inventory.set(4, itemstack.copy());
			        }
			        else if (output1.getItem() == itemstack.getItem())
			        {
			        	output1.grow(itemstack.getCount());
			        }
			        if(!input1.getItem().hasContainerItem(input1))
			        	input1.shrink(1);
			        else
			        	this.inventory.set(0, new ItemStack(input1.getItem().getContainerItem()));
			        if(!input2.getItem().hasContainerItem(input2))
			        	input2.shrink(1);
			        else
			        	this.inventory.set(1, new ItemStack(input2.getItem().getContainerItem()));
			        if(!input3.getItem().hasContainerItem(input3))
			        	input3.shrink(1);
			        else
			        	this.inventory.set(2, new ItemStack(input3.getItem().getContainerItem()));
			        if(!input4.getItem().hasContainerItem(input4))
			        	input4.shrink(1);
			        else
			        	this.inventory.set(3, new ItemStack(input4.getItem().getContainerItem()));
			        input.drain(1000, true);
		        }
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
