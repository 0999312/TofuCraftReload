package cn.mcmod.tofucraft.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockUtils {

    public static void handleEntityWeightingBlock(World world, Entity entity, Block block, IEntityWeightingBlockHandler handler)
    {
        int i = MathHelper.floor(entity.getEntityBoundingBox().minX + 0.001D);
        int j = MathHelper.floor(entity.getEntityBoundingBox().minY + 0.001D);
        int k = MathHelper.floor(entity.getEntityBoundingBox().minZ + 0.001D);
        int l = MathHelper.floor(entity.getEntityBoundingBox().maxX - 0.001D);
        int i1 = MathHelper.floor(entity.getEntityBoundingBox().maxY - 0.001D);
        int j1 = MathHelper.floor(entity.getEntityBoundingBox().maxZ - 0.001D);



        if (entity.world.chunk)

        {

            for (int k1 = i; k1 <= l; ++k1)

            {

                for (int l1 = j; l1 <= i1; ++l1)

                {

                    for (int i2 = k; i2 <= j1; ++i2)

                    {

                        int bx = k1;

                        int by = l1 - 1;

                        int bz = i2;

                        if (world.getBlock(bx, by, bz) == block)

                        {

                            handler.apply(world, entity, block, bx, by, bz);

                        }

                    }

                }

            }

        }

    }



    public interface IEntityWeightingBlockHandler
    {
        public void apply(World world, Entity entity, Block block, int x, int y, int z);
    }
}
