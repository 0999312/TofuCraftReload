package cn.mcmod.tofucraft.world;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.world.gen.MapGenTofuCaves;
import cn.mcmod.tofucraft.world.gen.structure.MapGenTofuVillage;
import cn.mcmod.tofucraft.world.gen.structure.tofumineshaft.MapGenTofuMineshaft;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;

import java.util.List;
import java.util.Random;

public class ChunkProviderTofu implements IChunkGenerator {
    private static final IBlockState BEDROCK = BlockLoader.TOFUBEDROCK.getDefaultState();

    private final boolean mapFeaturesEnabled;

    private final Random rand;
    private World world;


    private final NoiseGeneratorOctaves minLimitPerlinNoise;

    private final NoiseGeneratorOctaves maxLimitPerlinNoise;

    private final NoiseGeneratorOctaves mainPerlinNoise;

    @SuppressWarnings("unused")
    private final NoiseGeneratorOctaves noiseGen4;

    protected final NoiseGeneratorPerlin surfaceNoise;
    private final NoiseGeneratorOctaves depthNoise;

  /*  private MapGenSkeltonIgloo mapGenSkeltonIgloo = new MapGenSkeltonIgloo();
*/
    private Biome[] biomesForGeneration;
    private final double[] heightMap;
    private final float[] biomeWeights;

    private double[] mainNoiseRegion;
    private double[] minLimitRegion;
    private double[] maxLimitRegion;
    private double[] depthRegion;

    protected double[] depthBuffer = new double[256];

    private int chunkX = 0;
    private int chunkZ = 0;

    private final WorldGenerator diamondGen = new WorldGenMinable(BlockLoader.TOFUORE_DIAMOND.getDefaultState(), 4, BlockMatcher.forBlock(BlockLoader.tofuTerrain));

