package cn.mcmod.tofucraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class EntityAIUseItemOnLeftHand<T extends EntityLiving> extends EntityAIBase {
    private final T field_220766_a;
    private final ItemStack field_220767_b;
    private final Predicate<? super T> field_220768_c;
    private final SoundEvent field_220769_d;
    private int tick = 0;

    //1.14.4 UseItemGoal backported to 1.12.2
    public EntityAIUseItemOnLeftHand(T p_i50319_1_, ItemStack p_i50319_2_, @Nullable SoundEvent p_i50319_3_, Predicate<? super T> p_i50319_4_) {
        this.field_220766_a = p_i50319_1_;
        this.field_220767_b = p_i50319_2_;
        this.field_220769_d = p_i50319_3_;
        this.field_220768_c = p_i50319_4_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return this.field_220768_c.test(this.field_220766_a);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.field_220766_a.isHandActive() || this.tick < 40;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.field_220766_a.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, this.field_220767_b.copy());
        this.field_220766_a.setActiveHand(EnumHand.OFF_HAND);
        this.tick = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.field_220766_a.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    @Override
    public void updateTask() {
        super.updateTask();

        if (++tick < 40) {
            this.field_220766_a.setActiveHand(EnumHand.OFF_HAND);
        }

        if (tick == 10) {
            if (this.field_220769_d != null) {
                this.field_220766_a.playSound(this.field_220769_d, 1.0F, this.field_220766_a.getRNG().nextFloat() * 0.2F + 0.9F);
            }
        }
    }
}