package cn.mcmod.tofucraft.event;

import java.util.Random;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuBase;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.util.RecipesUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
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
		if(event.craftMatrix instanceof InventoryCrafting) {
			if (!item.isEmpty() && recipe.matches((InventoryCrafting) craftMatrix, player.world)) {
				player.inventory.addItemStackToInventory(new ItemStack(ItemLoader.material, 1, 11));
			}
		}
	}

}
