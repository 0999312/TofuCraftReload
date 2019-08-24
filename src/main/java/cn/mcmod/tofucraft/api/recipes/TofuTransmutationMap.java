package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.api.recipes.recipe.TofuTransmutation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.HashMap;
import java.util.List;

public class TofuTransmutationMap {
    private static HashMap<IBlockState, TofuTransmutation> recipes;

    public static void init(){
        register(Blocks.STONE.getDefaultState(), new TofuTransmutation(0.1f, 100, Blocks.DIAMOND_BLOCK.getDefaultState()));
    }

    public static void register(IBlockState state, TofuTransmutation result){
        recipes.put(state, result);
    }

    public static HashMap<IBlockState, TofuTransmutation> getRecipes() {
        return recipes;
    }

    public static TofuTransmutation findRecipe(IBlockState state){
        return recipes.getOrDefault(state, null);
    }
}
