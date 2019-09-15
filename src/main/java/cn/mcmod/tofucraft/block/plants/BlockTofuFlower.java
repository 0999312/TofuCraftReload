package cn.mcmod.tofucraft.block.plants;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class BlockTofuFlower extends BlockBush {
    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.4D, 0.0D, 0.4D, 0.6D, 0.6D, 0.6D);

    public BlockTofuFlower() {
        super();
        setCreativeTab(CommonProxy.tab);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {

        Block block = state.getBlock();
        return block == BlockLoader.MOMENTOFU || block == BlockLoader.KINUTOFU || block == BlockLoader.tofuTerrain || block == BlockLoader.zundatofuTerrain;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();

        IBlockState soil = world.getBlockState(down);

        return soil.getBlock().canSustainPlant(soil, world, down, EnumFacing.UP, this);

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BUSH_AABB;
    }
}
