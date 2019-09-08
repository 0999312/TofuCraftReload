package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.projectile.EntityTippedChingerArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTippedChingerArrow extends RenderArrow<EntityTippedChingerArrow> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation(TofuMain.MODID, "textures/entity/chingerarrow.png");

    public RenderTippedChingerArrow(RenderManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTippedChingerArrow entity) {
        return RES_ARROW;
    }
}