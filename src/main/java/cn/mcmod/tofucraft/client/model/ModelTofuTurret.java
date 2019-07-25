package cn.mcmod.tofucraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

/**
 * TofuTurret - bagu
 * Created using Tabula 7.0.0
 */
public class ModelTofuTurret extends ModelBase {
    public ModelRenderer core;
    public ModelRenderer spike;
    public ModelRenderer spike2;
    public ModelRenderer spike3;
    public ModelRenderer spike4;

    public ModelTofuTurret() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.spike = new ModelRenderer(this, 0, 0);
        this.spike.setRotationPoint(-3.0F, 3.0F, -3.5F);
        this.spike.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike, -0.7853981633974483F, 0.0F, 0.7853981633974483F);
        this.spike2 = new ModelRenderer(this, 0, 0);
        this.spike2.setRotationPoint(3.0F, 3.0F, -3.5F);
        this.spike2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike2, -0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.core = new ModelRenderer(this, 0, 0);
        this.core.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.core.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.spike4 = new ModelRenderer(this, 0, 0);
        this.spike4.setRotationPoint(-3.0F, 3.0F, 3.5F);
        this.spike4.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike4, 0.7853981633974483F, -0.017453292519943295F, 0.7853981633974483F);
        this.spike3 = new ModelRenderer(this, 0, 0);
        this.spike3.setRotationPoint(3.0F, 3.0F, 3.5F);
        this.spike3.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike3, 0.7853981633974483F, -0.017453292519943295F, -0.7853981633974483F);
        this.core.addChild(this.spike);
        this.core.addChild(this.spike2);
        this.core.addChild(this.spike4);
        this.core.addChild(this.spike3);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.1F, 1.1F, 1.1F);
        this.core.render(f5);
        GlStateManager.popMatrix();
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        float tick = entity.ticksExisted + partialTicks;

        GlStateManager.translate(0F, -0.2F - MathHelper.sin(tick * 0.1F) * 0.1F, 0F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
