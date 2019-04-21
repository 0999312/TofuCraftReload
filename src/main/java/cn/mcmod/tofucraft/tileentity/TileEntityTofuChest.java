package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTofuChest extends TileEntityChest {
    public TileEntityTofuChest() {
        super(BlockTofuChest.TOFU_CHEST);
    }

    @Override
    public String getName() {
        return "container.tofucraft.tofuchest";
    }

    public String getGuiID() {
        return "tofucraft:tofuchest";
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {

        //Use vanilla behaviour to prevent inventory from resetting when creating double chest
        return oldState.getBlock() != newSate.getBlock();

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(2, 2, 2));
    }

}