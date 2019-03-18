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
			player.inventory.addItemStackToInventory(new ItemStack(ItemLoader.material,1,11));
		}
	}
	
	@SubscribeEvent
	public void onBonemeal(BonemealEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        Random rand = world.rand;
        IBlockState block = event.getBlock();
        int posX = event.getPos().getX();
        int posY = event.getPos().getY();
        int posZ = event.getPos().getZ();
        int var11;
        int var12;
        int var13;
        if (block instanceof BlockTofuBase)
        {
            if (!world.isRemote)
            {
                label133:

                for (var12 = 0; var12 < 32; ++var12)
                {
                    var13 = posX;
                    int var14 = posY + 1;
                    int var15 = posZ;

                    for (int var16 = 0; var16 < var12 / 16; ++var16)
                    {
                        var13 += rand.nextInt(3) - 1;
                        var14 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                        var15 += rand.nextInt(3) - 1;

                        if (!(world.getBlockState(event.getPos()).getBlock() instanceof BlockTofuBase) || world.getBlockState(new BlockPos(var13, var14-1, var15)).isNormalCube())
                        {
                            continue label133;
                        }
                    }

                    if (world.isAirBlock(new BlockPos(var13, var14, var15)))
                    {
                        if (rand.nextInt(10) < 5)
                        {
                            if (BlockLoader.LEEK.canBlockStay(world, new BlockPos(var13, var14, var15), BlockLoader.LEEK.getDefaultState()))
                            {
                                world.setBlockState(new BlockPos(var13, var14, var15), BlockLoader.LEEK.getDefaultState());

                            }
                        }
                        else
                        {
                            world.getBiomeForCoordsBody(new BlockPos(var13, var14, var15)).plantFlower(world, rand, new BlockPos(var13, var14, var15));
                        }
                    }
                }
            }
            event.setResult(Result.ALLOW);
        }
	}
}
