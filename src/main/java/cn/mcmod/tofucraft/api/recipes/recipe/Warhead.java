package cn.mcmod.tofucraft.api.recipes.recipe;

import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;

public abstract class Warhead {

    public void onHit(RayTraceResult result, EntityAmmoBase bullet) {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.GENERIC, getDamage());
        }
        bullet.setDead();
    }

    public abstract float getDamage();

    public void onTicking(EntityAmmoBase bullet) {
    }

    public abstract ItemStack getAmmoItem();

    public void readFromNBT(NBTTagCompound nbt) {
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return nbt;
    }

}
