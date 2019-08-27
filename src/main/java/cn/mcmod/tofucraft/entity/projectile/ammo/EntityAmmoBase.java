package cn.mcmod.tofucraft.entity.projectile.ammo;

import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAmmoBase extends Entity implements IProjectile {
    public String warhead;
    public String propellant;
    public Warhead warheadInst;
    public Propellant propellantInst;
    protected int ticksInAir = 0; //A timer to terminate bullet calculation.
    protected Entity shootingEntity;

    public EntityAmmoBase(World worldIn) {
        super(worldIn);
        setSize(0.3f, 0.3f);
    }

    public EntityAmmoBase(World world, Entity shootingEntity, String warhead, String propellant) {
        this(world);
        this.shootingEntity = shootingEntity;
        this.warhead = warhead;
        this.propellant = propellant;

        try {
            this.warheadInst = FlintLockAmmoMap.getWarheads().get(warhead).newInstance();
            this.propellantInst = FlintLockAmmoMap.getPropellants().get(propellant).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void entityInit() {

    }


    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
        this.ticksInAir = nbtTagCompound.getInteger("inAir");
        this.warhead = nbtTagCompound.getString("warhead");
        this.propellant = nbtTagCompound.getString("propellant");

        try {
            this.warheadInst = FlintLockAmmoMap.getWarheads().get(warhead).newInstance();
            this.propellantInst = FlintLockAmmoMap.getPropellants().get(propellant).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (warheadInst != null)
            warheadInst.readFromNBT(nbtTagCompound);
        if (propellantInst != null)
            propellantInst.readFromNBT(nbtTagCompound);

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("inAir", this.ticksInAir);
        nbtTagCompound.setString("warhead", warhead);
        nbtTagCompound.setString("propellant", propellant);

        if (warheadInst != null)
            warheadInst.writeToNBT(nbtTagCompound);
        if (propellantInst != null)
            propellantInst.writeToNBT(nbtTagCompound);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (propellantInst == null || warheadInst == null)
            return;

        ticksInAir++;

        propellantInst.onTicking(this);
        warheadInst.onTicking(this);

        RayTraceResult raytraceresult = propellantInst.checkHit(this);

        if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            propellantInst.onHit(raytraceresult, this);
            warheadInst.onHit(raytraceresult, this);
        }

        propellantInst.updateMotion(this);
    }

    public void shoot(Entity shooter, float pitch, float yaw) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double) f, (double) f1, (double) f2, propellantInst.getPropellantForce(), propellantInst.getPropellantInaccuracy());
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround) {
            this.motionY += shooter.motionY;
        }
    }

    public int getTicksInAir() {
        return ticksInAir;
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double) f;
        y = y / (double) f;
        z = z / (double) f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        x = x * (double) velocity;
        y = y * (double) velocity;
        z = z * (double) velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

}
