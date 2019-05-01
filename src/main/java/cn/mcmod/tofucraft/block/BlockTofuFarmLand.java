package cn.mcmod.tofucraft.block;

import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;

public class BlockTofuFarmLand extends BlockFarmland {
    public BlockTofuFarmLand()
    {
        super();
        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.35F);
        this.setResistance(1.0F);
        this.setHarvestLevel("shovel", 0);
    }
}
