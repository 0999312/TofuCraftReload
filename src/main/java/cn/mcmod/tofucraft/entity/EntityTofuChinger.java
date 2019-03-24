package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityTofuChinger extends EntityMob {
    private static final DataParameter<Boolean> IS_MOUSEOPEN = EntityDataManager.<Boolean>createKey(EntityTofuChinger.class, DataSerializers.BOOLEAN);

    private int warningSoundTicks;

    private float clientSideMouseAnimation0;
    private float clientSideMouseAnimation;

    public EntityTofuChinger(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 0.7F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityTofuChinger.AIMeleeAttack());
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));

        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this,true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityTofuSlime.class,false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityPlayer.class,true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityTofunian.class,true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityRabbit.class,true));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_MOUSEOPEN, Boolean.valueOf(false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
    }

    public boolean isMouseOpen()
    {
        return ((Boolean)this.dataManager.get(IS_MOUSEOPEN)).booleanValue();
    }

    public void setMouseOpen(boolean standing)
    {
        this.dataManager.set(IS_MOUSEOPEN, Boolean.valueOf(standing));
    }

    @SideOnly(Side.CLIENT)
    public float getMouseAnimationScale(float p_189795_1_)
    {
        return (this.clientSideMouseAnimation0 + (this.clientSideMouseAnimation - this.clientSideMouseAnimation0) * p_189795_1_) / 6.0F;
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote) {
            this.clientSideMouseAnimation0 = this.clientSideMouseAnimation;

            if (this.isMouseOpen()) {
                this.clientSideMouseAnimation = MathHelper.clamp(this.clientSideMouseAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideMouseAnimation = MathHelper.clamp(this.clientSideMouseAnimation - 1.0F, 0.0F, 6.0F);
            }
        }

        if (this.warningSoundTicks > 0)
        {
            --this.warningSoundTicks;
        }
    }

    protected void playAttackSound()
    {
        if (this.warningSoundTicks <= 0)
        {
            this.playSound(SoundEvents.EVOCATION_FANGS_ATTACK, 1.0F, 1.4F);
            this.warningSoundTicks = 40;
        }
    }

    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
    }

    class AIMeleeAttack extends EntityAIAttackMelee
    {
        public AIMeleeAttack()
        {
            super(EntityTofuChinger.this, 1.2D, false);
        }

        protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
        {
            double d0 = this.getAttackReachSqr(p_190102_1_);

            if (p_190102_2_ <= d0 && this.attackTick <= 0)
            {
                this.attackTick = 20;
                this.attacker.attackEntityAsMob(p_190102_1_);
                EntityTofuChinger.this.setMouseOpen(false);
            }
            else if (p_190102_2_ <= d0 * 2.0D)
            {
                if (this.attackTick <= 0)
                {
                    EntityTofuChinger.this.setMouseOpen(false);
                    this.attackTick = 20;
                }

                if (this.attackTick <= 10)
                {
                    EntityTofuChinger.this.setMouseOpen(true);

                }

                if (this.attackTick <= 5)
                {
                    EntityTofuChinger.this.playAttackSound();
                }
            }
            else
            {
                this.attackTick = 20;
                EntityTofuChinger.this.setMouseOpen(false);
            }
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            EntityTofuChinger.this.setMouseOpen(false);
            super.resetTask();
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofuchinger;
    }
}
