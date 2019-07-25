package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuTurret;
import cn.mcmod.tofucraft.client.render.layer.LayerGlowEye;
import cn.mcmod.tofucraft.entity.EntityTofuTurret;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTofuTurret extends RenderLiving<EntityTofuTurret> {
    private static final ResourceLocation LOCATION = new ResourceLocation(TofuMain.MODID, "textures/mob/tofuturret/tofuturret.png");

    public RenderTofuTurret(RenderManager p_i48864_1_) {
        super(p_i48864_1_, new ModelTofuTurret(), 0.32F);
        this.addLayer(new LayerGlowEye<>(this, "textures/mob/tofuturret/tofuturret_eye.png"));
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityTofuTurret entity) {
        return LOCATION;
    }

}