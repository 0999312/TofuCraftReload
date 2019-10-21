package cn.mcmod.tofucraft.event;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.plants.BlockSoybeanNether;
import cn.mcmod.tofucraft.item.ItemArmorBase;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.world.gen.future.WorldGenCrops;
import cn.mcmod.tofucraft.world.village.TofuVillageCollection;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Random;

public class TofuEventLoader {
    /**
     * Returns {@code true} if {@code target} is present as an element anywhere in
     * {@code array}.
     *
     * @param array an array of {@code Potion} values, possibly empty
     * @param target a primitive {@code Potion} value
     * @return {@code true} if {@code array[i] == target} for some value of {@code
     *     i}
     */
    public static boolean containsPotion(Potion[] array, Potion target) {
      for (Potion value : array) {
        if (value == target) {
          return true;
        }
      }
      return false;
    }
	public void onPotionEffectApplied(EntityLivingBase entity, PotionEffect potionEffect)
    {
        if (entity.world.isRemote || potionEffect == null || potionEffect.getIsAmbient()) return ;

        int diamondArmors = 0;
        boolean[] armorsEquipped = new boolean[]{false, false, false, false};
        for (ItemStack armor : entity.getArmorInventoryList())
        {
            if (armor != null)
            {
                if (armor.getItem() == ItemLoader.diamondhelmet)
                {
                    armorsEquipped[0] = true;
                    diamondArmors++;
                }
                if (armor.getItem() == ItemLoader.diamondchestplate)
                {
                    armorsEquipped[1] = true;
                    diamondArmors++;
                }
                if (armor.getItem() == ItemLoader.diamondhelmet)
                {
                    armorsEquipped[2] = true;
                    diamondArmors++;
                }
                if (armor.getItem() == ItemLoader.diamondboots)
                {
                    armorsEquipped[3] = true;
                    diamondArmors++;
                }
            }
        }

        if (diamondArmors > 0)
        {
            Boolean isBadEffect = potionEffect.getPotion().isBadEffect();
            if (isBadEffect)
            {
            	Potion potion = potionEffect.getPotion();
                int amplifier = potionEffect.getAmplifier();
                int duration = potionEffect.getDuration();

                switch (diamondArmors)
                {
                    case 4:
                        amplifier = 1;
                    case 3:
                        duration /= 2;
                    case 2:
                        amplifier = Math.max(0, amplifier - 1);
                    case 1:
                        duration /= 2;
                }

                for (int i = 0; i < 4; i++)
                {
                    if (armorsEquipped[i] && containsPotion(ItemArmorBase.registanceList[i], potion))
                    {
                        duration = 0;
                    }
                }

                Class<? extends PotionEffect> effectClass = potionEffect.getClass();
                try {
					Field privateduration = effectClass.getDeclaredField("duration");
					Field privateamplifier = effectClass.getDeclaredField("amplifier");
					 if (privateduration != null){
						privateduration.setAccessible(true);
						privateduration.set(potionEffect, duration);
					 }
					 if (privateamplifier != null){
						privateamplifier.setAccessible(true);
						privateamplifier.set(potionEffect, amplifier);
					 }
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
            }
        }
    }
	@SubscribeEvent
	public void onPotionEffectApplied(PotionAddedEvent event) {
		onPotionEffectApplied(event.getEntityLiving(), event.getPotionEffect());
	}
	
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

    @SubscribeEvent
    public void worldTick(WorldEvent.Load event) {
        String s = TofuVillageCollection.fileNameForProvider(event.getWorld().provider);

        TofuVillageCollection tofuVillageCollection = (TofuVillageCollection) event.getWorld().getPerWorldStorage().getOrLoadData(TofuVillageCollection.class, s);

        if (tofuVillageCollection == null) {
            tofuVillageCollection = new TofuVillageCollection(event.getWorld());
            event.getWorld().getPerWorldStorage().setData(s, tofuVillageCollection);
        } else {
            tofuVillageCollection.setWorldsForAll(event.getWorld());
        }
    }

    @SubscribeEvent
    public void worldTick(TickEvent.WorldTickEvent event) {
        String s = TofuVillageCollection.fileNameForProvider(event.world.provider);

        TofuVillageCollection tofuVillageCollection = (TofuVillageCollection) event.world.getPerWorldStorage().getOrLoadData(TofuVillageCollection.class, s);

        if (tofuVillageCollection != null) {
            tofuVillageCollection.tick();
        }
    }
    
}
