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
		AggregatorRecipes.addRecipe(new ItemStack(ItemLoader.material,1,18), new ItemStack(ItemLoader.material,1,25));
		
		AdvancedAggregatorRecipes.addRecipe(new ItemStack(Items.BUCKET), FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYMILK_FLUID, 1000)));
		AdvancedAggregatorRecipes.addRecipe(new ItemStack(ItemLoader.nigari), new ItemStack(BlockLoader.KINUTOFU));
		AdvancedAggregatorRecipes.addRecipe(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(ItemLoader.soymilk_drink,1,0));
		AdvancedAggregatorRecipes.addRecipe(new ItemStack(ItemLoader.material,1,18), new ItemStack(ItemLoader.material,1,25));
		
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(ItemLoader.mineral_soymilk),
				new ItemStack(ItemLoader.mineral_soymilk),
				FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYMILK_FLUID, 1000)),
				new ItemStack(ItemLoader.nigari)
		}, new ItemStack(ItemLoader.material,1,26));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(ItemLoader.mineral_soymilk),
				new ItemStack(ItemLoader.mineral_soymilk),
				FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYMILKHELL_FLUID, 1000)),
				new ItemStack(ItemLoader.nigari)
		}, new ItemStack(ItemLoader.material,1,27));
		
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.BUCKET),
				"foodZunda"
		}, FluidUtil.getFilledBucket(new FluidStack(BlockLoader.ZUNDASOYMILK_FLUID, 1000)));
		
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				new ItemStack(ItemLoader.material,1,15),
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,1));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"cropApple",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,2));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"cropApple",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,3));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				new ItemStack(ItemLoader.material,1,7)
		}, new ItemStack(ItemLoader.soymilk_drink,1,4));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"listAllegg",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,5));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"cropPumpkin",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,6));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"sakuraLeaves",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,7));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"foodStrawberryjelly",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,8));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"cropTea",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,9));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"foodZunda",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_drink,1,10));
		AdvancedAggregatorRecipes.addRecipe(new Object[]{
				new ItemStack(Items.GLASS_BOTTLE),
				"dyeLightBlue",
				"listAllsugar"
		}, new ItemStack(ItemLoader.soymilk_ramune,1));
    }
    
	private static void registerCrasherRecipes() {
		CrasherRecipes.addRecipe("cobblestone", new ItemStack(Blocks.GRAVEL,2));
		CrasherRecipes.addRecipe("gravel", new ItemStack(Items.FLINT));
		CrasherRecipes.addRecipe("cropSoybean", new ItemStack(ItemLoader.material,3,11));
		CrasherRecipes.addRecipe("cropAlmond", new ItemStack(ItemLoader.material,2,15));
		CrasherRecipes.addRecipe("cropPotato", new ItemStack(ItemLoader.material,2,12));
		CrasherRecipes.addRecipe(new ItemStack(ItemLoader.material,1,12), new ItemStack(ItemLoader.material,2,13));
		CrasherRecipes.addRecipe(new ItemStack(ItemLoader.material,1,6), new ItemStack(ItemLoader.material,2,7));
		CrasherRecipes.addRecipe("listAlltofu", new ItemStack(ItemLoader.material,2,19));
		CrasherRecipes.addRecipe("listAlltofuBlock", new ItemStack(ItemLoader.material,8,19));
	}
	
	private static void registerCompressorRecipes() {
		CompressorRecipes.addRecipe(new ItemStack(ItemLoader.tofu_food,4,0), new ItemStack(BlockLoader.KINUTOFU));
		CompressorRecipes.addRecipe(new ItemStack(ItemLoader.tofu_food,4,1), new ItemStack(BlockLoader.MOMENTOFU));
		CompressorRecipes.addRecipe(new ItemStack(ItemLoader.tofu_food,4,2), new ItemStack(BlockLoader.ISHITOFU));
		CompressorRecipes.addRecipe(new ItemStack(ItemLoader.tofu_food,4,3), new ItemStack(BlockLoader.GRILD));
		CompressorRecipes.addRecipe(new ItemStack(ItemLoader.tofu_food,4,9), new ItemStack(BlockLoader.TOFUZUNDA));
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.KINUTOFU),new ItemStack(BlockLoader.MOMENTOFU));
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.MOMENTOFU), new ItemStack(BlockLoader.ISHITOFU));
		CompressorRecipes.addRecipe(new ItemStack(BlockLoader.ISHITOFU),new ItemStack(BlockLoader.METALTOFU));
	}
}
