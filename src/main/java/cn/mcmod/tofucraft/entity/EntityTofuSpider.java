package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.ai.EntityAIAttackMoveRanged;
import cn.mcmod.tofucraft.entity.projectile.EntityFukumame;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTofuSpider extends EntitySpider implements IRangedAttackMob {
    public EntityTofuSpider(World worldIn) {
        super(worldIn);
        this.setSize(1.2F, 0.7F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackMoveRanged<>(this, 1.0D, 24, 17.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class,true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class,true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    public double getMountedYOffset()
    {
        return (double)(this.height * 0.9F);
    }

    public float getEyeHeight()
    {
        return 0.36F;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        for (int i = 0; i < 4; i++) {
            EntityThrowable projectile = new EntityFukumame(this.world, this);

            Vec3d vec3d = this.getLook(1.0F);

            playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

            projectile.setLocationAndAngles(this.posX + vec3d.x * 1.3D, this.posY + this.getEyeHeight(), this.posZ + vec3d.z * 1.3D, this.rotationYaw, this.rotationPitch);

            float d0 = (this.rand.nextFloat() * 16.0F) - 8.0F;

            projectile.shoot(this, this.rotationPitch,this.rotationYaw + d0,0.0F, 1.5f, 0.8F);
            this.world.spawnEntity(projectile);
        }
    }

    @Override
    public boolean getCanSpawnHere() {

        if (this.dimension == TofuMain.TOFU_DIMENSION.getId() && this.rand.nextInt(10) == 0
                && this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(32.0D, 10.0D, 32.0D)).size() == 0) {
            return super.getCanSpawnHere();
        }
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return TofuLootTables.tofuspider;
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
