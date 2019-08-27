package cn.mcmod.tofucraft.block.mecha.antennas;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.base.block.IAnntena;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAntennaBasic extends Block implements IAnntena {
    /*
     * Basic antenna, the most simple one.
     * */
    public BlockAntennaBasic() {
        super(Material.IRON);
        this.setCreativeTab(CommonProxy.tab);
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
}
