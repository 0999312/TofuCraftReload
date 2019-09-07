package cn.mcmod.tofucraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MathUtil {
    public static RayTraceResult forwardsRaycast(Entity projectile, boolean includeEntities, boolean ignoreExcludedEntity, Entity excludedEntity)
    {
        double d0 = projectile.posX;
        double d1 = projectile.posY;
        double d2 = projectile.posZ;
        double d3 = projectile.motionX;
        double d4 = projectile.motionY;
        double d5 = projectile.motionZ;
        World world = projectile.world;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        Vec3d vec3d1 = new Vec3d(d0 + d3, d1 + d4, d2 + d5);
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, false, true, false);

        if (includeEntities)
        {
            if (raytraceresult != null)
            {
                vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = null;
            AxisAlignedBB entitySizeBox = new AxisAlignedBB(projectile.width, projectile.height, projectile.width, -projectile.width, -projectile.height, -projectile.width);
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, entitySizeBox.expand(d3, d4, d5).grow(1.0D).offset(vec3d));
            double d6 = 0.0D;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = list.get(i);

                if (entity1.canBeCollidedWith() && (ignoreExcludedEntity || !entity1.isEntityEqual(excludedEntity)) && !entity1.noClip)
                {
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

                    if (raytraceresult1 != null)
                    {
                        double d7 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

                        if (d7 < d6 || d6 == 0.0D)
                        {
                            entity = entity1;
                            d6 = d7;
                        }
                    }
                }
            }

            if (entity != null)
            {
                raytraceresult = new RayTraceResult(entity);
            }
        }

        return raytraceresult;
    }
}
