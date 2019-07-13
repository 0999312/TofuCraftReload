package cn.mcmod.tofucraft.world.gen.future;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrops extends WorldGenerator {

    /** The ID of the plant block used in this plant generator. */
    protected BlockBush plantBlock;

    public WorldGenCrops(BlockBush blockIn)
    {
        this.plantBlock = blockIn;
    }
    
    protected IBlockState getStateToPlace() {
    	return this.plantBlock.getDefaultState();
    }
	
    @Override
    public boolean generate(World par1World, Random par2Random, BlockPos pos) {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
    	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
    	
    	
        for (int l = 0; l < 64; ++l)
        {
            int i1 = x + par2Random.nextInt(8) - par2Random.nextInt(8);
            int j1 = y + par2Random.nextInt(4) - par2Random.nextInt(4);
            int k1 = z + par2Random.nextInt(8) - par2Random.nextInt(8);
            mutable.setPos(i1, j1, k1);
            IBlockState state = this.getStateToPlace();
            
            if (par1World.isAirBlock(mutable) && this.plantBlock.canBlockStay(par1World, mutable, state))
            {
                par1World.setBlockState(mutable, state, 2);
            }
        }

        return true;
    }
}
