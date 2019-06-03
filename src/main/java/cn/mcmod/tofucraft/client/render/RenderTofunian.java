package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofunian;
import cn.mcmod.tofucraft.client.render.layer.LayerTofunianHeldItem;
import cn.mcmod.tofucraft.entity.EntityTofunian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTofunian extends RenderLiving<EntityTofunian> {
    private static final ResourceLocation GUARDTEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofunian/hunter_tofunian.png");
    private static final ResourceLocation COOKTEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofunian/cook_tofunian.png");
    private static final ResourceLocation SMITHTEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofunian/smith_tofunian.png");
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofunian/tofunian.png");

    public RenderTofunian(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelTofunian(), 0.5F);
        this.addLayer(new LayerBipedArmor(this)        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelTofunian(0.5F, true);
                this.modelArmor = new ModelTofunian(1.0F, true);
            }
        });
        this.addLayer(new LayerTofunianHeldItem(this));
    }
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTofunian entity) {
        if(entity.getTofuProfession()== EntityTofunian.TofuProfession.GUARD){
            return GUARDTEXTURES;
        } else if (entity.getTofuProfession() == EntityTofunian.TofuProfession.TOFUCOOK || entity.getTofuProfession() == EntityTofunian.TofuProfession.FISHERMAN) {
            return COOKTEXTURES;
        } else if (entity.getTofuProfession() == EntityTofunian.TofuProfession.TOFUSMITH) {
            return SMITHTEXTURES;
        }else{
            return TEXTURES;
        }
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
}