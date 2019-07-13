package cn.mcmod.tofucraft.block.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import cn.mcmod.tofucraft.block.BlockLoader;

public class BlockSoyMilk extends BlockFluidClassic {
    public BlockSoyMilk(Fluid fluid) {
        super(fluid, Material.WATER);
        this.setTickRandomly(true);
        this.setResistance(1000.0F);
    }


    @Override
    public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            int heat = this.getHeatStrength(par1World, pos);
            if (par5Entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLiving = (EntityLivingBase)par5Entity;
                if (entityLiving.ticksExisted % 20 == 0)
                {
                	switch (heat) {
					case 1:
						entityLiving.heal(1f);
						break;
					case 2:
						entityLiving.attackEntityFrom(DamageSource.ON_FIRE, 1f);
						break;
					default:
						break;
					}
                }
            }
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(world, pos, state, rand);
            int heat = this.getHeatStrength(world, pos);
            if (heat == 2)
            {
                if (world.isAirBlock(pos.up()) && rand.nextInt(4) == 0)
                {
                	world.setBlockState(pos.up(), BlockLoader.YUBA_FLOW.getDefaultState());
                    if (rand.nextInt(200) == 0)
                    	world.setBlockToAir(pos);
                }
            }
    }

    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        super.randomDisplayTick(state, world, pos, rand);
        if (world.getBlockState(pos.up()).getMaterial() != Material.WATER && rand.nextInt(3) == 0)
        {
            if (this.getHeatStrength(world, pos) > 0)
            {
                float steamX = pos.getX() + 0.5F;
                float steamY = pos.getY() + 0.9F;
                float steamZ = pos.getZ() + 0.5F;
                float steamRandX = rand.nextFloat() * 0.6F - 0.3F;
                float steamRandZ = rand.nextFloat() * 0.6F - 0.3F;
                double gRand1 = rand.nextGaussian() * 0.01D;
                double gRand2 = rand.nextGaussian() * 0.01D;
                double gRand3 = rand.nextGaussian() * 0.01D;
                world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (steamX + steamRandX), steamY, (steamZ + steamRandZ), gRand1, gRand2, gRand3);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos neighborPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, neighborPos);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        return BlockFaceShape.UNDEFINED;
    }
    private int getHeatStrength(World par1World, BlockPos pos)
    {
        for (int i = 1; i < 5; i++)
        {
            Block block = par1World.getBlockState(pos.down(i)).getBlock();
            if (block instanceof BlockFire || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
            {
                return i <= 2 ? 2 : 1;
            }
        }
        return 0;
    }

}