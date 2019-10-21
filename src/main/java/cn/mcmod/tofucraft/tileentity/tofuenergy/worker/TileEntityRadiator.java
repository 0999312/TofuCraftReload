package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.base.tileentity.TileEntityWorkerBase;
import cn.mcmod.tofucraft.block.BlockTofu;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityRadiator extends TileEntityWorkerBase implements ITickable {
    public static final String TAG_TANK = "tf_tank";
    private static final int WORKING_RADIUS = 4;
    private static final Random rnd = new Random();
    private FluidTank tank = new SpecificFluidTank(1600, FluidRegistry.WATER);

    public TileEntityRadiator() {
        super(500);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            for (int i = 0; i <= 20; i++) {
                BlockPos tickingPos = pos.add(
                        rnd.nextInt(2 * (WORKING_RADIUS + 1)),
                        rnd.nextInt(2 * (WORKING_RADIUS + 1)),
                        rnd.nextInt(2 * (WORKING_RADIUS + 1)));
                tickingPos.add(
                        -WORKING_RADIUS,
                        -WORKING_RADIUS,
                        -WORKING_RADIUS);
                if (world.getBlockState(tickingPos).getBlock() instanceof BlockTofu && tank.getFluidAmount() < 1600) {
                    for (int j = 0; j <= 20; j++)
                        world.getBlockState(tickingPos).getBlock().
                                updateTick(world, tickingPos, world.getBlockState(tickingPos), rnd);
                    tank.fill(new FluidStack(FluidRegistry.WATER, 10), true);
                }
            }
            drain(3, false);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tank.readFromNBT(compound.getCompoundTag(TAG_TANK));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(TAG_TANK, tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    public class SpecificFluidTank extends FluidTank {
        private final Fluid fluid;

        public SpecificFluidTank(int capacity, Fluid fluid) {
            super(capacity);
            this.fluid = fluid;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid().equals(this.fluid);
        }
    }
}
