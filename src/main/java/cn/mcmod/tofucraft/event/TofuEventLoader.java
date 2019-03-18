package cn.mcmod.tofucraft.event;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.util.RecipesUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TofuEventLoader {

	
	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        ItemStack item = event.crafting;
		IInventory craftMatrix = event.craftMatrix;
		
		IRecipe recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(TofuMain.MODID, "soymilk_cloth"));
		if(!item.isEmpty()&&recipe.matches((InventoryCrafting) craftMatrix, player.world)){
			System.out.println("OMO");
			player.inventory.addItemStackToInventory(new ItemStack(ItemLoader.material,1,11));
		}else{
			System.out.println("OWO");
		}
	}
	
}
