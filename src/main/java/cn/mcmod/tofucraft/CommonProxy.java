package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TofuParticleType;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.event.TofuEventLoader;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.item.TofuOreDictLoader;
import cn.mcmod.tofucraft.tileentity.TileEntityRegistry;
import cn.mcmod.tofucraft.util.RecipesUtil;
import cn.mcmod.tofucraft.world.gen.structure.MapGenTofuVillage;
import cn.mcmod.tofucraft.world.gen.structure.tofuvillage.StructureTofuVillagePieces;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
        TileEntityRegistry.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(TofuMain.instance, new TofuGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerFluidBlockRendering(Block block, String name) {

    }
	@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
//		RecipesUtil.addShapelessRecipe(new ItemStack(ItemLoader.soyoil_bottle,1), new Object[]{
//				new ItemStack(ItemLoader.soybeans),ItemLoader.defatting_potion,Items.GLASS_BOTTLE
//		});
//		RecipesUtil.addShapelessRecipe(new ItemStack(ItemLoader.dashi_bottle,1), new Object[]{
//				Items.WATER_BUCKET,Items.COOKED_FISH,Items.GLASS_BOTTLE
//		});
//		RecipesUtil.addShapelessRecipe(new ItemStack(ItemLoader.foodset,1,11), new Object[]{
//				Items.BOWL,Items.COOKED_BEEF,Items.POTATO,Items.CARROT,Items.SUGAR,TofuOreDictLoader.Soysause,TofuOreDictLoader.Dashi
//		});
//		RecipesUtil.addShapelessRecipe(new ItemStack(ItemLoader.foodset,1,11), new Object[]{
//				Items.BOWL,Items.COOKED_PORKCHOP,Items.POTATO,Items.CARROT,Items.SUGAR,TofuOreDictLoader.Soysause,TofuOreDictLoader.Dashi
//		});
	}
    public World getClientWorld() {
        return null;
    }


    
    public void spawnParticle(World world, TofuParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {

    }
    

}
