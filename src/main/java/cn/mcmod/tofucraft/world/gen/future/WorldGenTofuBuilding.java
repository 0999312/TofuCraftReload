package cn.mcmod.tofucraft.world.gen.future;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenerator;
import cn.mcmod.tofucraft.block.BlockLoader;

public class WorldGenTofuBuilding extends WorldGenerator {

    protected final int minHeight;
    protected final IBlockState blockTofuState;

    public WorldGenTofuBuilding(boolean notify)
    {
        this(notify, 3, BlockLoader.tofuTerrain.getDefaultState());
    }
    
    public WorldGenTofuBuilding(boolean notify, int height, IBlockState tofustate)
    {
        super(notify);
        this.minHeight = height;
        this.blockTofuState = tofustate;
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
    
        int var6 = rand.nextInt(3) + this.minHeight;
        boolean var7 = true;
        int var3 = position.getX();
        int var4 = position.getY();
        int var5 = position.getZ();
        
        if (var4 >= 1 && var4 + var6 + 1 <= 256)
        {
            byte var9;
            int var11;
            BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

            for (int var8 = var4; var8 <= var4 + 1 + var6; ++var8)
            {
                var9 = 1;

                if (var8 == var4)
                {
                    var9 = 0;
                }

                if (var8 >= var4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (int var10 = var3 - var9; var10 <= var3 + var9 && var7; ++var10)
                {
                    for (var11 = var5 - var9; var11 <= var5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                        	mutablepos.setPos(var10, var8, var11);
                        	
                            if (!worldIn.isAirBlock(mutablepos))
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                Block var8 = worldIn.getBlockState(position.down()).getBlock();

                if ((var8 == BlockLoader.tofuTerrain || var8 == BlockLoader.MOMENTOFU)
                        && var4 < 256 - var6 - 1)
                {
                    this.buildTofu(position, var6, worldIn, rand);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
	
    protected void buildTofu(BlockPos pos, int height, World worldIn, Random rand)
    {
    	int ox = pos.getX();
    	int oy = pos.getY();
    	int oz = pos.getZ();
    	
        int radius = 1 + height / 2;
        BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

        for (int blockY = oy; blockY <= oy + height; ++blockY)
        {
            for (int blockX = ox - radius; blockX <= ox + radius; ++blockX)
            {

                for (int blockZ = oz - radius; blockZ <= oz + radius; ++blockZ)
                {
                    if (blockY == oy)
                    {
                        for (int y = oy - 1; y > 0; y--)
                        {
                        	mutablepos.setPos(blockX, y, blockZ);
                        	
                            Block blockId = worldIn.getBlockState(mutablepos).getBlock();
                            if (blockId == Blocks.AIR || blockId == BlockLoader.LEEK)
                            {
                                this.setBlockAndNotifyAdequately(worldIn, mutablepos, this.blockTofuState);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                	mutablepos.setPos(blockX, blockY, blockZ);

                    this.setBlockAndNotifyAdequately(worldIn, mutablepos, this.blockTofuState);
                }
            }
        }
        mutablepos.setPos(ox, oy + height + 1, oz);
        this.plantLeeks(mutablepos, radius, worldIn, rand);
    }
	
    protected void plantLeeks(BlockPos pos, int r, World world, Random random)
    {
        WorldGenerator var6 = new WorldGenBush(BlockLoader.LEEK);
        var6.generate(world, random, pos);
    }
}
