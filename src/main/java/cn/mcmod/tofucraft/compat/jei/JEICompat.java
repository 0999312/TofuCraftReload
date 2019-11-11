package cn.mcmod.tofucraft.compat.jei;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.gui.GuiTFAdvancedAggreagator;
import cn.mcmod.tofucraft.gui.GuiTFAggreagator;
import cn.mcmod.tofucraft.gui.GuiTFCompressor;
import cn.mcmod.tofucraft.gui.GuiTFCrasher;
import cn.mcmod.tofucraft.gui.GuiTFOven;
import cn.mcmod.tofucraft.inventory.ContainerTFAdvancedAggregator;
import cn.mcmod.tofucraft.inventory.ContainerTFAggregator;
import cn.mcmod.tofucraft.inventory.ContainerTFCompressor;
import cn.mcmod.tofucraft.inventory.ContainerTFCrasher;
import cn.mcmod.tofucraft.inventory.ContainerTFOven;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICompat implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		
		registry.addRecipes(CrusherRecipeMaker.getRecipes(jeiHelpers),"tofucraft.crusher");
		registry.addRecipes(CompressorRecipeMaker.getRecipes(jeiHelpers),"tofucraft.compressor");
		registry.addRecipes(AggregatorRecipeMaker.getRecipes(jeiHelpers),"tofucraft.aggregator");
		registry.addRecipes(AdvancedAggregatorRecipeMaker.getRecipes(jeiHelpers),"tofucraft.advanced_aggregator");

		registry.addRecipeClickArea(GuiTFCrasher.class, 68,15, 28, 17,"tofucraft.crusher");
		registry.addRecipeClickArea(GuiTFCompressor.class, 68,15, 28, 17,"tofucraft.compressor");
		registry.addRecipeClickArea(GuiTFAggreagator.class, 73,31, 30, 20,"tofucraft.aggregator");
		registry.addRecipeClickArea(GuiTFAdvancedAggreagator.class, 80,27, 30, 20,"tofucraft.advanced_aggregator");
		registry.addRecipeClickArea(GuiTFOven.class, 68,15, 28, 17,VanillaRecipeCategoryUid.SMELTING);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerTFCrasher.class,"tofucraft.crusher", 0, 1, 2, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTFCompressor.class,"tofucraft.compressor", 0, 1, 2, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTFAggregator.class,"tofucraft.aggregator", 0, 1, 2, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTFAdvancedAggregator.class,"tofucraft.advanced_aggregator", 0, 4, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTFOven.class,VanillaRecipeCategoryUid.SMELTING, 0, 1, 2, 36);
		
		registry.addRecipeCatalyst(new ItemStack(BlockLoader.TFCRASHER),"tofucraft.crusher");
		registry.addRecipeCatalyst(new ItemStack(BlockLoader.TFCOMPRESSOR),"tofucraft.compressor");
		registry.addRecipeCatalyst(new ItemStack(BlockLoader.TFAGGREGATOR),"tofucraft.aggregator");
		registry.addRecipeCatalyst(new ItemStack(BlockLoader.TFAdvancedAGGREGATOR),"tofucraft.advanced_aggregator");
		registry.addRecipeCatalyst(new ItemStack(BlockLoader.TFOVEN),VanillaRecipeCategoryUid.SMELTING);
	}
	
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new IRecipeCategory[]{
				new CategoryCrusher(registry.getJeiHelpers().getGuiHelper()),
				new CategoryCompressor(registry.getJeiHelpers().getGuiHelper()),
				new CategoryAggregator(registry.getJeiHelpers().getGuiHelper()),
				new CategoryAdvancedAggregator(registry.getJeiHelpers().getGuiHelper()),
		}
	);
		
	}
	
}
