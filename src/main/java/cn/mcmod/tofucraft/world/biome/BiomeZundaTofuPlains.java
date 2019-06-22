package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeZundaTofuPlains extends BiomeTofu {
    public BiomeZundaTofuPlains(BiomeProperties property) {
        super(property.setBaseHeight(0.1f).setHeightVariation(0.1f));
        this.treesPerChunk = -999;
        this.topBlock = BlockLoader.zundatofuTerrain.getDefaultState();
    }
    @Override
    public WorldGenAbstractTree genBigTreeChance(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
}
