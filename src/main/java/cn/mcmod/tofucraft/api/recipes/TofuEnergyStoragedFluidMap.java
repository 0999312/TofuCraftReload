package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.api.recipes.recipe.TofuEnergyStoragedFluid;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class TofuEnergyStoragedFluidMap {
    private static HashMap<FluidStack, TofuEnergyStoragedFluid> recipes = new HashMap<>();

    public static void init(){
        register(new FluidStack(BlockLoader.SOYMILKHELL_FLUID, 100),
                new TofuEnergyStoragedFluid(new FluidStack(BlockLoader.NIGARI_FLUID, 100), 100));
    }

    public static void register(FluidStack fluid, TofuEnergyStoragedFluid recipe){
        recipes.put(fluid, recipe);
    }

    public static HashMap<FluidStack, TofuEnergyStoragedFluid> getRecipes() {
        return recipes;
    }

    public static boolean isEnergyStorageFluid(FluidStack fluid){
        for (FluidStack key : recipes.keySet()){
            if (key.getFluid() == fluid.getFluid())
                return true;
        }
        return false;
    }

    public static boolean isEnergyContainedFluid(FluidStack fluid){
        for (TofuEnergyStoragedFluid value : recipes.values()){
            if (value.getOutput().getFluid() == fluid.getFluid())
                return true;
        }
        return false;
    }

    public static Map.Entry<FluidStack, TofuEnergyStoragedFluid> getSufficientRecipe(FluidStack stack){
        for (Map.Entry<FluidStack, TofuEnergyStoragedFluid> entry : recipes.entrySet()){
            if (entry.getKey().getFluid() == stack.getFluid())
                return entry;
        }
        return null;
    }

    public static Map.Entry<FluidStack, TofuEnergyStoragedFluid> getReversedRecipe(FluidStack stack){
        for (Map.Entry<FluidStack, TofuEnergyStoragedFluid> entry : recipes.entrySet()){
            if (entry.getValue().getOutput().getFluid() == stack.getFluid())
                return entry;
        }

        return null;
    }
}
