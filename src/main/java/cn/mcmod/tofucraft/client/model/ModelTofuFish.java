package cn.mcmod.tofucraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTofuFish extends ModelBase {
    private final ModelRenderer field_203723_a;
    private final ModelRenderer field_203724_b;
    private final ModelRenderer field_203725_c;
    private final ModelRenderer field_203726_d;
    private final ModelRenderer field_203727_e;
    private final ModelRenderer field_203728_f;
    private final ModelRenderer field_203729_g;

    public ModelTofuFish() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int i = 22;
        this.field_203723_a = new ModelRenderer(this, 0, 0);
        this.field_203723_a.addBox(-1.0F, -2.0F, 0.0F, 2, 4, 7);
        this.field_203723_a.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.field_203725_c = new ModelRenderer(this, 11, 0);
        this.field_203725_c.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 3);
        this.field_203725_c.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.field_203726_d = new ModelRenderer(this, 0, 0);
        this.field_203726_d.addBox(-1.0F, -2.0F, -1.0F, 2, 3, 1);
        this.field_203726_d.setRotationPoint(0.0F, 22.0F, -3.0F);
        this.field_203727_e = new ModelRenderer(this, 22, 1);
        this.field_203727_e.addBox(-2.0F, 0.0F, -1.0F, 2, 0, 2);
        this.field_203727_e.setRotationPoint(-1.0F, 23.0F, 0.0F);
        this.field_203727_e.rotateAngleZ = (-(float)Math.PI / 4F);
        this.field_203728_f = new ModelRenderer(this, 22, 4);
        this.field_203728_f.addBox(0.0F, 0.0F, -1.0F, 2, 0, 2);
        this.field_203728_f.setRotationPoint(1.0F, 23.0F, 0.0F);
        this.field_203728_f.rotateAngleZ = ((float)Math.PI / 4F);
        this.field_203729_g = new ModelRenderer(this, 22, 3);
        this.field_203729_g.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4);
        this.field_203729_g.setRotationPoint(0.0F, 22.0F, 7.0F);
        this.field_203724_b = new ModelRenderer(this, 20, -6);
        this.field_203724_b.addBox(0.0F, -1.0F, -1.0F, 0, 1, 6);
        this.field_203724_b.setRotationPoint(0.0F, 20.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.field_203723_a.render(scale);
        this.field_203725_c.render(scale);
        this.field_203726_d.render(scale);
        this.field_203727_e.render(scale);
        this.field_203728_f.render(scale);
        this.field_203729_g.render(scale);
        this.field_203724_b.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }

        this.field_203729_g.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }
}