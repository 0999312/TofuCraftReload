package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuFish;
import cn.mcmod.tofucraft.entity.EntityTofuFish;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTofuFish extends RenderLiving<EntityTofuFish> {
    private static final ResourceLocation COD_LOCATION = new ResourceLocation(TofuMain.MODID,"textures/mob/tofufish.png");

    public RenderTofuFish(RenderManager p_i48864_1_) {
        super(p_i48864_1_, new ModelTofuFish(), 0.2F);
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityTofuFish entity) {
        return COD_LOCATION;
    }

    protected void applyRotations(EntityTofuFish entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        if (!entityLiving.isInWater()) {
            GlStateManager.translate(0.1F, 0.1F, -0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        }

    }
}