package cn.mcmod.tofucraft.compat.jei;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryAdvancedAggregator implements IRecipeCategory<IRecipeWrapper>{
	 protected final IDrawable background;
	  private final IDrawable icon;
	  public CategoryAdvancedAggregator(IGuiHelper helper) {
		  ResourceLocation backgroundTexture = new ResourceLocation(TofuMain.MODID+":textures/gui/jei_gui.png");
		  this.icon = helper.createDrawableIngredient(new ItemStack(BlockLoader.TFAGGREGATOR));
		  this.background = helper.createDrawable(backgroundTexture, 0, 27, 96, 48);
	}
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public String getModName() {
		return TofuMain.NAME;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei.tofucraft.category.advanced_aggregator", new Object[0]);
	}

	@Override
	public String getUid() {
		return "tofucraft.advanced_aggregator";
	}
	@Override
	public void setRecipe(IRecipeLayout arg0, IRecipeWrapper arg1, IIngredients arg2) {
		IGuiItemStackGroup items = arg0.getItemStacks();
		
		items.init(0, true, 5, 5);
		items.init(1, true, 25, 5);
		items.init(2, true, 5, 25);
		items.init(3, true, 25, 25);
		items.init(4, false, 75, 15);
	    
		items.set(arg2);
	}
	public IDrawable getIcon() {
		return icon;
	}

}
