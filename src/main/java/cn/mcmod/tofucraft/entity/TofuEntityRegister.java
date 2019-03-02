package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.RenderTofuCow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TofuEntityRegister {
    public static void entityRegister() {
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofucow"), EntityTofuCow.class, prefix("TofuCow"), 1, TofuMain.instance, 70, 3, false, 0xEBE8E8, 0xABA9A9);
    }

    private static String prefix(String path) {

        return TofuMain.MODID + "." + path;

    }

    @SideOnly(Side.CLIENT)
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuCow.class, RenderTofuCow::new);
    }
}
