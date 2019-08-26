package cn.mcmod.tofucraft.entity.projectile.ammo;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAmmoTest extends EntityAmmoBase {
    public EntityAmmoTest(World worldIn) {
        super(worldIn);
        this.setSize(2,2);
    }

    public EntityAmmoTest(World world, Entity shooter){
        this(world);
        this.shootingEntity = shooter;
    }

    @Override
    public EntityAmmoBase create(World world, Entity shooter) {
        return new EntityAmmoTest(world, shooter);
    }

    @Override
    public float getVelocity() {
        return 0.4f;
    }

    @Override
    public void onHit(RayTraceResult result) {
        TofuMain.logger.info("Hit!");
        if (result.entityHit != null){
            result.entityHit.setDead();
        }
        super.onHit(result);
    }

}
