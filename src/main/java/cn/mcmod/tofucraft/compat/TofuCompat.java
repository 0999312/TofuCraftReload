package cn.mcmod.tofucraft.compat;

import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.fml.common.Loader;

public class TofuCompat {
    public static void preInit() {
        if (Loader.isModLoaded("tconstruct")) {
            TConstructCompat.preInit();
        }
        if (Loader.isModLoaded("flammpfeil.slashblade")){
        	SlashBlade.InitEventBus.register(new SlashBladeCompat());
        }
    }

    public static void init() {
        if (Loader.isModLoaded("tconstruct")) {
            TConstructCompat.init();
        }

    }
}
