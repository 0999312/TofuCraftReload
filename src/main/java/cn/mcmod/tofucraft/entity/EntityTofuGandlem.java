package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.TofuParticleType;
import cn.mcmod.tofucraft.entity.ai.EntityAIAttackMoveRanged;
import cn.mcmod.tofucraft.entity.movehelper.EntityTofuFlyHelper;
import cn.mcmod.tofucraft.entity.projectile.EntityBeam;
import cn.mcmod.tofucraft.entity.projectile.EntityFukumame;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityTofuGandlem extends EntityMob implements IRangedAttackMob {
    protected static final DataParameter<Byte> AGGRESSIVE = EntityDataManager.<Byte>createKey(EntityTofuGandlem.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> SPELL_CASTING = EntityDataManager.<Boolean>createKey(EntityTofuGandlem.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.<Boolean>createKey(EntityTofuGandlem.class, DataSerializers.BOOLEAN);

    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;

    private float prevClientSideSpellCastingAnimation;
    private float clientSideSpellCastingAnimation;
    private float prevClientSideAttackingAnimation;
    private float clientSideAttackingAnimation;

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS));

    public EntityTofuGandlem(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 2.05F);
        this.isImmuneToFire = true;
        this.moveHelper = new EntityTofuFlyHelper(this);
        this.experienceValue = 60;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new AIDoNothing());
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new AIHealSpell());
        this.tasks.addTask(4, new AITofuShoot());
        this.tasks.addTask(5, new AISummonSpell());
        this.tasks.addTask(6, new EntityAIAttackMoveRanged(this, 1.0D, 60, 16.0F) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !isSpellcasting();
            }

            @Override
            public boolean shouldContinueExecuting() {
                return super.shouldContinueExecuting() && !isSpellcasting();
            }
        });
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.1D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(260.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.262896D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.46D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(AGGRESSIVE, Byte.valueOf((byte) 0));
        this.dataManager.register(SPELL_CASTING, Boolean.FALSE);
        this.dataManager.register(SLEEP, Boolean.FALSE);
    }

    @SideOnly(Side.CLIENT)
    protected boolean isAggressive(int mask) {
        int i = ((Byte) this.dataManager.get(AGGRESSIVE)).byteValue();
        return (i & mask) != 0;
    }

    protected void setAggressive(int mask, boolean value) {
        int i = ((Byte) this.dataManager.get(AGGRESSIVE)).byteValue();

        if (value) {
            i = i | mask;
        } else {
            i = i & ~mask;
        }

        this.dataManager.set(AGGRESSIVE, Byte.valueOf((byte) (i & 255)));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        compound.setBoolean("Sleep", this.isSleep());

        /*if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }*/
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        this.setSleep(compound.getBoolean("Sleep"));
    }

    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        //this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.85F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        super.onLivingUpdate();
    }

    @Override
    protected void updateAITasks() {
        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0) {

            this.heightOffsetUpdateTime = 100;

            this.heightOffset = 0.5f + (float) this.rand.nextGaussian() * 2.0f;

        }

        EntityLivingBase target = getAttackTarget();

        if (target != null && target.isEntityAlive() && target.posY + (double) target.getEyeHeight() > this.posY + (double) getEyeHeight() + (double) this.heightOffset) {
            this.motionY = (0.2 - motionY) * 0.2;
            this.isAirBorne = true;
        }

        super.updateAITasks();

       /* if(!this.isSleep()) {
            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }*/
    }

  /*  public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }*/

    public boolean isSpellcasting() {
        return ((Boolean) this.dataManager.get(SPELL_CASTING)).booleanValue();
    }

    protected void setSpellcasting(boolean casting) {
        this.dataManager.set(SPELL_CASTING, Boolean.valueOf(casting));
    }

    public boolean isSleep() {
        return ((Boolean) this.dataManager.get(SLEEP)).booleanValue();
    }

    public void setSleep(boolean sleep) {
        this.dataManager.set(SLEEP, Boolean.valueOf(sleep));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            this.prevClientSideSpellCastingAnimation = this.clientSideSpellCastingAnimation;
            if (this.isSpellcasting()) {
                this.clientSideSpellCastingAnimation = MathHelper.clamp(this.clientSideSpellCastingAnimation + 0.5F, 0.0F, 6.0F);
            } else {
                this.clientSideSpellCastingAnimation = MathHelper.clamp(this.clientSideSpellCastingAnimation - 1.0F, 0.0F, 6.0F);
            }

            this.prevClientSideAttackingAnimation = this.clientSideAttackingAnimation;
            if (this.isAggressive()) {
                this.clientSideAttackingAnimation = MathHelper.clamp(this.clientSideAttackingAnimation + 0.5F, 0.0F, 6.0F);
            } else {
                this.clientSideAttackingAnimation = MathHelper.clamp(this.clientSideAttackingAnimation - 1.0F, 0.0F, 6.0F);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getSpellCastingAnimationScale(float p_189795_1_) {
        return (this.prevClientSideSpellCastingAnimation + (this.clientSideSpellCastingAnimation - this.prevClientSideSpellCastingAnimation) * p_189795_1_) / 6.0F;
    }

    @SideOnly(Side.CLIENT)
    public float getAttackingAnimationScale(float p_189795_1_) {
        return (this.prevClientSideAttackingAnimation + (this.clientSideAttackingAnimation - this.prevClientSideAttackingAnimation) * p_189795_1_) / 6.0F;
    }


    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        double d1 = target.posX - this.posX;
        double d2 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
        double d3 = target.posZ - this.posZ;

        EntityBeam projectile = new EntityBeam(this.world, this, d1 + this.getRNG().nextGaussian() * 0.01 - 0.005, d2, d3 + this.getRNG().nextGaussian() * 0.01 - 0.005);

        Vec3d vec3d = this.getLook(1.0F);

        playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

        projectile.setLocationAndAngles(this.posX + vec3d.x * 1.3D, this.posY + this.getEyeHeight(), this.posZ + vec3d.z * 1.3D, this.rotationYaw, this.rotationPitch);

        float d0 = (this.rand.nextFloat() * 16.0F) - 8.0F;

        projectile.posY = this.posY + (double) (this.height / 2.0F) + 0.5D;
        this.world.spawnEntity(projectile);

    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getCreatureAttribute() == TofuEntityRegister.tofuTurret) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else if (source.getImmediateSource() instanceof EntityFukumame) {
            return false;
        } else {
            if (isSleep()) {
                setSleep(false);

               /* if(source.getImmediateSource() instanceof EntityPlayerMP) {
                    this.bossInfo.addPlayer((EntityPlayerMP) source.getImmediateSource());
                }*/
            }
            if (source.getImmediateSource() instanceof EntityArrow) {
                return super.attackEntityFrom(source, amount * 0.8F);
            } else {
                return super.attackEntityFrom(source, amount);
            }
        }
    }


    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return TofuEntityRegister.tofuTurret;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofugandlem;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @SideOnly(Side.CLIENT)
    public boolean isAggressive() {
        return this.isAggressive(1);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        this.setAggressive(1, swingingArms);
    }

    class AIHealSpell extends AIUseSpell {
        private AIHealSpell() {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return false;
            } else {
                return EntityTofuGandlem.this.getHealth() < EntityTofuGandlem.this.getMaxHealth() / 2;
            }
        }

        protected int getCastingInterval() {
            return 600;
        }

        protected void castSpell() {
            EntityTofuGandlem.this.heal(4F);
            EntityTofuGandlem.this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 160, 1));
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED;
        }
    }

    class AISummonSpell extends AIUseSpell {
        private AISummonSpell() {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return false;
            } else {
                int i = EntityTofuGandlem.this.world.getEntitiesWithinAABB(EntityTofuTurret.class, EntityTofuGandlem.this.getEntityBoundingBox().grow(30.0D)).size();
                return EntityTofuGandlem.this.rand.nextInt(2) + 1 > i;
            }
        }

        protected int getCastingInterval() {
            return 800;
        }

        protected int getCastWarmupTime() {
            return 40;
        }

        protected void castSpell() {
            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos = (new BlockPos(EntityTofuGandlem.this)).add(-2 + EntityTofuGandlem.this.rand.nextInt(5), 1, -2 + EntityTofuGandlem.this.rand.nextInt(5));
                if (EntityTofuGandlem.this.world.rand.nextInt(2) == 0) {
                    EntityTofuTurret entityturret = new EntityTofuTurret(EntityTofuGandlem.this.world);
                    entityturret.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    entityturret.onInitialSpawn(EntityTofuGandlem.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData) null);
                    entityturret.setOwner(EntityTofuGandlem.this);
                    EntityTofuGandlem.this.world.spawnEntity(entityturret);
                } else {
                    EntityTofuMindCore entityturret = new EntityTofuMindCore(EntityTofuGandlem.this.world);
                    entityturret.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    entityturret.onInitialSpawn(EntityTofuGandlem.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData) null);
                    EntityTofuGandlem.this.world.spawnEntity(entityturret);
                }
            }
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }
    }

    public abstract class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityTofuGandlem.this.getAttackTarget() == null) {
                return false;
            } else if (EntityTofuGandlem.this.isSpellcasting()) {
                return false;
            } else {
                return EntityTofuGandlem.this.ticksExisted >= this.spellCooldown;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityTofuGandlem.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            EntityTofuGandlem.this.setSpellcasting(false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            this.spellCooldown = EntityTofuGandlem.this.ticksExisted + this.getCastingInterval();
            EntityTofuGandlem.this.setSpellcasting(true);
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                EntityTofuGandlem.this.playSound(soundevent, 1.0F, 1.0F);
            }

        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                EntityTofuGandlem.this.setSpellcasting(false);
                this.castSpell();
                EntityTofuGandlem.this.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.4F, 1.0F);
            }
        }

        protected abstract void castSpell();

        protected int getCastWarmupTime() {
            return 20;
        }


        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();
    }

    private class AITofuShoot extends AIUseSpell {
        private AITofuShoot() {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return false;
            } else {
                return true;
            }
        }

        protected int getCastingInterval() {
            return 260;
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected void castSpell() {
            EntityLivingBase entitylivingbase = EntityTofuGandlem.this.getAttackTarget();
            double d0 = Math.min(entitylivingbase.posY, EntityTofuGandlem.this.posY);
            double d1 = Math.max(entitylivingbase.posY, EntityTofuGandlem.this.posY) + 1.0D;
            float f = (float) MathHelper.atan2(entitylivingbase.posZ - EntityTofuGandlem.this.posZ, entitylivingbase.posX - EntityTofuGandlem.this.posX);


            for (int i = 0; i < 5; ++i) {
                float f1 = f + (float) i * (float) Math.PI * 0.4F;
                this.spawnFallTofu(EntityTofuGandlem.this.posX + (double) MathHelper.cos(f1) * 1.5D, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f1) * 1.5D);
                TofuMain.proxy.spawnParticle(EntityTofuGandlem.this.world, TofuParticleType.TOFUPORTAL, EntityTofuGandlem.this.posX + (double) MathHelper.cos(f1) * 1.5D, EntityTofuGandlem.this.posY + 8, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f1) * 1.5D, 0.0D, 0.0D, 0.0D);
            }

            for (int k = 0; k < 8; ++k) {
                float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + ((float) Math.PI * 2F / 5F);
                this.spawnFallTofu(EntityTofuGandlem.this.posX + (double) MathHelper.cos(f2) * 2.5D, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f2) * 2.5D);
                TofuMain.proxy.spawnParticle(EntityTofuGandlem.this.world, TofuParticleType.TOFUPORTAL, EntityTofuGandlem.this.posX + (double) MathHelper.cos(f2) * 2.5D, EntityTofuGandlem.this.posY + 8, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f2) * 2.5D, 0.0D, 0.0D, 0.0D);
            }

            for (int k = 0; k < 13; ++k) {
                float f3 = f + (float) k * 1.2F;
                this.spawnFallTofu(EntityTofuGandlem.this.posX + (double) MathHelper.cos(f3) * 3.5D, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f3) * 3.5D);
                TofuMain.proxy.spawnParticle(EntityTofuGandlem.this.world, TofuParticleType.TOFUPORTAL, EntityTofuGandlem.this.posX + (double) MathHelper.cos(f3) * 1.5D, EntityTofuGandlem.this.posY + 8, EntityTofuGandlem.this.posZ + (double) MathHelper.sin(f3) * 1.5D, 0.0D, 0.0D, 0.0D);
            }
        }

        private void spawnFallTofu(double posX, double posZ) {
            EntityFallTofu fallTofu = new EntityFallTofu(EntityTofuGandlem.this.world, posX, EntityTofuGandlem.this.posY + 8, posZ);
            fallTofu.setOwner(EntityTofuGandlem.this);
            fallTofu.setHurtEntities(true);
            EntityTofuGandlem.this.world.spawnEntity(fallTofu);
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }
    }

    class AIDoNothing extends EntityAIBase {
        public AIDoNothing() {
            this.setMutexBits(7);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityTofuGandlem.this.isSleep();
        }
    }
}
