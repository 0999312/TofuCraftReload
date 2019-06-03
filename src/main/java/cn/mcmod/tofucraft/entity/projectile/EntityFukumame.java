package cn.mcmod.tofucraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Calendar;

public class EntityFukumame extends EntityThrowable {

    private double damage = 1.0f;
    private boolean isCrit;
    private int age;

    public EntityFukumame(World par1World) {
        super(par1World);
        this.isCrit = this.chkdate();
    }

    public EntityFukumame(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter);
        this.isCrit = this.chkdate();
    }

    public EntityFukumame(World par1World, EntityLivingBase par2EntityLiving) {
        this(par1World, par2EntityLiving.posX, par2EntityLiving.posY + (double) par2EntityLiving.getEyeHeight() - 0.1D, par2EntityLiving.posZ);
        this.isCrit = this.chkdate();
        this.thrower = par2EntityLiving;
        this.ignoreEntity = par2EntityLiving;
    }

    public EntityFukumame(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        this.isCrit = this.chkdate();
    }

    private boolean chkdate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) == Calendar.FEBRUARY && cal.get(Calendar.DATE) == 3;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        Entity entityHit = par1MovingObjectPosition.entityHit;
        if (entityHit == this.ignoreEntity && this.age < 5) {
            return;
        }
        if (par1MovingObjectPosition.entityHit != null) {
            double d = this.getDamage();
            d *= this.isCrit ? 2.5D : 1.0D;


            entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) d);
            if (entityHit instanceof EntityLivingBase) {
                EntityLivingBase entityLivivng = (EntityLivingBase) entityHit;
                entityLivivng.hurtResistantTime = entityLivivng.maxHurtResistantTime / 2;
            }
            for (int i = 0; i < 3; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        ++this.age;

        if (!this.inGround && this.isCrit) {
            for (int l = 0; l < 2; ++l) {
                this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * l / 2.0D, this.posY + this.motionY * l / 2.0D, this.posZ + this.motionZ * l / 2.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }
    }

    public void setDamage(double d) {
        this.damage = d;
    }

    public double getDamage() {
        return this.damage;
    }

}
