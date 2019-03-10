package cn.mcmod.tofucraft.client.render.layer;

import cn.mcmod.tofucraft.client.render.RenderTofuSlime;
import cn.mcmod.tofucraft.entity.EntityTofuSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerTofuSlimeGel implements LayerRenderer<EntityTofuSlime> {
    private final RenderTofuSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public LayerTofuSlimeGel(RenderTofuSlime slimeRendererIn) {
        this.slimeRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityTofuSlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entitylivingbaseIn.isInvisible()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