    private MapGenBase caveGenerator = new MapGenTofuCaves();
    private MapGenTofuMineshaft mineshaft = new MapGenTofuMineshaft();
    private MapGenTofuVillage villageGenerator = new MapGenTofuVillage();
    public ChunkProviderTofu(World worldIn, long seed) {
        {
            villageGenerator = (MapGenTofuVillage) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(villageGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE);
            mineshaft = (MapGenTofuMineshaft) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(mineshaft, InitMapGenEvent.EventType.CUSTOM);
            caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(caveGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);
            this.mapFeaturesEnabled = worldIn.getWorldInfo().isMapFeaturesEnabled();
        }
        this.world = worldIn;
        this.rand = new Random(seed);
        this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
        this.depthNoise = new NoiseGeneratorOctaves(rand, 16);
        this.heightMap = new double[825];
        this.biomeWeights = new float[25];
        this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt((float) (j * j + k * k) + 0.2F);

                this.biomeWeights[j + 2 + (k + 2) * 5] = f;
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;

        this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double) (x * 16), (double) (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                Biome biome = biomesIn[j + i * 16];
                biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {

        this.chunkX = x;
        this.chunkZ = z;
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.setBlocksInChunk(x, z, chunkprimer);
        this.buildSurfaces(chunkprimer);
        this.setBedRock(chunkprimer);

        this.caveGenerator.generate(this.world, x, z, chunkprimer);


        if (this.mapFeaturesEnabled) {
            this.mineshaft.generate(this.world, x, z, chunkprimer);
            this.villageGenerator.generate(this.world, x, z, chunkprimer);

        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }


    private void setBedRock(ChunkPrimer primer) {
        for (int i = 0; i < 16; ++i) {

            for (int j = 0; j < 16; ++j) {
                IBlockState iblockstate2 = primer.getBlockState(i, 1, j);
                if (iblockstate2.getBlock() == BlockLoader.tofuTerrain) {
                    primer.setBlockState(i, 0, j, BEDROCK);
                    primer.setBlockState(i, 1, j, BEDROCK);
                }

            }
        }
    }

    //generate frigidstone and biomes block
    private void buildSurfaces(ChunkPrimer primer) {
        int l;
        IBlockState iblockstate, iblockstate1, desertstone;
        for (int i = 0; i < 16; ++i) {

            for (int j = 0; j < 16; ++j) {
                l = -1;

                Biome biome = this.biomesForGeneration[j + i * 16];
                iblockstate = biome.topBlock;
                iblockstate1 = biome.fillerBlock;
                desertstone = BlockLoader.tofuTerrain.getDefaultState();

                for (int i1 = 127; i1 >= 0; --i1) {

                    IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

                    if (iblockstate2.getMaterial() == Material.AIR) {
                        l = -3;

                    } else if (iblockstate2.getBlock() == BlockLoader.tofuTerrain) {
                        if (l <= -1) {
                            if (l == -3) {
                                primer.setBlockState(i, i1, j, iblockstate);
                            } else {
                                primer.setBlockState(i, i1, j, iblockstate1);

                            }

                            l += 1;

                        } else if (l > 0) {
                            --l;
                            primer.setBlockState(i, i1, j, desertstone);

                        }
                    }
                }
            }
        }
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {

        byte seaLevel = 63;

        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);

        this.generateHeightmap(x * 4, 0, z * 4);


        for (int k = 0; k < 4; ++k) {

            int l = k * 5;

            int i1 = (k + 1) * 5;


            for (int j1 = 0; j1 < 4; ++j1) {

                int k1 = (l + j1) * 33;

                int l1 = (l + j1 + 1) * 33;

                int i2 = (i1 + j1) * 33;

                int j2 = (i1 + j1 + 1) * 33;


                for (int k2 = 0; k2 < 32; ++k2) {

                    double d0 = 0.125D;

                    double d1 = this.heightMap[k1 + k2];

                    double d2 = this.heightMap[l1 + k2];

                    double d3 = this.heightMap[i2 + k2];

                    double d4 = this.heightMap[j2 + k2];

                    double d5 = (this.heightMap[k1 + k2 + 1] - d1) * d0;

                    double d6 = (this.heightMap[l1 + k2 + 1] - d2) * d0;

                    double d7 = (this.heightMap[i2 + k2 + 1] - d3) * d0;

                    double d8 = (this.heightMap[j2 + k2 + 1] - d4) * d0;


                    for (int l2 = 0; l2 < 8; ++l2) {

                        double d9 = 0.25D;

                        double d10 = d1;

                        double d11 = d2;

                        double d12 = (d3 - d1) * d9;

                        double d13 = (d4 - d2) * d9;


                        for (int i3 = 0; i3 < 4; ++i3) {

                            double d14 = 0.25D;

                            double d16 = (d11 - d10) * d14;

                            double d15 = d10 - d16;


                            for (int k3 = 0; k3 < 4; ++k3) {

                                if ((d15 += d16) > 0.0D) {

                                    primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, BlockLoader.tofuTerrain.getDefaultState());

                                } else if (k2 * 8 + l2 < seaLevel) {

                                    primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, BlockLoader.SOYMILK.getDefaultState());

                                }

                            }


                            d10 += d12;

                            d11 += d13;

                        }


                        d1 += d5;

                        d2 += d6;

                        d3 += d7;

                        d4 += d8;

                    }

                }

            }

        }

    }


    private void generateHeightmap(int x, int zero, int z) {


        this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, x, z, 5, 5, 200.0D, 200.0D, 0.5D);

