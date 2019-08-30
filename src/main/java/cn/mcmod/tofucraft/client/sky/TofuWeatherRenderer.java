package cn.mcmod.tofucraft.client.sky;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.IRenderHandler;

import java.util.Random;

public class TofuWeatherRenderer extends IRenderHandler {

    public static final TofuWeatherRenderer INSTANCE = new TofuWeatherRenderer();

    private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation(TofuMain.MODID, "textures/environment/tofurain.png");

    private long rendererUpdateTick;

    private final float[] rainXCoords = new float[1024];
    private final float[] rainYCoords = new float[1024];

    private int soundCounter;

    private final Random random = new Random();
    private int rainSoundCounter;


    public TofuWeatherRenderer() {
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {

                float f = (float) (j - 16);
                float f1 = (float) (i - 16);
                float f2 = MathHelper.sqrt(f * f + f1 * f1);

                this.rainXCoords[i << 5 | j] = -f1 / f2;
                this.rainYCoords[i << 5 | j] = f / f2;

            }

        }
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        ++this.rendererUpdateTick;

        float f = mc.world.getRainStrength(partialTicks);

        renderNormalWeather(partialTicks, world, mc);

        this.addRainParticles(mc);
    }


    private void addRainParticles(Minecraft mc) {
        if (!mc.isGamePaused()) {
            float f = mc.world.getRainStrength(1.0F);

            if (!mc.gameSettings.fancyGraphics) {
                f /= 2.0F;
            }

            if (f != 0.0F) {
                this.random.setSeed((long) this.rendererUpdateTick * 312987231L);
                Entity entity = mc.getRenderViewEntity();
                World world = mc.world;
                BlockPos blockpos = new BlockPos(entity);
                int i = 10;
                double d0 = 0.0D;
                double d1 = 0.0D;
                double d2 = 0.0D;
                int j = 0;
                int k = (int) (100.0F * f * f);

                if (mc.gameSettings.particleSetting == 1) {
                    k >>= 1;
                } else if (mc.gameSettings.particleSetting == 2) {
                    k = 0;
                }

                for (int l = 0; l < k; ++l) {
                    BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(this.random.nextInt(10) - this.random.nextInt(10), 0, this.random.nextInt(10) - this.random.nextInt(10)));
                    Biome biome = world.getBiome(blockpos1);
                    BlockPos blockpos2 = blockpos1.down();
                    IBlockState iblockstate = world.getBlockState(blockpos2);

                    if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10 && biome.canRain() && biome.getTemperature(blockpos1) >= 0.15F) {
                        double d3 = this.random.nextDouble();
                        double d4 = this.random.nextDouble();
                        AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, blockpos2);

                        if (iblockstate.getMaterial() != Material.LAVA && iblockstate.getBlock() != Blocks.MAGMA) {
                            if (iblockstate.getMaterial() != Material.AIR) {
                                ++j;

                                if (this.random.nextInt(j) == 0) {
                                    d0 = (double) blockpos2.getX() + d3;
                                    d1 = (double) ((float) blockpos2.getY() + 0.1F) + axisalignedbb.maxY - 1.0D;
                                    d2 = (double) blockpos2.getZ() + d4;
                                }

                                mc.world.spawnParticle(EnumParticleTypes.WATER_DROP, (double) blockpos2.getX() + d3, (double) ((float) blockpos2.getY() + 0.1F) + axisalignedbb.maxY, (double) blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                            }
                        } else {
                            mc.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) blockpos1.getX() + d3, (double) ((float) blockpos1.getY() + 0.1F) - axisalignedbb.minY, (double) blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                        }
                    }
                }

                if (j > 0 && this.random.nextInt(5) < this.rainSoundCounter++) {
                    this.rainSoundCounter = 0;

                    if (d1 > (double) (blockpos.getY() + 1) && world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor((float) blockpos.getY())) {
                        mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
                    } else {
                        mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
                    }
                }
            }
        }
    }

    private void renderNormalWeather(float partialTicks, WorldClient world, Minecraft mc) {

        float f = mc.world.getRainStrength(partialTicks);


        if (f > 0.0F) {
            mc.entityRenderer.enableLightmap();
            Entity entity = mc.getRenderViewEntity();
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posY);
            int k = MathHelper.floor(entity.posZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            GlStateManager.disableCull();
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.alphaFunc(516, 0.1F);
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
            int l = MathHelper.floor(d1);
            int i1 = 5;

            if (mc.gameSettings.fancyGraphics) {
                i1 = 10;
            }

            int j1 = -1;
            float f1 = (float) this.rendererUpdateTick + partialTicks;
            vertexbuffer.setTranslation(-d0, -d1, -d2);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k1 = k - i1; k1 <= k + i1; ++k1) {
                for (int l1 = i - i1; l1 <= i + i1; ++l1) {
                    int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
                    double d3 = (double) this.rainXCoords[i2] * 0.5D;
                    double d4 = (double) this.rainYCoords[i2] * 0.5D;
                    blockpos$mutableblockpos.setPos(l1, 0, k1);
                    Biome biome = world.getBiome(blockpos$mutableblockpos);

                    if (biome.canRain() || biome.getEnableSnow()) {
                        int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
                        int k2 = j - i1;
                        int l2 = j + i1;

                        if (k2 < j2) {
                            k2 = j2;
                        }

                        if (l2 < j2) {
                            l2 = j2;
                        }

                        int i3 = j2;

                        if (j2 < l) {
                            i3 = l;
                        }

                        if (k2 != l2) {
                            world.rand.setSeed((long) (l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
                            blockpos$mutableblockpos.setPos(l1, k2, k1);
                            float f2 = biome.getTemperature(blockpos$mutableblockpos);


                            if (j1 != 1) {
                                if (j1 >= 0) {
                                    tessellator.draw();
                                }

                                j1 = 1;
                                mc.getTextureManager().bindTexture(RAIN_TEXTURES);
                                vertexbuffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }

                            //rain speed
                            double d8 = (double) (-((float) (this.rendererUpdateTick & 511) + partialTicks) / 30.0F);
                            double d9 = world.rand.nextDouble() + (double) f1 * 0.01D * (double) ((float) world.rand.nextGaussian());
                            double d10 = world.rand.nextDouble() + (double) (f1 * (float) world.rand.nextGaussian()) * 0.001D;
                            double d11 = (double) ((float) l1 + 0.5F) - entity.posX;
                            double d12 = (double) ((float) k1 + 0.5F) - entity.posZ;
                            float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / (float) i1;
                            float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f;
                            blockpos$mutableblockpos.setPos(l1, i3, k1);
                            int i4 = (world.getCombinedLight(blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
                            int j4 = i4 >> 16 & 65535;
                            int k4 = i4 & 65535;
                            vertexbuffer.pos((double) l1 - d3 + 0.5D, (double) l2, (double) k1 - d4 + 0.5D).tex(0.0D + d9, (double) k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                            vertexbuffer.pos((double) l1 + d3 + 0.5D, (double) l2, (double) k1 + d4 + 0.5D).tex(1.0D + d9, (double) k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                            vertexbuffer.pos((double) l1 + d3 + 0.5D, (double) k2, (double) k1 + d4 + 0.5D).tex(1.0D + d9, (double) l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                            vertexbuffer.pos((double) l1 - d3 + 0.5D, (double) k2, (double) k1 - d4 + 0.5D).tex(0.0D + d9, (double) l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();

                        }
                    }
                }
            }

            if (j1 >= 0) {
                tessellator.draw();
            }

            vertexbuffer.setTranslation(0.0D, 0.0D, 0.0D);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            mc.entityRenderer.disableLightmap();
        }
    }
}