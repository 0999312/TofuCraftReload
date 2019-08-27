package cn.mcmod.tofucraft.entity.projectile.ammo;

import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityAmmoBase extends Entity implements IProjectile {
    private static final Predicate<Entity> ARROW_TARGETS =
            Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE,
                    Entity::canBeCollidedWith);

    protected int ticksInAir = 0; //A timer to terminate bullet calculation.
    public String warhead;
    public String propellant;

    public Warhead warheadInst;
    public Propellant propellantInst;

    protected Entity shootingEntity;

    private EntityAmmoBase(World worldIn) {
        super(worldIn);
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

        setSize(warheadInst.getSize(), warheadInst.getSize());
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

        if (ticksInAir >= 64) this.setDead();
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

    @Nullable
    public Entity findEntityOnPath(Vec3d start, Vec3d end) {
        Entity entity = null;
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
                this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D),
                ARROW_TARGETS::test);
        double d0 = 0.0D;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity1 = list.get(i);

            if (entity1 != this.shootingEntity || this.ticksInAir > 0) {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

                if (raytraceresult != null) {
                    double d1 = start.squareDistanceTo(raytraceresult.hitVec);

                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }
}
