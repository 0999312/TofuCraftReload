package cn.mcmod.tofucraft.api.recipes.recipe;

import net.minecraftforge.fluids.FluidStack;

public class TofuEnergyStoragedFluid {
    private final FluidStack output;
    private final int energy;

    public TofuEnergyStoragedFluid(FluidStack output, int energy) {
        this.output = output;
        this.energy = energy;
    }

    public FluidStack getOutput() {
        return output;
    }

    public int getEnergy() {
        return energy;
    }
}
