package cn.mcmod.tofucraft.entity.projectile;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.advancements.TofuAdvancements;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityZundaArrow extends EntityArrow {
    private int duration = 200;

    public EntityZundaArrow(World worldIn) {
        super(worldIn);
    }

    public EntityZundaArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public EntityZundaArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote && !this.inGround) {
            this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    protected ItemStack getArrowStack() {
        return new ItemStack(ItemLoader.zundaArrow);
    }

    protected void onHit(RayTraceResult raytraceResultIn) {
        Entity entity = raytraceResultIn.entityHit;

        if (entity != null) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceil((double) f * this.getDamage());

            if (this.getIsCritical()) {
                i += this.rand.nextInt(i / 2 + 2);
            }

            PotionEffect potioneffect = new PotionEffect(MobEffects.REGENERATION, this.duration, 0);

            DamageSource damagesource = TofuMain.zunda;
            
            if (entity instanceof EntityLivingBase){
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                if(entity instanceof EntitySlime){
               	 EntitySlime slime = (EntitySlime)entity;
               	if (!world.isRemote)
                   {
                       for (int J1 = 0; J1 < slime.getSlimeSize(); J1++)
                       {
                           slime.entityDropItem(new ItemStack(ItemLoader.tofu_food,1,9), 0.2f);
                       }

                   }
               	slime.setDead();
               }else
                if (entitylivingbase.isEntityUndead()) {

                    if (!this.world.isRemote) {
                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                    }
                    entity.attackEntityFrom(damagesource, (float) i);

                    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                    //knockback
                    if (f1 > 0.0F) {
                        entitylivingbase.addVelocity(this.motionX * 0.2D * 0.6000000238418579D / (double) f1, 0.1D, this.motionZ * (double) 0.2D * 0.6000000238418579D / (double) f1);
                    }


                    if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);

                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }

                } else {
                    (entitylivingbase).addPotionEffect(potioneffect);
                    if(this.shootingEntity instanceof EntityPlayer){
                        EntityPlayer player = (EntityPlayer) this.shootingEntity;
                        TofuAdvancements.grantAdvancement(player, "caring_shooting");
                    }
                }
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                if (!(entity instanceof EntityEnderman)) {
                    this.setDead();
                }
            } else {
                super.onHit(raytraceResultIn);
            }
        } else {
            super.onHit(raytraceResultIn);
        }
    }

    protected void arrowHit(EntityLivingBase living)
    {
        super.arrowHit(living);
        PotionEffect potioneffect = new PotionEffect(MobEffects.REGENERATION, this.duration, 0);
        living.addPotionEffect(potioneffect);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Duration")) {
            this.duration = compound.getInteger("Duration");
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Duration", this.duration);
    }
}