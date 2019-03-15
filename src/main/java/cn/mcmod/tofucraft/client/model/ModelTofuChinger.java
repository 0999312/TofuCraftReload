package cn.mcmod.tofucraft.client.model;

import cn.mcmod.tofucraft.entity.EntityTofuChinger;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * TofuChinger - bagu
 * Created using Tabula 7.0.0
 */
public class ModelTofuChinger extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer chin;
    public ModelRenderer backlegR;
    public ModelRenderer mainlegR;
    public ModelRenderer backlegL;
    public ModelRenderer mainlegL;

    public ModelTofuChinger() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.mainlegR = new ModelRenderer(this, 8, 10);
        this.mainlegR.setRotationPoint(-2.4F, -1.0F, -2.5F);
        this.mainlegR.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.backlegL = new ModelRenderer(this, 0, 15);
        this.backlegL.setRotationPoint(2.6F, -1.0F, 0.5F);
        this.backlegL.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.mainlegL = new ModelRenderer(this, 8, 15);
        this.mainlegL.setRotationPoint(2.6F, -1.0F, -2.5F);
        this.mainlegL.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.0F, 2.0F);
        this.body.addBox(-3.0F, -2.0F, -4.0F, 6, 2, 6, 0.0F);
        this.chin = new ModelRenderer(this, 24, 13);
        this.chin.setRotationPoint(0.0F, -5.0F, 1.0F);
        this.chin.addBox(-4.0F, 0.0F, -8.0F, 8, 3, 10, 0.0F);
        this.backlegR = new ModelRenderer(this, 0, 10);
        this.backlegR.setRotationPoint(-2.4F, -1.0F, 0.5F);
        this.backlegR.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.head = new ModelRenderer(this, 24, 0);
        this.head.setRotationPoint(0.0F, -5.0F, 1.0F);
        this.head.addBox(-4.0F, -3.0F, -8.0F, 8, 3, 10, 0.0F);
        this.body.addChild(this.mainlegR);
        this.body.addChild(this.backlegL);
        this.body.addChild(this.mainlegL);
        this.body.addChild(this.chin);
        this.body.addChild(this.backlegR);
        this.body.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float f = ageInTicks - (float)entityIn.ticksExisted;
        float f1 = ((EntityTofuChinger)entityIn).getMouseAnimationScale(f);
        f1 = f1 * f1;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F - 0.8F * f1;
        this.chin.rotateAngleY = netHeadYaw * 0.017453292F;
        this.chin.rotateAngleX = headPitch * 0.017453292F;
        this.mainlegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.mainlegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.backlegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.backlegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
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
