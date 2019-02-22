package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	 	@Override
	    public void preInit(FMLPreInitializationEvent event)
	    {
	        super.preInit(event);
	        ItemLoader.registerRenders();
	    }

	    @Override
	    public void init(FMLInitializationEvent event)
	    {
	        super.init(event);
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event)
	    {
	        super.postInit(event);
	    }

}
