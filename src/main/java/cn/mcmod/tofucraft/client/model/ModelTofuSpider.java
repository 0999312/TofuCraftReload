package cn.mcmod.tofucraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * TofuSpider - bagu
 * Created using Tabula 7.0.0
 */
public class ModelTofuSpider extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer body2;
    public ModelRenderer legR;
    public ModelRenderer legR2;
    public ModelRenderer legR3;
    public ModelRenderer legL2;
    public ModelRenderer legL1;
    public ModelRenderer legL3;
    public ModelRenderer mouseL;
    public ModelRenderer mouseR;
    public ModelRenderer eyeL;
    public ModelRenderer eyeR;

    public ModelTofuSpider() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.eyeL = new ModelRenderer(this, 32, 8);
        this.eyeL.setRotationPoint(2.1F, -0.5F, -4.3F);
        this.eyeL.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F);
        this.legL1 = new ModelRenderer(this, 14, 18);
        this.legL1.setRotationPoint(3.4F, 0.0F, 6.5F);
        this.legL1.addBox(0.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legL1, 0.0F, -0.40980330836826856F, 0.5009094953223726F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 21.1F, -3.0F);
        this.body.addBox(-4.0F, -3.0F, 0.0F, 8, 5, 8, 0.0F);
        this.legR2 = new ModelRenderer(this, 0, 18);
        this.legR2.setRotationPoint(-3.4F, 0.0F, 4.0F);
        this.legR2.addBox(-5.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legR2, 0.0F, 0.0F, -0.5009094953223726F);
        this.mouseR = new ModelRenderer(this, 0, 0);
        this.mouseR.setRotationPoint(-1.5F, 0.8F, -5.2F);
        this.mouseR.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.legL2 = new ModelRenderer(this, 14, 18);
        this.legL2.setRotationPoint(3.6F, 0.0F, 4.0F);
        this.legL2.addBox(0.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legL2, 0.0F, 0.0F, 0.5009094953223726F);
        this.eyeR = new ModelRenderer(this, 38, 8);
        this.eyeR.setRotationPoint(-2.1F, -0.5F, -4.3F);
        this.eyeR.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F);
        this.mouseL = new ModelRenderer(this, 0, 0);
        this.mouseL.setRotationPoint(0.5F, 0.8F, -5.2F);
        this.mouseL.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.legL3 = new ModelRenderer(this, 14, 18);
        this.legL3.setRotationPoint(3.4F, 0.0F, 1.5F);
        this.legL3.addBox(0.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legL3, 0.0F, 0.40980330836826856F, 0.5009094953223726F);
        this.legR = new ModelRenderer(this, 0, 18);
        this.legR.setRotationPoint(-3.4F, 0.0F, 1.5F);
        this.legR.addBox(-5.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legR, 0.0F, -0.40980330836826856F, -0.5009094953223726F);
        this.body2 = new ModelRenderer(this, 0, 13);
        this.body2.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.body2.addBox(-3.5F, -2.5F, 0.0F, 7, 4, 1, 0.0F);
        this.legR3 = new ModelRenderer(this, 0, 18);
        this.legR3.setRotationPoint(-3.4F, 0.0F, 6.5F);
        this.legR3.addBox(-5.0F, -1.0F, -1.0F, 5, 2, 2, 0.0F);
        this.setRotateAngle(legR3, 0.0F, 0.40980330836826856F, -0.5009094953223726F);
        this.head = new ModelRenderer(this, 32, 0);
        this.head.setRotationPoint(0.0F, -0.4F, -0.7F);
        this.head.addBox(-3.0F, -2.0F, -4.0F, 6, 4, 4, 0.0F);
        this.head.addChild(this.eyeL);
        this.body.addChild(this.legL3);
        this.body.addChild(this.legR2);
        this.head.addChild(this.mouseR);
        this.body.addChild(this.legL2);
        this.head.addChild(this.eyeR);
        this.head.addChild(this.mouseL);
        this.body.addChild(this.legL1);
        this.body.addChild(this.legR);
        this.body.addChild(this.body2);
        this.body.addChild(this.legR3);
        this.body.addChild(this.head);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.legR.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount -0.40980330836826856F;
        this.legR2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.5F * limbSwingAmount;
        this.legR3.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount + 0.40980330836826856F;

        this.legL1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount - 0.40980330836826856F;
        this.legL2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.6F * limbSwingAmount;
        this.legL3.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount +0.40980330836826856F;
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
