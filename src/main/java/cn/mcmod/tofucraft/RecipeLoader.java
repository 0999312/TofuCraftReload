package cn.mcmod.tofucraft;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cn.mcmod.tofucraft.api.recipes.AdvancedAggregatorRecipes;
import cn.mcmod.tofucraft.api.recipes.AggregatorRecipes;
import cn.mcmod.tofucraft.api.recipes.CompressorRecipes;
import cn.mcmod.tofucraft.api.recipes.CrasherRecipes;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;

public class RecipeLoader {
	public static List<IAction> actions = new ArrayList<IAction>();
	
	public static void Init() {
		registerCompressorRecipes();
		registerCrasherRecipes();
		registerAggregatorRecipe();
		if (Loader.isModLoaded("crafttweaker"))
        {
            doDelayTask();
        }
		actions = null;
	}

	public static void addAction(IAction action)
    {
        actions.add(action);
    }

    @Method(modid = "crafttweaker")
    public static void doDelayTask()
    {
        for (IAction act : actions)
        {
            CraftTweakerAPI.apply(act);
            if (act.describe() != null)
            {
            	TofuMain.logger.log(Level.INFO, act.describe());
            }
        }
        actions.clear();
    }
    private static void registerAggregatorRecipe() {
		AggregatorRecipes.addRecipe(new ItemStack(Items.BUCKET), FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYMILK_FLUID, 1000)));
		AggregatorRecipes.addRecipe(new ItemStack(ItemLoader.nigari), new ItemStack(BlockLoader.KINUTOFU));
		AggregatorRecipes.addRecipe(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(ItemLoader.soymilk_drink,1,0));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				new ItemStack(Items.PAPER),
				"cobblestone",
				"treeSapling"
		}, new ItemStack(ItemLoader.soymilk_drink,1,1));
    }
    
	private static void registerCrasherRecipes() {
		CrasherRecipes.addRecipe("cobblestone", new ItemStack(Blocks.GRAVEL,2));
		CrasherRecipes.addRecipe("gravel", new ItemStack(Items.FLINT));
	}
	
	private static void registerCompressorRecipes() {
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.KINUTOFU,2),new ItemStack(BlockLoader.MOMENTOFU));
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.MOMENTOFU,2), new ItemStack(BlockLoader.ISHITOFU));
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.ISHITOFU,2),new ItemStack(BlockLoader.METALTOFU));
	}
}
