package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuSpider;
import cn.mcmod.tofucraft.entity.EntityTofuSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTofuSpider extends RenderLiving<EntityTofuSpider> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofuspider/tofuspider.png");
    public RenderTofuSpider(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelTofuSpider(), 0.4F);
    }

    protected void preRenderCallback(EntityTofuSpider entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1.8F, 1.8F, 1.8F);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTofuSpider entity) {
        return TEXTURES;
    }
}
