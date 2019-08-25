package cn.mcmod.tofucraft.api.recipes.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SoymilkAggregate {
    private final int energy;
    private final ItemStack result;
    private final FluidStack input;

    public SoymilkAggregate(int energy, ItemStack result, FluidStack input) {
        this.energy = energy;
        this.result = result;
        this.input = input;
    }

    public int getEnergy() {
        return energy;
    }

    public ItemStack getResult() {
        return result;
    }

    public FluidStack getInput() {
        return input;
    }
}
