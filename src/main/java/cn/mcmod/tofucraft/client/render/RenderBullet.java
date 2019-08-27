package cn.mcmod.tofucraft.client.render;

import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderBullet extends Render<EntityAmmoBase> {
    public RenderBullet(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAmmoBase entityAmmoBase) {
        return null;
    }

    @Override
    public void doRender(EntityAmmoBase entity, double x, double y, double z, float entityYaw, float partialTicks) {

    }
}
