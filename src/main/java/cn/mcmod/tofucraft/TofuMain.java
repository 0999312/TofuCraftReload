package cn.mcmod.tofucraft;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TofuMain.MODID, name = TofuMain.NAME, version = TofuMain.VERSION)
public class TofuMain
{
    public static final String MODID = "tofucraft";
    public static final String NAME = "TofuCraftReload";
    public static final String VERSION = "@version@";

	@Instance(TofuMain.MODID)
    public static TofuMain instance;
    
	@SidedProxy(clientSide = "cn.mcmod.tofucraft.ClientProxy",serverSide = "cn.mcmod.tofucraft.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void construct(FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	    proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
	    proxy.init(event);
	
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	    proxy.postInit(event);
	}
}
