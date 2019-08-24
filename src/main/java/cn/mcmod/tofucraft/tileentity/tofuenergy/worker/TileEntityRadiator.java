package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

import cn.mcmod.tofucraft.base.tileentity.TileEntityWorkerBase;
import cn.mcmod.tofucraft.block.BlockTofu;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileEntityRadiator extends TileEntityWorkerBase implements ITickable {
    protected static final int WORKING_RADIUS = 4;
    private static final Random rnd = new Random();

    public TileEntityRadiator(int energyMax) {
        super(energyMax);
    }

    @Override
    public void update() {
        for (int i = 0; i <= 20; i++) {
            BlockPos tickingPos = pos.add(
                    rnd.nextInt(2 * (WORKING_RADIUS + 1)),
                    rnd.nextInt(2 * (WORKING_RADIUS + 1)),
                    rnd.nextInt(2 * (WORKING_RADIUS + 1)));
            tickingPos.add(
                    -WORKING_RADIUS,
                    -WORKING_RADIUS,
                    -WORKING_RADIUS);
            if (world.getBlockState(tickingPos).getBlock() instanceof BlockTofu) {
                for (int j = 0; j <= 20; j++)
                world.getBlockState(tickingPos).getBlock().
                        updateTick(world, tickingPos, world.getBlockState(tickingPos), rnd);
            }
        }
        drain(3, false);
    }
}
