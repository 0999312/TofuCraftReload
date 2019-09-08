package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.api.recipes.SoymilkAggregationMap;
import cn.mcmod.tofucraft.api.recipes.TofuEnergyMap;
import cn.mcmod.tofucraft.api.recipes.TofuEnergyStoragedFluidMap;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TofuParticleType;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.entity.villager.VillagerTofu1;
import cn.mcmod.tofucraft.event.TofuEventLoader;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.item.TofuOreDictLoader;
import cn.mcmod.tofucraft.tileentity.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class CommonProxy {
    public static CreativeTabs tab;

    public void preInit(FMLPreInitializationEvent event) {
        tab = new CreativeTabsTofu();
        new BlockLoader(event);
        new ItemLoader(event);
        TofuEntityRegister.entityRegister();
        MinecraftForge.EVENT_BUS.register(new TofuEventLoader());
        new TofuOreDictLoader();
    }

    public void init(FMLInitializationEvent event) {
        TofuEnergyMap.init();
        TofuEnergyStoragedFluidMap.init();
        SoymilkAggregationMap.init();
        FlintLockAmmoMap.init();

        TileEntityRegistry.init();
        VillagerTofu1.registerVillager();
        NetworkRegistry.INSTANCE.registerGuiHandler(TofuMain.instance, new TofuGuiHandler());
     
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerFluidBlockRendering(Block block, String name) {

    }
	@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		//boildEdamame
		GameRegistry.addSmelting( new ItemStack(ItemLoader.material,1,3), new ItemStack(ItemLoader.foodset,16,22), 0.25f);
		//SoyBeenParched
		GameRegistry.addSmelting( new ItemStack(ItemLoader.soybeans,1), new ItemStack(ItemLoader.material,1,6), 0.2f);

		GameRegistry.addSmelting( new ItemStack(ItemLoader.material,1,13), new ItemStack(ItemLoader.material,1,14), 0.2f);
		
		GameRegistry.addSmelting(new ItemStack(ItemLoader.tofu_food,1,0), new ItemStack(ItemLoader.tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(new ItemStack(ItemLoader.tofu_food,1,1), new ItemStack(ItemLoader.tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(new ItemStack(ItemLoader.tofu_food,1,2), new ItemStack(ItemLoader.foodset,1,16), 0.2f);
		GameRegistry.addSmelting(new ItemStack(ItemLoader.material,1,20), new ItemStack(ItemLoader.foodset,1,8), 0.2f);
		GameRegistry.addSmelting(new ItemStack(ItemLoader.foodset,1,11), new ItemStack(ItemLoader.foodset,1,12), 0.2f);
		
		GameRegistry.addSmelting(BlockLoader.KINUTOFU, new ItemStack(BlockLoader.GRILD), 0.6f);
		GameRegistry.addSmelting(BlockLoader.MOMENTOFU, new ItemStack(BlockLoader.GRILD), 0.6f);
		
	}
    public World getClientWorld() {
        return null;
    }


    
    public void spawnParticle(World world, TofuParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {

    }
    

}
