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

public class CategoryCompressor implements IRecipeCategory<IRecipeWrapper>{
	 protected final IDrawable background;
	  private final IDrawable icon;
	  public CategoryCompressor(IGuiHelper helper) {
		  ResourceLocation backgroundTexture = new ResourceLocation(TofuMain.MODID+":textures/gui/jei_gui.png");
		  this.icon = helper.createDrawableIngredient(new ItemStack(BlockLoader.TFCOMPRESSOR));
		  this.background = helper.createDrawable(backgroundTexture, 0, 0, 96, 26);
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
		return I18n.format("jei.tofucraft.category.compressor", new Object[0]);
	}

	@Override
	public String getUid() {
		return "tofucraft.compressor";
	}
	@Override
	public void setRecipe(IRecipeLayout arg0, IRecipeWrapper arg1, IIngredients arg2) {
		IGuiItemStackGroup items = arg0.getItemStacks();
		
		items.init(0, true, 4, 4);
		items.init(1, false, 74, 4);
	    
		items.set(arg2);
	}
	public IDrawable getIcon() {
		return icon;
	}

}
