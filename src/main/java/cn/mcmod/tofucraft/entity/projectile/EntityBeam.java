package cn.mcmod.tofucraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBeam extends EntityFireball {
    public EntityBeam(World worldIn) {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityBeam(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 4.0F)) {
                        this.applyEnchantments(this.shootingEntity, result.entityHit);
                    }
                } else {
                    result.entityHit.attackEntityFrom(DamageSource.MAGIC, 4.0F);
                }
            }

            this.world.newExplosion(this, this.posX, this.posY, this.posZ, 0.5F, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity));
            this.setDead();
        }
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    protected boolean isFireballFiery() {
        return false;
    }

    public boolean isBurning() {
        return false;
    }
}
