package cn.mcmod.tofucraft.world.gen.future;

import cn.mcmod.tofucraft.block.vine.BlockUnderVine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenUnderVine extends WorldGenerator
{
    private final BlockUnderVine block;

    public WorldGenUnderVine(BlockUnderVine blockIn)
    {
        this.block = blockIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < worldIn.getHeight() - 1) && this.block.canBlockStay(worldIn, blockpos))
            {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);

                if(worldIn.isAirBlock(blockpos.down()) && rand.nextFloat()< 0.8F) {
                    worldIn.setBlockState(blockpos.down(), this.block.getDefaultState(), 2);
                    if(worldIn.isAirBlock(blockpos.down(2)) && rand.nextFloat()< 0.6F) {
                        worldIn.setBlockState(blockpos.down(2), this.block.getDefaultState(), 2);
                        if(worldIn.isAirBlock(blockpos.down(3)) && rand.nextFloat()< 0.45F) {
                            worldIn.setBlockState(blockpos.down(3), this.block.getDefaultState(), 2);
                            if(worldIn.isAirBlock(blockpos.down(4)) && rand.nextFloat()< 0.25F) {
                                worldIn.setBlockState(blockpos.down(4), this.block.getDefaultState(), 2);
                                worldIn.neighborChanged(blockpos.down(4),this.block,position);
                            }
                            worldIn.neighborChanged(blockpos.down(3),this.block,position);
                        }
                        worldIn.neighborChanged(blockpos.down(2),this.block,position);
                    }
                    worldIn.neighborChanged(blockpos.down(),this.block,position);
                }
                worldIn.neighborChanged(blockpos,this.block,position);
            }
        }

        return true;
    }
}