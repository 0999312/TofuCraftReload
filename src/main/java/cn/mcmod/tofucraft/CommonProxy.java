package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TofuParticleType;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.tileentity.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public static CreativeTabs tab;

    public void preInit(FMLPreInitializationEvent event) {
        tab = new CreativeTabsTofu();
        new BlockLoader(event);
        new ItemLoader(event);
        TofuEntityRegister.entityRegister();


    }

    public void init(FMLInitializationEvent event) {
        TileEntityRegistry.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(TofuMain.instance, new TofuGuiHandler());

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerFluidBlockRendering(Block block, String name) {

    }

    public World getClientWorld() {
        return null;
    }

    public void spawnParticle(World world, TofuParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {

    }
}
