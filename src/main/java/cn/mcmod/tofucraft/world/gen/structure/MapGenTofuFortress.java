package cn.mcmod.tofucraft.world.gen.structure;

import cn.mcmod.tofucraft.world.ChunkProviderTofu;
import cn.mcmod.tofucraft.world.biome.TofuBiomes;
import cn.mcmod.tofucraft.world.gen.structure.tofufortress.TofuFortressPiece;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
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

public class MapGenTofuFortress extends MapGenStructure
{
    /** A list of all the biomes villages can spawn in. */
    public static List<Biome> VILLAGE_SPAWN_BIOMES = Arrays.<Biome>asList(TofuBiomes.TOFU_FOREST,TofuBiomes.TOFU_FOREST_HILLS);
    /** None */
    private int size;
    private int distance;
    private final ChunkProviderTofu provider;

    public MapGenTofuFortress(ChunkProviderTofu providerIn)
    {
        this.provider = providerIn;
        this.distance = 38;
    }


    public String getStructureName()
    {
        return "TofuFortress";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.distance - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.distance - 1;
        }

        int k = chunkX / this.distance;
        int l = chunkZ / this.distance;
        Random random = this.world.setRandomSeed(k, l, 14368312);
        k = k * this.distance;
        l = l * this.distance;
        k = k + random.nextInt(this.distance - 8);
        l = l + random.nextInt(this.distance - 8);

        if (i == k && j == l)
        {
            boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, VILLAGE_SPAWN_BIOMES);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.distance, 8, 14368312, false, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenTofuFortress.Start(this.world, provider,this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        /** well ... thats what it does */
        private boolean vaild;

        public Start()
        {
        }

        public Start(World p_i47235_1_, ChunkProviderTofu p_i47235_2_, Random p_i47235_3_, int p_i47235_4_, int p_i47235_5_)
        {
            super(p_i47235_4_, p_i47235_5_);
            this.create(p_i47235_1_, p_i47235_2_, p_i47235_3_, p_i47235_4_, p_i47235_5_);
        }

        private void create(World p_191092_1_, ChunkProviderTofu p_191092_2_, Random p_191092_3_, int p_191092_4_, int p_191092_5_)
        {
            Rotation rotation = Rotation.values()[p_191092_3_.nextInt(Rotation.values().length)];
            ChunkPrimer chunkprimer = new ChunkPrimer();
            p_191092_2_.setBlocksInChunk(p_191092_4_, p_191092_5_, chunkprimer);
            int i = 5;
            int j = 5;

            if (rotation == Rotation.CLOCKWISE_90)
            {
                i = -5;
            }
            else if (rotation == Rotation.CLOCKWISE_180)
            {
                i = -5;
                j = -5;
            }
            else if (rotation == Rotation.COUNTERCLOCKWISE_90)
            {
                j = -5;
            }

            int k = chunkprimer.findGroundBlockIdx(7, 7);
            int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
            int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
            int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
            int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));


                BlockPos blockpos = new BlockPos(p_191092_4_ * 16 + 8, k1 -1, p_191092_5_ * 16 + 8);
                List<TofuFortressPiece.TofuTowerTemplate> list = Lists.<TofuFortressPiece.TofuTowerTemplate>newLinkedList();
                TofuFortressPiece.generateTower(p_191092_1_.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, list, p_191092_3_);
                this.components.addAll(list);
                this.updateBoundingBox();
                this.vaild = true;

        }

        /**
         * currently only defined for Villages, returns true if Village has more than 2 non-road components
         */
        public boolean isSizeableStructure()
        {
            return this.vaild;
        }

        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);
            int i = this.boundingBox.minY;

            for (int j = structurebb.minX; j <= structurebb.maxX; ++j)
            {
                for (int k = structurebb.minZ; k <= structurebb.maxZ; ++k)
                {
                    BlockPos blockpos = new BlockPos(j, i, k);

                    if (!worldIn.isAirBlock(blockpos) && this.boundingBox.isVecInside(blockpos))
                    {
                        boolean flag = false;

                        for (StructureComponent structurecomponent : this.components)
                        {
                            if (structurecomponent.getBoundingBox().isVecInside(blockpos))
                            {
                                flag = true;
                                break;
                            }
                        }
                        /*
                        if (flag)
                        {
                            for (int l = i - 1; l > 1; --l)
                            {
                                BlockPos blockpos1 = new BlockPos(j, l, k);

                                if (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getMaterial().isLiquid())
                                {
                                    break;
                                }

                                worldIn.setBlockState(blockpos1, B.getDefaultState(), 2);
                            }
                        }*/
                    }
                }
            }
        }

        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.vaild);
        }

        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            this.vaild = tagCompound.getBoolean("Valid");
        }
    }
}