package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeTofuPlains extends BiomeTofu{
    public BiomeTofuPlains(BiomeProperties property)
    {
        super(property
        		.setBaseHeight(0.1f)
        		.setHeightVariation(0.1f));
        this.treesPerChunk = -999;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 2, 2, 3));
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
