package cn.mcmod.tofucraft.compat;

import net.minecraftforge.fml.common.Loader;

public class TofuCompat {
    public static void preInit() {
        if (Loader.isModLoaded("tconstruct")) {
            TConstructCompat.preInit();
        }
    }

    public static void init() {
        if (Loader.isModLoaded("tconstruct")) {
            TConstructCompat.init();
        }
    }
}
