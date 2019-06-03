package cn.mcmod.tofucraft.world.gen.future;

import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TofuOreGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider instanceof WorldProviderSurface) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);

            BlockPos pos = getHeight(world, new BlockPos(x, 0, z));

            this.generateOre(world, random, chunkX << 4, chunkZ << 4);

        }
    }

    public static BlockPos getHeight(World world, BlockPos pos) {

        for (int y = 0; y < 256; y++) {

            BlockPos pos1 = pos.up(y);

            if (world.getBlockState(pos1.up()).getBlock() == Blocks.AIR && world.getBlockState(pos1.down()).getBlock() != Blocks.AIR) {

                return pos1;

            }

        }

        return pos;

    }

    private void generateOre(World world, Random random, int x, int z) {
        //1チャンクで生成したい回数だけ繰り返す。
        if (world.provider instanceof WorldProviderSurface) {

            for (int i = 0; i < 9; i++) {
                int genX = x + random.nextInt(16);
                int genY = 10 + random.nextInt(60);
                int genZ = z + random.nextInt(16);
                new WorldGenMinable(
                        BlockLoader.TOFUGEM_ORE.getDefaultState(), 3 + random.nextInt(6)).generate(world, random, new BlockPos(genX, genY, genZ));
            }
        }
    }
}