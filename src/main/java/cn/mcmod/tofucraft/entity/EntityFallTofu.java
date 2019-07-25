package cn.mcmod.tofucraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityFallTofu extends Entity {
    private EntityLivingBase owner;
    @Nullable
    private UUID ownerUniqueId;
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0F;
    public NBTTagCompound tileEntityData;

    public EntityFallTofu(World worldIn) {
        super(worldIn);
        this.setSize(0.98F, 0.98F);
    }

    public EntityFallTofu(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.setPosition(x, y + (double) ((1.0F - this.height) / 2.0F), z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem() {
        return false;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void onUpdate() {

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.hurtEntities && this.onGround) {
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F);
            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.2F, 1.0F);
            if (!this.world.isRemote) {
                this.setDead();
                return;
            }
        }
        if (this.fallTime++ == 300) {
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F);
            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.2F, 1.0F);
            if (!this.world.isRemote) {
                this.setDead();
                return;
            }
        }


        RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, false, this.owner);

        if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onImpact(raytraceresult);
            this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.2F, 1.0F);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F);
            if (!this.world.isRemote) {
                this.setDead();
            }
        }


        if (!this.hasNoGravity()) {
            if (this.onGround) {
                this.motionY = 0.0F;
            } else if (this.isInWater() || this.isInLava()) {
                this.motionY = (0.3 - this.motionY) * 0.3;
            } else {
                this.motionY -= 0.03999999910593033D;
            }
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
    }

    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                if (this.owner == null) {
                    DamageSource damagesource = DamageSource.FALLING_BLOCK;

                    result.entityHit.attackEntityFrom(damagesource, 5);
                } else {
                    DamageSource damagesource = DamageSource.FALLING_BLOCK;

                    result.entityHit.attackEntityFrom(new EntityDamageSource("fallingBlock", this.owner), 5);
                }
            }
        }
    }


    public float getCollisionBorderSize() {
        return 0.0F;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }

    @Override
    public boolean canBePushed() {

        return false;

    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound) {
        if (this.owner != null) {
            NBTTagCompound nbttagcompound = NBTUtil.createUUIDTag(this.owner.getUniqueID());
            compound.setTag("Owner", nbttagcompound);
        }
        compound.setInteger("Time", this.fallTime);
        compound.setBoolean("DropItem", this.shouldDropItem);
        compound.setBoolean("HurtEntities", this.hurtEntities);
        compound.setFloat("FallHurtAmount", this.fallHurtAmount);
        compound.setInteger("FallHurtMax", this.fallHurtMax);

        if (this.tileEntityData != null) {
            compound.setTag("TileEntityData", this.tileEntityData);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound) {
        int i = compound.getByte("Data") & 255;


        this.fallTime = compound.getInteger("Time");

        if (compound.hasKey("HurtEntities", 99)) {
            this.hurtEntities = compound.getBoolean("HurtEntities");
            this.fallHurtAmount = compound.getFloat("FallHurtAmount");
            this.fallHurtMax = compound.getInteger("FallHurtMax");
        }
        if (compound.hasKey("Owner", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Owner");
            this.ownerUniqueId = NBTUtil.getUUIDFromTag(nbttagcompound);
        }

        if (compound.hasKey("DropItem", 99)) {
            this.shouldDropItem = compound.getBoolean("DropItem");
        }

        if (compound.hasKey("TileEntityData", 10)) {
            this.tileEntityData = compound.getCompoundTag("TileEntityData");
        }

    }

    public void setHurtEntities(boolean p_145806_1_) {
        this.hurtEntities = p_145806_1_;
    }


    @SideOnly(Side.CLIENT)
    public World getWorldObj() {
        return this.world;
    }

    public void setOwner(EntityLivingBase owner) {
        this.owner = owner;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    public boolean ignoreItemEntityData() {
        return true;
    }
}