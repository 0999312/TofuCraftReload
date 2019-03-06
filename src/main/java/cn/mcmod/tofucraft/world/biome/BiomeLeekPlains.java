package cn.mcmod.tofucraft.world.biome;

import java.util.Random;

import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeLeekPlains extends BiomeTofu {
    public BiomeLeekPlains(Biome.BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = -999;
        this.chanceOfLeeks = 1;
        this.maxGrassPerChunk = 5;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 3, 4, 4));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenAbstractTree genBigTreeChance(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
    
}
