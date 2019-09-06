package cn.mcmod.tofucraft.client.render;


import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuGandlem;
import cn.mcmod.tofucraft.client.render.layer.LayerGlowEye;
import cn.mcmod.tofucraft.entity.EntityTofuGandlem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTofuGandlem extends RenderLiving<EntityTofuGandlem> {
    private static final ResourceLocation LOCATION = new ResourceLocation(TofuMain.MODID, "textures/mob/tofugandlem/tofugandlem.png");
    private static final ResourceLocation SLEEP_LOCATION = new ResourceLocation(TofuMain.MODID, "textures/mob/tofugandlem/tofugandlem_sleep.png");

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public RenderTofuGandlem(RenderManager p_i48864_1_) {
        super(p_i48864_1_, new ModelTofuGandlem(), 0.5F);
        this.addLayer(new LayerGlowEye(this, "textures/mob/tofugandlem/tofugandlem_eye.png") {
			@Override
            public void doRenderLayer(EntityLiving entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (entitylivingbaseIn instanceof EntityTofuGandlem) {
                    if (!((EntityTofuGandlem) entitylivingbaseIn).isSleep()) {
                        super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                    }
                }
            }
        });
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityTofuGandlem entity) {
        if (entity.isSleep() || !entity.isEntityAlive()) {
            return SLEEP_LOCATION;
        } else {
            return LOCATION;
        }
    }

}