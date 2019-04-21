package cn.mcmod.tofucraft;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TofuMain.MODID)
public class TofuConfig {
    @Config.Ignore
    private final static String config = TofuMain.MODID + ".config.";

    @Config.LangKey(config + "dimension_id")
    @Config.RequiresMcRestart
    @Config.Comment("What ID number to assign to the TofuWorld dimension. Change if you are having conflicts with another mod.")
    public static int dimensionID = 56;


    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TofuMain.MODID)) {
            ConfigManager.sync(TofuMain.MODID, Config.Type.INSTANCE);
        }
    }
}
