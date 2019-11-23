package cn.mcmod.tofucraft.block.tofu;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.util.TofuBlockUtils;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTofuStairs extends BlockStairs {

    private boolean isFragile = false;

    public BlockTofuStairs(IBlockState state)
    {
        super(state);
        this.setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT));

        this.useNeighborBrightness = true;
    }

    public BlockStairs setFragile()
    {
        isFragile = true;
        this.setTickRandomly(true);
        return this;
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, BlockPos pos, Entity par5Entity, float par6)
    {
        if (isFragile)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        super.updateTick(par1World, pos, state, par5Random);

        if (isFragile)
        {
            IBlockState weightBlock = par1World.getBlockState(pos.up());

            if (weightBlock != null)
            {
                if (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON)
                {
                    dropBlockAsItem(par1World, pos, state, 0);
                    par1World.setBlockToAir(pos);
                }
            }
        }
    }
}
