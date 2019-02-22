package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public static CreativeTabs tab;

	public void preInit(FMLPreInitializationEvent event)
    {
		tab=new CreativeTabsTofu();
		new ItemLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