        this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, x, zero, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);

        this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);

        this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);

        int terrainIndex = 0;

        int noiseIndex = 0;


        for (int ax = 0; ax < 5; ++ax) {

            for (int az = 0; az < 5; ++az) {

                float totalVariation = 0.0F;

                float totalHeight = 0.0F;

                float totalFactor = 0.0F;

                byte two = 2;

                Biome biome = this.biomesForGeneration[ax + 2 + (az + 2) * 10];


                for (int ox = -two; ox <= two; ++ox) {

                    for (int oz = -two; oz <= two; ++oz) {

                        Biome biome1 = this.biomesForGeneration[ax + ox + 2 + (az + oz + 2) * 10];

                        float rootHeight = biome1.getBaseHeight();

                        float heightVariation = biome1.getHeightVariation();


                        float heightFactor = this.biomeWeights[ox + 2 + (oz + 2) * 5] / (rootHeight + 2.0F);


                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {

                            heightFactor /= 2.0F;

                        }


                        totalVariation += heightVariation * heightFactor;

                        totalHeight += rootHeight * heightFactor;

                        totalFactor += heightFactor;

                    }

                }

                totalVariation /= totalFactor;

                totalHeight /= totalFactor;

                totalVariation = totalVariation * 0.9F + 0.1F;

                totalHeight = (totalHeight * 4.0F - 1.0F) / 8.0F;

                double terrainNoise = this.depthRegion[noiseIndex] / 8000.0D;

                if (terrainNoise < 0.0D) {

                    terrainNoise = -terrainNoise * 0.3D;

                }

                terrainNoise = terrainNoise * 3.0D - 2.0D;

                if (terrainNoise < 0.0D) {

                    terrainNoise /= 2.0D;

                    if (terrainNoise < -1.0D) {

                        terrainNoise = -1.0D;

                    }
                    terrainNoise /= 1.4D;

                    terrainNoise /= 2.0D;

                } else {

                    if (terrainNoise > 1.0D) {

                        terrainNoise = 1.0D;

                    }
                    terrainNoise /= 8.0D;
                }

                ++noiseIndex;

                double heightCalc = (double) totalHeight;

                double variationCalc = (double) totalVariation;

                heightCalc += terrainNoise * 0.2D;

                heightCalc = heightCalc * 8.5D / 8.0D;

                double d5 = 8.5D + heightCalc * 4.0D;

                for (int ay = 0; ay < 33; ++ay) {

                    double d6 = ((double) ay - d5) * 12.0D * 128.0D / 256.0D / variationCalc;

                    if (d6 < 0.0D) {

                        d6 *= 4.0D;

                    }

                    double d7 = this.minLimitRegion[terrainIndex] / 512.0D;
                    double d8 = this.maxLimitRegion[terrainIndex] / 512.0D;
                    double d9 = (this.mainNoiseRegion[terrainIndex] / 10.0D + 1.0D) / 2.0D;
                    double terrainCalc = MathHelper.clampedLerp(d7, d8, d9) - d6;


                    if (ay > 29) {

                        double d11 = (double) ((float) (ay - 29) / 3.0F);

                        terrainCalc = terrainCalc * (1.0D - d11) + -10.0D * d11;

                    }
                    this.heightMap[terrainIndex] = terrainCalc;
                    ++terrainIndex;
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);

        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

        rand.setSeed(world.getSeed());
        long xSeed = rand.nextLong() / 2L * 2L + 1L;
        long zSeed = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed(chunkX * xSeed + chunkZ * zSeed ^ world.getSeed());

        int i = x * 16;
        int j = z * 16;

        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(this.world, this.rand, diamondGen, blockpos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            for (int l1 = 0; l1 < 14; ++l1)
            {
                this.diamondGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16), this.rand.nextInt(32) + 10, this.rand.nextInt(16)));
            }


        ChunkPos chunkpos = new ChunkPos(x, z);

        if (mapFeaturesEnabled) {
            this.mineshaft.generateStructure(this.world, this.rand, chunkpos);
            this.villageGenerator.generateStructure(this.world, this.rand, chunkpos);
        }

        biome.decorate(world, rand, blockpos);


        if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, false, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
            WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);

        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);

        return biome.getSpawnableList(creatureType);
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        if (!this.mapFeaturesEnabled) {
            return false;
        } else if ("TofuMineshaft".equals(structureName) && this.mineshaft != null)
        {
            return this.mineshaft.isInsideStructure(pos);
        } else if ("TofuVillage".equals(structureName) && this.villageGenerator != null)
        {
            return this.villageGenerator.isInsideStructure(pos);
        } else {
            return false;
        }
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {

        if (!this.mapFeaturesEnabled) {
            return null;
        } else if ("TofuMineshaft".equals(structureName) && this.mineshaft != null)
        {
            return this.mineshaft.getNearestStructurePos(worldIn, position, findUnexplored);
        } else if ("TofuVillage".equals(structureName) && this.villageGenerator != null)
        {
            return this.villageGenerator.getNearestStructurePos(worldIn, position, findUnexplored);
        } else {
            return null;
        }

    }


    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {
        if (this.mapFeaturesEnabled) {
            this.mineshaft.generate(this.world, x, z, null);
            this.villageGenerator.generate(this.world, x, z, null);
        }
    }
}