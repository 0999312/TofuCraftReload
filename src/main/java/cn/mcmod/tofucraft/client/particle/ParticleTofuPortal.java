package cn.mcmod.tofucraft.client.particle;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class ParticleTofuPortal extends ParticlePortal {
    public ParticleTofuPortal(World world, double x, double y, double z, double velX, double velY, double velZ) {
        super(world,x,y,z,velX,velY,velZ);
        float f = this.rand.nextFloat() * 0.6F + 0.7F;
        this.particleRed = f * 0.9F;
        this.particleGreen = f * 0.9F;
        this.particleBlue = f * 0.9F;
    }
}
