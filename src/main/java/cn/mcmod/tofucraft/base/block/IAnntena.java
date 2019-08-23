package cn.mcmod.tofucraft.base.block;

import net.minecraft.util.math.BlockPos;

public interface IAnntena {
    public double getRadius(BlockPos pos);
    public int getPower(BlockPos pos);
}
