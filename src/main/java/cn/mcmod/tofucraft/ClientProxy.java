package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TofuParticleType;
import cn.mcmod.tofucraft.client.particle.ParticleTofuPortal;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	 	@Override
	    public void preInit(FMLPreInitializationEvent event)
	    {
	        super.preInit(event);
			BlockLoader.registerRenders();
	        ItemLoader.registerRenders();
			TofuEntityRegister.entityRender();
	    }

	    @Override
	    public void init(FMLInitializationEvent event)
	    {
	        super.init(event);
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event)
	    {
	        super.postInit(event);
	    }

	public static void registerFluidBlockRendering(Block block, String name) {
		final ModelResourceLocation fluidLocation = new ModelResourceLocation(TofuMain.MODID.toLowerCase() + ":fluids", name);
		// use a custom state mapper which will ignore the LEVEL property
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return fluidLocation;
			}
		});
	}

	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().world;
	}

	public static void spawnParticle(World world, TofuParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {
		Minecraft mc = Minecraft.getMinecraft();
		Entity entity = mc.getRenderViewEntity();
		// ignore the passed-in world, since on SP we get the integrated server world, which is not really what we want
		if (entity != null && mc.effectRenderer != null) {
			int i = mc.gameSettings.particleSetting;
			if (i == 1 && world.rand.nextInt(3) == 0) {
				i = 2;
			}
			double d0 = entity.posX - x;
			double d1 = entity.posY - y;
			double d2 = entity.posZ - z;
			if (d0 * d0 + d1 * d1 + d2 * d2 <= 1024D && i <= 1) {
				Particle particle = null;
				switch (particleType) {
					case TOFUPORTAL:
						particle = new ParticleTofuPortal(world, x, y, z, velX, velY, velZ);
						break;
				}

				if (particle != null) {
					mc.effectRenderer.addEffect(particle);
				}
			}
		}
	}
}
