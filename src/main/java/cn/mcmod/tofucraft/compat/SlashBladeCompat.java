package cn.mcmod.tofucraft.compat;

import cn.mcmod.tofucraft.block.BlockLoader;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.named.event.LoadEvent.PostInitEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class SlashBladeCompat {
	@SubscribeEvent
	public void InitIshiKatana(InitEvent event){
		String name = "slashblade.tofucraft.ishikatana";
	    ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
	    NBTTagCompound tag = new NBTTagCompound();
	    customblade.setTagCompound(tag);
	    ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	    ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(183));
	    ItemSlashBlade.setBaseAttackModifier(tag, 3F);
        ItemSlashBlade.TextureName.set(tag,"tofuishi_katana");
	    SlashBlade.registerCustomItemStack(name, customblade);
	    ItemSlashBladeNamed.NamedBlades.add(name);
	}
	@SubscribeEvent
	public void InitMetalKatana(InitEvent event){
		String name = "slashblade.tofucraft.metalkatana";
	    ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
	    NBTTagCompound tag = new NBTTagCompound();
	    customblade.setTagCompound(tag);
	    ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	    ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(415));
	    ItemSlashBlade.setBaseAttackModifier(tag, 6F);
        ItemSlashBlade.TextureName.set(tag,"tofumetal_katana");
	    SlashBlade.registerCustomItemStack(name, customblade);
	    ItemSlashBladeNamed.NamedBlades.add(name);
	}
	@SubscribeEvent
	public void InitDiamondKatana(InitEvent event){
		String name = "slashblade.tofucraft.diamondkatana";
	    ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
	    NBTTagCompound tag = new NBTTagCompound();
	    customblade.setTagCompound(tag);
	    ItemSlashBladeNamed.CurrentItemName.set(tag, name);
	    ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(0x1212));
	    ItemSlashBlade.setBaseAttackModifier(tag, 8F);
        ItemSlashBlade.TextureName.set(tag,"tofudiamond_katana");
	    SlashBlade.registerCustomItemStack(name, customblade);
	    ItemSlashBladeNamed.NamedBlades.add(name);
	}
	@SubscribeEvent
	public void InitRecipes(PostInitEvent event) {
		SlashBlade.addRecipe("slashblade.tofucraft.ishikatana", 
				new ShapedOreRecipe(new ResourceLocation("flammpfeil.slashblade","slashblade.tofucraft.ishikatana"),
				SlashBlade.getCustomBlade("slashblade.tofucraft.ishikatana"), new Object[]{
				 " CS",
				 "CS ",
				 "GP ",
				 Character.valueOf('P'), "string",
				 Character.valueOf('G'), "stickWood",
				 Character.valueOf('S'), BlockLoader.ISHITOFU ,
				 'C',BlockLoader.ISHITOFU
		}));
		SlashBlade.addRecipe("slashblade.tofucraft.metalkatana", 
				new ShapedOreRecipe(new ResourceLocation("flammpfeil.slashblade","slashblade.tofucraft.metalkatana"),
				SlashBlade.getCustomBlade("slashblade.tofucraft.metalkatana"), new Object[]{
				 " CS",
				 "CS ",
				 "GP ",
				 Character.valueOf('P'), "string",
				 Character.valueOf('G'), "stickWood",
				 Character.valueOf('S'), BlockLoader.METALTOFU ,
				 'C',BlockLoader.ISHITOFU
		}));
		SlashBlade.addRecipe("slashblade.tofucraft.diamondkatana", 
				new ShapedOreRecipe(new ResourceLocation("flammpfeil.slashblade","slashblade.tofucraft.diamondkatana"),
				SlashBlade.getCustomBlade("slashblade.tofucraft.diamondkatana"), new Object[]{
				 " CS",
				 "CS ",
				 "GP ",
				 Character.valueOf('P'), "string",
				 Character.valueOf('G'), "stickWood",
				 Character.valueOf('S'), BlockLoader.TOFUDIAMOND ,
				 'C',BlockLoader.ISHITOFU
		}));
	}
}
