package cn.mcmod.tofucraft.block.mecha.antennas;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.base.block.IAnntena;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAntennaBasic extends Block implements IAnntena {
    private static float size = 0.4375F;
    private static float half = size / 2;
    public static final AxisAlignedBB ANTENNA_AABB = new AxisAlignedBB(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, 1.0F, 0.5F + half);

    /*
     * Basic antenna, the most simple one.
     * */
    public BlockAntennaBasic() {
        super(Material.IRON);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
    }

    @Override
    public double getRadius(BlockPos pos, World world) {
        return 7;
    }

    @Override
    public int getPower(BlockPos pos, World world) {
        return 7;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return ANTENNA_AABB;
    }
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
}
