package cn.mcmod.tofucraft.block.plants;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockLeekCrop extends BlockCrops {

    private static final AxisAlignedBB[] SOYBEAN_AABB = new AxisAlignedBB[]{
            new AxisAlignedBB(0.10000001192092896D, 0.0D, 0.10000001192092896D, 0.899999988079071D, 0.4000000238418579D, 0.899999988079071D),
            new AxisAlignedBB(0.10000001192092896D, 0.0D, 0.10000001192092896D, 0.899999988079071D, 0.6000000238418579D, 0.899999988079071D),
            new AxisAlignedBB(0.10000001192092896D, 0.0D, 0.10000001192092896D, 0.899999988079071D, 0.9000000238418579D, 0.899999988079071D)
    };
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

    public BlockLeekCrop() {
        super();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SOYBEAN_AABB[((Integer) state.getValue(this.getAgeProperty())).intValue()];
    }

    protected PropertyInteger getAgeProperty() {
        return AGE;
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

    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int age = getAge(state);
        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR) {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }

        if (age >= getMaxAge()) {
            int k = 3 + fortune;
            for (int i = 0; i < k; ++i) {
                if (rand.nextInt(2 * getMaxAge()) <= age) {
                    drops.add(new ItemStack(this.getSeed(), 1, this.damageDropped(state)));
                }
            }
        }
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {

        Block block = state.getBlock();
        return block == BlockLoader.TOFUFARMLAND;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();

        IBlockState soil = world.getBlockState(down);

        return soil.getBlock().canSustainPlant(soil, world, down, EnumFacing.UP, this);

    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    @Override
    protected Item getSeed() {
        return ItemLoader.material;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    @Override
    protected Item getCrop() {
        return ItemLoader.material;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }

    public int damageDropped(IBlockState state) {
        return 16;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{AGE});
    }
}
