package cn.mcmod.tofucraft.util;

import net.minecraft.world.World;

public class WorldUtils {
    public static boolean isMorning(World world) {

        long timeDay = world.getWorldTime() % 24000;

        return timeDay > 0 && timeDay < 800;

    }
}
