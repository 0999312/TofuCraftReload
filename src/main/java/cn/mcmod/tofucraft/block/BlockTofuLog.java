package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTofuLog extends Block {
    public BlockTofuLog() {
        super(Material.WOOD);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
    }


    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }
}
