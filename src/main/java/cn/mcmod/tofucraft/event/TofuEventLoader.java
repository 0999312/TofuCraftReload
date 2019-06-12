package cn.mcmod.tofucraft.event;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TofuEventLoader {

	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        ItemStack item = event.crafting;
		IInventory craftMatrix = event.craftMatrix;
		
		if(craftMatrix instanceof InventoryCrafting){
		InventoryCrafting craftMatrix1 = (InventoryCrafting) craftMatrix;
		IRecipe recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(TofuMain.MODID, "soymilk_cloth"));
		if(recipe!=null){
			if(!item.isEmpty()&&recipe.matches(craftMatrix1, player.world))
				player.inventory.addItemStackToInventory(new ItemStack(ItemLoader.material,1,11));
			}
		}
	}

}
