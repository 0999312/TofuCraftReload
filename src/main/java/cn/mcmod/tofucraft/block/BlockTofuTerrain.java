package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTofuTerrain extends BlockTofuBase {

    public BlockTofuTerrain(SoundType sound) {
        super();
        this.setSoundType(sound);
        this.setHardness(0.35F);
        this.setResistance(1.0F);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote && worldIn.getBlockState(pos) == BlockLoader.zundatofuTerrain.getDefaultState()) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
                worldIn.setBlockState(pos, BlockLoader.tofuTerrain.getDefaultState());
            } else {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if ((iblockstate1.getBlock() == BlockLoader.tofuTerrain || iblockstate1.getBlock() == BlockLoader.KINUTOFU) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                            worldIn.setBlockState(blockpos, BlockLoader.zundatofuTerrain.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemLoader.tofu_food;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 1;
    }

    @Override
    public ItemStack createScoopedBlockStack() {
        if (this == BlockLoader.zundatofuTerrain) {
            return new ItemStack(BlockLoader.zundatofuTerrain, 1);
        } else {
            return new ItemStack(BlockLoader.tofuTerrain, 1);
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

        return new ItemStack(this);
    }

}
