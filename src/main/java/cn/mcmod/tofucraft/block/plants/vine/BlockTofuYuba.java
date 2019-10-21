package cn.mcmod.tofucraft.block.plants.vine;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTofuYuba extends BlockUnderVine implements net.minecraftforge.common.IShearable, IGrowable {
    public BlockTofuYuba() {
        super();
        this.setCreativeTab(CommonProxy.tab);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (rand.nextInt(40) == 0) {
            if (worldIn.isAirBlock(pos.down()) && this.canBlockStay(worldIn, pos.down())) {
                worldIn.setBlockState(pos.down(), this.getDefaultState());
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemLoader.material;
    }

    public int damageDropped(IBlockState state) {
        return 32;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.growByBonemeal(worldIn, rand, pos);
    }

    private void growByBonemeal(World worldIn, Random rand, BlockPos blockpos) {
        if (worldIn.isAirBlock(blockpos.down()) && rand.nextFloat() < 0.8F) {
            worldIn.setBlockState(blockpos.down(), this.getDefaultState(), 2);
            if (worldIn.isAirBlock(blockpos.down(2)) && rand.nextFloat() < 0.6F) {
                worldIn.setBlockState(blockpos.down(2), this.getDefaultState(), 2);
                if (worldIn.isAirBlock(blockpos.down(3)) && rand.nextFloat() < 0.45F) {
                    worldIn.setBlockState(blockpos.down(3), this.getDefaultState(), 2);
                    if (worldIn.isAirBlock(blockpos.down(4)) && rand.nextFloat() < 0.25F) {
                        worldIn.setBlockState(blockpos.down(4), this.getDefaultState(), 2);
                        worldIn.neighborChanged(blockpos.down(4), this, blockpos);
                    }
                    worldIn.neighborChanged(blockpos.down(3), this, blockpos);
                }
                worldIn.neighborChanged(blockpos.down(2), this, blockpos);
            }
            worldIn.neighborChanged(blockpos.down(), this, blockpos);
        }
        worldIn.neighborChanged(blockpos, this, blockpos);
    }
}
