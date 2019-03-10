package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofunian;
import cn.mcmod.tofucraft.entity.EntityTofunian;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTofunian extends RenderBiped<EntityTofunian> {
    private static final ResourceLocation GUARDTEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/hunter_tofunian.png");
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofunian.png");

    public RenderTofunian(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelTofunian(), 0.5F);
        //this.field_82434_o = this.modelBipedMain;
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelTofunian(0.5F, true);
                this.modelArmor = new ModelTofunian(1.0F, true);
            }
        });
    }
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTofunian entity) {
        if(entity.getTofuProfession()== EntityTofunian.TofuProfession.GUARD){
            return GUARDTEXTURES;
        }else if(entity.getTofuProfession()== EntityTofunian.TofuProfession.TOFUCOOK){
            return GUARDTEXTURES;
        }else{
            return TEXTURES;
        }
    }
}