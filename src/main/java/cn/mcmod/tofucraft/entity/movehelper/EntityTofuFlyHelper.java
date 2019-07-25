package cn.mcmod.tofucraft.entity.movehelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityTofuFlyHelper extends EntityMoveHelper {
    public EntityTofuFlyHelper(EntityLiving p_i47418_1_) {
        super(p_i47418_1_);
    }

    public void onUpdateMoveHelper() {
        if (this.action == EntityMoveHelper.Action.STRAFE) {
            float f1;
            float f2 = this.moveForward;
            float f3 = this.moveStrafe;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (this.entity.onGround) {
                f1 = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            } else {
                f1 = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
            }

            if (f4 < 1.0F) {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.entity.rotationYaw * 0.017453292F);
            float f6 = MathHelper.cos(this.entity.rotationYaw * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigate pathnavigate = this.entity.getNavigator();

            if (pathnavigate != null) {
                NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();

                if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.entity.world, MathHelper.floor(this.entity.posX + (double) f7), MathHelper.floor(this.entity.posY), MathHelper.floor(this.entity.posZ + (double) f8)) != PathNodeType.WALKABLE) {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                }
            }

            if (f4 < 8.800000277905201E-7D) {
                this.entity.setMoveVertical(0.0F);
                this.entity.setMoveForward(0.0F);
                return;
            }

            this.entity.setAIMoveSpeed(f1);
            this.entity.setMoveForward(this.moveForward);
            this.entity.setMoveStrafing(this.moveStrafe);
            this.action = EntityMoveHelper.Action.WAIT;
        } else if (this.action == EntityMoveHelper.Action.MOVE_TO) {
            this.action = EntityMoveHelper.Action.WAIT;
            this.entity.setNoGravity(true);
            double d0 = this.posX - this.entity.posX;
            double d1 = this.posY - this.entity.posY;
            double d2 = this.posZ - this.entity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d3 < 8.800000277905201E-7D) {
                this.entity.setMoveVertical(0.0F);
                this.entity.setMoveForward(0.0F);
                return;
            }

            float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 10.0F);
            float f1;

            if (this.entity.onGround) {
                f1 = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            } else {
                f1 = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
            }

            this.entity.setAIMoveSpeed(f1);
            double d4 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
            float f2 = (float) (-(MathHelper.atan2(d1, d4) * (180D / Math.PI)));
            this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, f2, 10.0F);
            this.entity.setMoveVertical(d1 > 0.0D ? f1 : -f1);
        } else {
            this.entity.setNoGravity(false);
            this.entity.setMoveVertical(0.0F);
            this.entity.setMoveForward(0.0F);
        }
    }
}