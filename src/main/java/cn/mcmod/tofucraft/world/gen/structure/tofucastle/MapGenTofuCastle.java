package cn.mcmod.tofucraft.world.gen.structure.tofucastle;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.world.ChunkProviderTofu;
import cn.mcmod.tofucraft.world.biome.TofuBiomes;
import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapGenTofuCastle extends MapGenStructure {
    /**
     * A list of all the biomes villages can spawn in.
     */
    public static List<Biome> CASTLE_SPAWN_BIOMES = Arrays.<Biome>asList(TofuBiomes.TOFU_FOREST);
    /**
     * None
     */
    private int size;
    private int distance;
    private final ChunkProviderTofu provider;

    public MapGenTofuCastle(ChunkProviderTofu providerIn) {
        this.distance = 34;
        this.provider = providerIn;
    }

    public String getStructureName() {
        return "TofuCastle";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0) {
            chunkX -= this.distance - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= this.distance - 1;
        }

        int k = chunkX / this.distance;
        int l = chunkZ / this.distance;
        Random random = this.world.setRandomSeed(k, l, 14267312);
        k = k * this.distance;
        l = l * this.distance;
        k = k + random.nextInt(this.distance - 8);
        l = l + random.nextInt(this.distance - 8);

        if (i == k && j == l) {
            boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, CASTLE_SPAWN_BIOMES);

            if (flag) {
                return true;
            }
        }

        return false;
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.distance, 8, 10387312, false, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new MapGenTofuCastle.Start(this.world, this.provider, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart {
        private boolean isValid;


        public Start() {
        }

        public Start(World p_i47235_1_, ChunkProviderTofu p_i47235_2_, Random p_i47235_3_, int p_i47235_4_, int p_i47235_5_) {
            super(p_i47235_4_, p_i47235_5_);
            this.create(p_i47235_1_, p_i47235_2_, p_i47235_3_, p_i47235_4_, p_i47235_5_);
        }

        private void create(World p_191092_1_, ChunkProviderTofu p_191092_2_, Random p_191092_3_, int p_191092_4_, int p_191092_5_) {
            Rotation rotation = Rotation.NONE;
            ChunkPrimer chunkprimer = new ChunkPrimer();
            p_191092_2_.setBlocksInChunk(p_191092_4_, p_191092_5_, chunkprimer);
            int i = 5;
            int j = 5;


            int k = chunkprimer.findGroundBlockIdx(7, 7);
            int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
            int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
            int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
            int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));


            BlockPos blockpos = new BlockPos(p_191092_4_ * 16 + 8, k1, p_191092_5_ * 16 + 8);
            List<TofuCastlePiece.TofuCastleTemplate> list = Lists.<TofuCastlePiece.TofuCastleTemplate>newLinkedList();
            TofuCastlePiece.generateCore(p_191092_1_.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, list, p_191092_3_);
            this.components.addAll(list);
            this.updateBoundingBox();
            this.isValid = true;

        }

        /**
         * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
         */
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
            super.generateStructure(worldIn, rand, structurebb);
            int i = this.boundingBox.minY;

            for (int j = structurebb.minX; j <= structurebb.maxX; ++j) {
                for (int k = structurebb.minZ; k <= structurebb.maxZ; ++k) {
                    BlockPos blockpos = new BlockPos(j, i, k);

                    if (!worldIn.isAirBlock(blockpos) && this.boundingBox.isVecInside(blockpos)) {
                        boolean flag = false;

                        for (StructureComponent structurecomponent : this.components) {
                            if (structurecomponent.getBoundingBox().isVecInside(blockpos)) {
                                flag = true;
                                break;
                            }
                        }

                        if (flag) {
                            for (int l = i - 1; l > 1; --l) {
                                BlockPos blockpos1 = new BlockPos(j, l, k);

                                if (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getMaterial().isLiquid()) {
                                    break;
                                }

                                worldIn.setBlockState(blockpos1, BlockLoader.tofuTerrain.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }

        /**
         * currently only defined for Villages, returns true if Village has more than 2 non-road components
         */
        public boolean isSizeableStructure() {
            return this.isValid;
        }
    }
}