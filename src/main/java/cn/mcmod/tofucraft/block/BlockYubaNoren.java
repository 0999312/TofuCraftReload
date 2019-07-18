package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.material.TofuMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockYubaNoren extends Block {

    public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

    protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.05D, 0.1D, 0.375D, 0.95D, 1.0D, 0.625D);

    protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.1D, 0.05D, 0.625D, 1.0D, 0.95D);

    protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.1D, 0.375D, 0.625D, 1.0D, 0.625D);

    public BlockYubaNoren() {
        super(TofuMaterial.tofu);
        this.setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));

        this.setSoundType(SoundType.CLOTH);
        this.setTickRandomly(true);

        this.setResistance(0.5f);
        this.setHardness(0.5f);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos) && worldIn.getBlockState(pos).getBlock() != this;
    }

    protected boolean canSustainNoren(IBlockState state) {
        return state.isFullCube();
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        return this.canSustainNoren(worldIn.getBlockState(pos.up()));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(world, pos, state);
    }

    @Override
    @Deprecated
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        super.neighborChanged(state, world, pos, block, fromPos);
        if (!this.canBlockStay(world, pos)) {
            this.checkAndDropBlock(world, pos, state);
        }
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getMetaForAxis(state.getValue(AXIS));
    }

    public static int getMetaForAxis(EnumFacing.Axis axis) {
        if (axis == EnumFacing.Axis.X) {
            return 1;
        }

        return axis == EnumFacing.Axis.Z ? 2 : 0;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * <p>
     * blockstate.
     */

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {

        switch (rot) {
            case COUNTERCLOCKWISE_90:

            case CLOCKWISE_90:

                switch (state.getValue(AXIS)) {
                    case X:
                        return state.withProperty(AXIS, EnumFacing.Axis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumFacing.Axis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        switch (state.getValue(AXIS)) {
            case X:
                return X_AABB;
            case Z:
                return Z_AABB;
            default:
                return Y_AABB;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, AXIS);

    }
}
