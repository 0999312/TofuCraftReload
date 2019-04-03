package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.projectile.EntityZundaArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderZundaArrow extends RenderArrow<EntityZundaArrow> {
    public static final ResourceLocation ZUNDAARROW = new ResourceLocation(TofuMain.MODID,"textures/entity/zundaarrow.png");
    public RenderZundaArrow(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityZundaArrow entity) {
        return ZUNDAARROW;
    }
}
