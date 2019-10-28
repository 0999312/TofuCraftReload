package cn.mcmod.tofucraft;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cn.mcmod.tofucraft.api.recipes.CompressorRecipes;
import cn.mcmod.tofucraft.api.recipes.CrasherRecipes;
import cn.mcmod.tofucraft.block.BlockLoader;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;

public class RecipeLoader {
	public static List<IAction> actions = new ArrayList<IAction>();
	
	public static void Init() {
		registerCompressorRecipes();
		registerCrasherRecipes();
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
