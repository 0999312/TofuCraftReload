package cn.mcmod.tofucraft.world.gen.structure.tofumineshaft;

import cn.mcmod.tofucraft.world.biome.BiomeTofuBuildings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Map;

public class MapGenTofuMineshaft extends MapGenStructure
{
    private double chance = 0.004D;

    public MapGenTofuMineshaft()
    {
    }

    public String getStructureName()
    {
        return "TofuMineshaft";
    }

    public MapGenTofuMineshaft(Map<String, String> p_i2034_1_)
    {
        for (Map.Entry<String, String> entry : p_i2034_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("chance"))
            {
                this.chance = MathHelper.getDouble(entry.getValue(), this.chance);
            }
        }
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        int j = pos.getX() >> 4;
        int k = pos.getZ() >> 4;

        for (int l = 0; l <= 1000; ++l)
        {
            for (int i1 = -l; i1 <= l; ++i1)
            {
                boolean flag = i1 == -l || i1 == l;

                for (int j1 = -l; j1 <= l; ++j1)
                {
                    boolean flag1 = j1 == -l || j1 == l;

                    if (flag || flag1)
                    {
                        int k1 = j + i1;
                        int l1 = k + j1;
                        this.rand.setSeed((long)(k1 ^ l1) ^ worldIn.getSeed());
                        this.rand.nextInt();

                        if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1)))
                        {
                            return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                        }
                    }
                }
            }
        }

        return null;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        Biome biome = this.world.getBiome(new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8));
        MapGenTofuMineshaft.Type mapgenmineshaft$type = biome instanceof BiomeTofuBuildings ? Type.BUILDING : MapGenTofuMineshaft.Type.NORMAL;
        return new StructureTofuMineshaftStart(this.world, this.rand, chunkX, chunkZ, mapgenmineshaft$type);
    }

    public static enum Type
    {
        NORMAL,
        BUILDING;

        public static MapGenTofuMineshaft.Type byId(int id)
        {
            return id >= 0 && id < values().length ? values()[id] : NORMAL;
        }
    }
}