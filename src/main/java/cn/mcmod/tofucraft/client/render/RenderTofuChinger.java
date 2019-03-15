package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.model.ModelTofuChinger;
import cn.mcmod.tofucraft.entity.EntityTofuChinger;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTofuChinger extends RenderLiving<EntityTofuChinger> {
    private static final ResourceLocation CHINGER_TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/mob/tofuchinger.png");

    public RenderTofuChinger(RenderManager p_i47210_1_) {
        super(p_i47210_1_, new ModelTofuChinger(), 0.4F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTofuChinger entity) {
        return CHINGER_TEXTURES;
    }
}