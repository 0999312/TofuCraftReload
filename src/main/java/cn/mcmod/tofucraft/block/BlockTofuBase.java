package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.material.TofuMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public class BlockTofuBase extends Block implements IGrowable{

    public BlockTofuBase() {
        super(TofuMaterial.tofu);
    }

    public BlockTofuBase(Material material) {
        super(material);
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 4;
    }

    /**
     * Whether the tofu can be scooped with Tofu Scoop
     */
    private boolean scoopable = true;

    public BlockTofuBase setScoopable(boolean b) {
        this.scoopable = b;
        return this;
    }

    public boolean isScoopable() {
        return this.scoopable;
    }


    public ItemStack createScoopedBlockStack() {
        return new ItemStack(this);
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        BlockPos blockpos = pos.up();

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos1 = blockpos;
            int j = 0;

            while (true)
            {
                if (j >= i / 16)
                {
                    if (worldIn.isAirBlock(blockpos1))
                    {
                    	if (rand.nextInt(8) == 0) 	plantFlower(worldIn, rand, blockpos1);
                    }
                    break;
                }

                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (!(worldIn.getBlockState(blockpos1.down()).getBlock() instanceof BlockTofuBase) || worldIn.getBlockState(blockpos1).isNormalCube())
                {
                    break;
                }

                ++j;
	            }
	        }
	    }
	
    
    public void plantFlower(World world, Random rand, BlockPos pos)
    {
        if (!BlockLoader.LEEK.canBlockStay(world, pos, BlockLoader.LEEK.getDefaultState()))
        {
            return;
        }

        world.setBlockState(pos, BlockLoader.LEEK.getDefaultState(), 3);
    }
}