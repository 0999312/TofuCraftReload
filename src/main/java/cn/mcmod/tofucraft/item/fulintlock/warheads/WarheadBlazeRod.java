package cn.mcmod.tofucraft.item.fulintlock.warheads;

import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;

public class WarheadBlazeRod extends Warhead {
    private static final ItemStack rod = new ItemStack(Items.BLAZE_ROD, 1);

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public ItemStack getAmmoItem() {
        return rod;
    }

    @Override
    public void onHit(RayTraceResult result, EntityAmmoBase bullet) {
        if (result.entityHit != null){
            result.entityHit.setFire(100);
            result.entityHit.attackEntityFrom(DamageSource.FIREWORKS, 5);
        }else
            bullet.setDead();
    }
}
