package cn.mcmod.tofucraft.block.mecha.antennas;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.base.block.IAnntena;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
}
