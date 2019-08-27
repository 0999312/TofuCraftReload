package cn.mcmod.tofucraft.api.recipes.recipe;

import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

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
        Vec3d vec3d1 = new Vec3d(bullet.posX, bullet.posY, bullet.posZ);
        Vec3d vec3d = new Vec3d(bullet.posX + bullet.motionX, bullet.posY + bullet.motionY, bullet.posZ + bullet.motionZ);
        RayTraceResult raytraceresult = bullet.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
        vec3d1 = new Vec3d(bullet.posX, bullet.posY, bullet.posZ);
        vec3d = new Vec3d(bullet.posX + bullet.motionX, bullet.posY + bullet.motionY, bullet.posZ + bullet.motionZ);

        if (raytraceresult != null)
            vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);


        Entity entity = bullet.findEntityOnPath(vec3d1, vec3d);

        if (entity != null)
            raytraceresult = new RayTraceResult(entity);
        if (bullet.getTicksInAir() <= 2)
            raytraceresult = null;

        return raytraceresult;
    }
}
