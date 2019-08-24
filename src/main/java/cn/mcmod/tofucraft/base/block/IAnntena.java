package cn.mcmod.tofucraft.base.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAnntena {
    public double getRadius(BlockPos pos, World world);
    public int getPower(BlockPos pos, World world);
}
