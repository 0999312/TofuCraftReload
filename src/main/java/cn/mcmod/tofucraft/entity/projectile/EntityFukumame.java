package cn.mcmod.tofucraft.entity.projectile;

import cn.mcmod.tofucraft.entity.EntityTofuSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Calendar;

public class EntityFukumame extends EntityThrowable {

    private double damage = 1.0f;
    private boolean isCrit;

    public EntityFukumame(World par1World)
    {
        super(par1World);
        this.isCrit = this.chkdate();
    }

    public EntityFukumame(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter);
    }

    public EntityFukumame(World par1World, EntityLivingBase par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
        this.isCrit = this.chkdate();

        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);

        float yaw = this.rotationYaw + rand.nextFloat() * 30F - 15F;
        float pitch = this.rotationPitch + rand.nextFloat() * 10F - 5F;
        float f = 0.2F;
        this.motionX = (-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (-MathHelper.sin(pitch / 180.0F * (float)Math.PI) * f);
        /*this.shoot(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0F);*/
    }

    public EntityFukumame(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        this.isCrit = this.chkdate();
    }

    private boolean chkdate()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) == Calendar.FEBRUARY && cal.get(Calendar.DATE) == 3;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            double d = this.getDamage();
            d *= this.isCrit ? 2.5D : 1.0D;

            Entity entityHit = par1MovingObjectPosition.entityHit;
            entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)d);
            if (entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivivng = (EntityLivingBase)entityHit;
                entityLivivng.hurtResistantTime = entityLivivng.maxHurtResistantTime / 2;
            }
            for (int i = 0; i < 3; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        if (!this.world.isRemote)
        {
            this.setDead();
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.inGround && this.isCrit)
        {
            for (int l = 0; l < 2; ++l)
            {
                this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * l / 2.0D, this.posY + this.motionY * l / 2.0D, this.posZ + this.motionZ * l / 2.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }
    }

    public void setDamage(double d)
    {
        this.damage = d;
    }

    public double getDamage()
    {
        return this.damage;
    }

}
