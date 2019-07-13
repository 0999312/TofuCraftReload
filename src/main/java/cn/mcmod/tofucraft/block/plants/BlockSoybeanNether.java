package cn.mcmod.tofucraft.block.plants;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSoybeanNether extends BlockCrops {

    private static final AxisAlignedBB[] SOYBEAN_AABB = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D)
    };

    public BlockSoybeanNether() {
        super();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SOYBEAN_AABB[((Integer) state.getValue(this.getAgeProperty())).intValue()];
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (this.isMaxAge(state)) {
            int ret = 1;
            for (int n = 0; n < 5 + fortune; n++) {
                if (random.nextInt(15) <= this.getAge(state)) {
                    ret++;
                }
            }
            return ret;
        } else {
            return 1;
        }
    }

    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.SOUL_SAND;
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    @Override
    protected Item getSeed() {
        return ItemLoader.soybeansHell;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    @Override
    protected Item getCrop() {
        return ItemLoader.soybeansHell;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }
}
