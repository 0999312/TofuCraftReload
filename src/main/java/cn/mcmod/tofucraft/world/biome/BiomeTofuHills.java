package cn.mcmod.tofucraft.world.biome;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import cn.mcmod.tofucraft.block.BlockLoader;

public class BiomeTofuHills extends BiomeTofu {
	
    public BiomeTofuHills(BiomeProperties property)
    {
        super(property);
    }

    @Override
    public void decorate(World worldIn, Random randomIn, BlockPos pos)
    {
        super.decorate(worldIn, randomIn, pos);
        
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int k = 0; k < 1; ++k)
        {
            int x = pos.getX() + randomIn.nextInt(16) + 8;
            int z = pos.getZ() + randomIn.nextInt(16) + 8;

            int y = worldIn.getHeight(x, z) - 1;
            
            mutable.setPos(x, y, z);
            if (y > 80 && worldIn.getBlockState(mutable).getBlock() == BlockLoader.tofuTerrain)
            {
                if (worldIn.getBlockState(mutable.east()).getBlock() == BlockLoader.tofuTerrain
                        && worldIn.getBlockState(mutable.south()).getBlock() == BlockLoader.tofuTerrain
                        && worldIn.getBlockState(mutable.west()).getBlock() == BlockLoader.tofuTerrain
                        && worldIn.getBlockState(mutable.north()).getBlock() == BlockLoader.tofuTerrain)
                {
                    int h = randomIn.nextInt(3) + 3;
                    for (int i = 0; i < h; i++)
                    {
                        worldIn.setBlockState(new BlockPos(mutable.setPos(x, y + i, z)), BlockLoader.SOYMILK.getDefaultState(), 2);
                    }
                }
            }
        }

    }
}
