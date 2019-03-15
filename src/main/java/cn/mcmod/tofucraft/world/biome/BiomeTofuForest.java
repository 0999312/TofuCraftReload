package cn.mcmod.tofucraft.world.biome;

import java.util.Random;

import cn.mcmod.tofucraft.entity.EntityTofuChinger;
import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeTofuForest extends BiomeTofu {
    public BiomeTofuForest(BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = 10;
        this.chanceOfLeeks = 10;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 3, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTofuChinger.class, 1, 1, 2));
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
