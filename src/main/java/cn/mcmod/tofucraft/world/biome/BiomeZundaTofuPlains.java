package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;

import java.util.Random;

public class BiomeZundaTofuPlains extends BiomeTofu {
    private final WorldGenerator gemoreGen = new WorldGenMinable(BlockLoader.TOFUGEM_ORE.getDefaultState(), 2, BlockMatcher.forBlock(BlockLoader.tofuTerrain));


    public BiomeZundaTofuPlains(BiomeProperties property) {
        super(property.setBaseHeight(0.1f).setHeightVariation(0.1f));
        this.treesPerChunk = -999;
        this.topBlock = BlockLoader.zundatofuTerrain.getDefaultState();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 2, 2, 3));
    }

    @Override
    public WorldGenAbstractTree genBigTreeChance(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, gemoreGen, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            for (int l1 = 0; l1 < 14; ++l1) {
                this.gemoreGen.generate(worldIn, rand, pos.add(rand.nextInt(16), rand.nextInt(32) + 10, rand.nextInt(16)));
            }
    }
}
