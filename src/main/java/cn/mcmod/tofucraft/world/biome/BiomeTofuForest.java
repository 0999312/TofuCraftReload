package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.entity.EntityTofuChinger;
import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeTofuForest extends BiomeTofu {
    public BiomeTofuForest(BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = 10;
        this.chanceOfLeeks = 10;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 3, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTofuChinger.class, 1, 1, 2));
        this.chanceOfyuba = 5;
        this.maxYubaPerChunk = 30;
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
