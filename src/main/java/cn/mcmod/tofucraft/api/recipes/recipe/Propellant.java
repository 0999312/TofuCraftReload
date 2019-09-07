package cn.mcmod.tofucraft.api.recipes.recipe;

import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import cn.mcmod.tofucraft.util.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;

public abstract class Propellant {

    public abstract ItemStack getPropellantItem();

    public void updateMotion(EntityAmmoBase bullet) {
        bullet.posX += bullet.motionX;
        bullet.posY += bullet.motionY;
        bullet.posZ += bullet.motionZ;
    }

    public abstract float getPropellantForce();

    public abstract float getPropellantInaccuracy();


    public void onTicking(EntityAmmoBase bullet) {

    }


    public void readFromNBT(NBTTagCompound nbt) {
    }


    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return nbt;
    }


    public void onHit(RayTraceResult result, EntityAmmoBase bullet) {

    }

    public RayTraceResult checkHit(EntityAmmoBase bullet) {
        if (bullet.getTicksInAir() <= 2) return null;
        return MathUtil.forwardsRaycast(bullet, true, true, bullet);
    }
}
