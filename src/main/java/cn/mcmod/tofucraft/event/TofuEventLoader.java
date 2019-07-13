package cn.mcmod.tofucraft.event;

import java.util.Random;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.plants.BlockSoybeanNether;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.world.gen.future.WorldGenCrops;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
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
    @SubscribeEvent
    public void decorateBiome(DecorateBiomeEvent.Post event)
    {
        World worldObj = event.getWorld();
        Random rand = event.getRand();
        @SuppressWarnings("deprecation")
		BlockPos pos = event.getPos();
        // Hellsoybeans
        if (rand.nextInt(600) < Math.min((Math.abs(pos.getX()) + Math.abs(pos.getZ())) / 2, 400) - 100)
        {
            if (Biome.getIdForBiome(worldObj.getBiome(pos)) == Biome.getIdForBiome(Biomes.HELL))
            {
                int k = pos.getX();
                int l = pos.getZ();
                BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                
                for (int i = 0; i < 10; ++i)
                {
                    int j1 = k + rand.nextInt(16) + 8;
                    int k1 = rand.nextInt(128);
                    int l1 = l + rand.nextInt(16) + 8;
                    mutable.setPos(j1, k1, l1);
                    
                    (new WorldGenCrops((BlockBush)BlockLoader.SOYBEAN_NETHER)
                    		{ 
                    			@Override
								protected IBlockState getStateToPlace() {
                    				return this.plantBlock.getDefaultState().withProperty(BlockSoybeanNether.AGE, 7);
                    			}
                    		})
                    		.generate(worldObj, rand, mutable);           
                }
            }
        }
    }
    
}
