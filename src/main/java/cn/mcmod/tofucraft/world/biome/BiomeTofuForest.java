package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.entity.EntityTofuChinger;
import cn.mcmod.tofucraft.entity.EntityTofuCow;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenerator;

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

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        int j, k, l, i1;

        if (rand.nextInt(30) == 0) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (j = 0; j < 5; j++) {
                k = pos.getX() + rand.nextInt(16) + 8;
                l = rand.nextInt(128);
                i1 = pos.getZ() + rand.nextInt(16) + 8;
                mutable.setPos(k, l, i1);

                WorldGenerator var6 = new WorldGenBush(BlockLoader.TOFUFLOWER);
                var6.generate(worldIn, rand, mutable);
            }
        }
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
