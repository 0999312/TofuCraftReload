package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuMindCore;
import cn.mcmod.tofucraft.client.render.layer.LayerGlowEye;
import cn.mcmod.tofucraft.entity.EntityTofuMindCore;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTofuMindCore extends RenderLiving<EntityTofuMindCore> {
    private static final ResourceLocation LOCATION = new ResourceLocation(TofuMain.MODID, "textures/mob/tofumindcore/tofumindcore.png");

    public RenderTofuMindCore(RenderManager p_i48864_1_) {
        super(p_i48864_1_, new ModelTofuMindCore(), 0.5F);
        this.addLayer(new LayerGlowEye<>(this, "textures/mob/tofumindcore/tofumindcore_eye.png"));
        this.addLayer(new LayerHeldItem(this) {
            @Override
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                GlStateManager.translate(0.0F, 0.35F, 0.0F);
                super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
        });
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityTofuMindCore entity) {
        return LOCATION;
    }

}