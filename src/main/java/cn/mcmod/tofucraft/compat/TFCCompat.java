package cn.mcmod.tofucraft.compat;

import cn.mcmod.tofucraft.compat.tfc.SoyGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TFCCompat {

    public static void preInit() {
        GameRegistry.registerWorldGenerator(new SoyGenerator(), 0);
    }
}
