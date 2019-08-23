package cn.mcmod.tofucraft.block.mecha;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.tileentity.TileEntityReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockTFReceiver extends Block {
    /*
    * Comment:
    * This is a block only for test, it doesn't have any function usage other than debugging and testing.*/
    public BlockTFReceiver() {
        super(Material.IRON);
        this.setCreativeTab(CommonProxy.tab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        for (Map.Entry<String, TileEntity> entry: TofuNetwork.Instance.getExtractableWithinRadius(worldIn.getTileEntity(pos), 20).collect(Collectors.toSet())){
            TofuMain.logger.info(entry.toString());
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityReceiver();
    }
}
