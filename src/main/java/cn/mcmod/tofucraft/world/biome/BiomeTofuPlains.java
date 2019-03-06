package cn.mcmod.tofucraft.world.biome;

import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeTofuPlains extends BiomeTofu{
    public BiomeTofuPlains(BiomeProperties property)
    {
        super(property
        		.setBaseHeight(0.1f)
        		.setHeightVariation(0.1f));
        this.treesPerChunk = -999;
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
