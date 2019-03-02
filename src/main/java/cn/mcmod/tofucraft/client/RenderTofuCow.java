package cn.mcmod.tofucraft.client;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTofuCow extends RenderLiving<EntityTofuCow> {
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/cow/tofucow.png");

    public RenderTofuCow(RenderManager p_i47210_1_) {
        super(p_i47210_1_, new ModelCow(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTofuCow entity) {
        return COW_TEXTURES;
    }
}