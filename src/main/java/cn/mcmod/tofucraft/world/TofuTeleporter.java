package cn.mcmod.tofucraft.world;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuPortal;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TofuTeleporter extends Teleporter {

    public static final IBlockState FRAME_BLOCK = BlockLoader.GRILD.getDefaultState();
    public static final BlockTofuPortal PORTAL_BLOCK = BlockLoader.tofu_PORTAL;
    public static final int[] frameMetaTable = new int[]{4, 2, 5, 3};

    private final WorldServer worldServerInstance;

    /** A private Random() function in Teleporter */
    private final Random random;

    /** Stores successful portal placement locations for rapid lookup. */
    private final Long2ObjectMap<PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap<PortalPosition>(4096);

    public TofuTeleporter(WorldServer world)
    {
        super(world);
        this.worldServerInstance = world;
        this.random = new Random();
    }

    /**
     * Place an entity in a nearby portal, creating one if necessary.
     */
    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        if (!this.placeInExistingPortal(entityIn, rotationYaw))
        {
            this.makePortal(entityIn);
            this.placeInExistingPortal(entityIn, rotationYaw);
        }
    }

    public Vec3d locateSpawnPoint(Entity entity)
    {
        World world = this.worldServerInstance;
        byte byte0 = 16;
        double d = -1D;
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = random.nextInt(4);
        for(int i2 = i - byte0; i2 <= i + byte0; i2++)
        {
            double d1 = (i2 + 0.5D) - entity.posX;
            for(int j3 = k - byte0; j3 <= k + byte0; j3++)
            {
                double d3 = (j3 + 0.5D) - entity.posZ;
                for(int k4 = 127; k4 >= 0; k4--)
                {
                    if(!world.isAirBlock(new BlockPos(i2, k4, j3)))
                    {
                        continue;
                    }
                    for(; k4 > 0 && world.isAirBlock(new BlockPos(i2, k4 - 1, j3)); k4--) { }
                    label0:
                    for(int k5 = l1; k5 < l1 + 4; k5++)
                    {
                        int l6 = k5 % 2;
                        int i8 = 1 - l6;
                        if(k5 % 4 >= 2)
                        {
                            l6 = -l6;
                            i8 = -i8;
                        }
                        for(int j9 = 0; j9 < 3; j9++)
                        {
                            for(int k10 = 0; k10 < 4; k10++)
                            {
                                for(int l11 = -1; l11 < 4; l11++)
                                {
                                    int j12 = i2 + (k10 - 1) * l6 + j9 * i8;
                                    int l12 = k4 + l11;
                                    int j13 = (j3 + (k10 - 1) * i8) - j9 * l6;
                                    BlockPos pos = new BlockPos(j12, l12, j13);
                                    if(l11 < 0 && !world.getBlockState(pos).getMaterial().isSolid() || l11 >= 0 && !world.isAirBlock(pos))
                                    {
                                        break label0;
                                    }
                                }

                            }

                        }

                        double d5 = (k4 + 0.5D) - entity.posY;
                        double d7 = d1 * d1 + d5 * d5 + d3 * d3;
                        if(d < 0.0D || d7 < d)
                        {
                            d = d7;
                            l = i2;
                            i1 = k4;
                            j1 = j3;
                            k1 = k5 % 4;
                        }
                    }

                }

            }

        }

        if(d < 0.0D)
        {
            for(int j2 = i - byte0; j2 <= i + byte0; j2++)
            {
                double d2 = (j2 + 0.5D) - entity.posX;
                for(int k3 = k - byte0; k3 <= k + byte0; k3++)
                {
                    double d4 = (k3 + 0.5D) - entity.posZ;
                    for(int l4 = 127; l4 >= 0; l4--)
                    {
                        if(!world.isAirBlock(new BlockPos(j2, l4, k3)))
                        {
                            continue;
                        }
                        for(; l4 > 0 && world.isAirBlock(new BlockPos(j2, l4 - 1, k3)); l4--) { }
                        label1:
                        for(int l5 = l1; l5 < l1 + 2; l5++)
                        {
                            int i7 = l5 % 2;
                            int j8 = 1 - i7;
                            for(int k9 = 0; k9 < 4; k9++)
                            {
                                for(int l10 = -1; l10 < 4; l10++)
                                {
                                    int i12 = j2 + (k9 - 1) * i7;
                                    int k12 = l4 + l10;
                                    int i13 = k3 + (k9 - 1) * j8;
                                    BlockPos pos = new BlockPos(i12, k12, i13);
                                    if(l10 < 0 && !world.getBlockState(pos).getMaterial().isSolid() || l10 >= 0 && !world.isAirBlock(pos))
                                    {
                                        break label1;
                                    }
                                }

                            }

                            double d6 = (l4 + 0.5D) - entity.posY;
                            double d8 = d2 * d2 + d6 * d6 + d4 * d4;
                            if(d < 0.0D || d8 < d)
                            {
                                d = d8;
                                l = j2;
                                i1 = l4;
                                j1 = k3;
                                k1 = l5 % 2;
                            }
                        }

                    }

                }

            }

        }
        int k2 = k1;
        int l2 = l;
        int i3 = i1;
        int l3 = j1;
        int i4 = k2 % 2;
        int j4 = 1 - i4;
        if(k2 % 4 >= 2)
        {
            i4 = -i4;
            j4 = -j4;
        }
        if(d < 0.0D)
        {
            if(i1 < 70)
            {
                i1 = 70;
            }
            if(i1 > 118)
            {
                i1 = 118;
            }
            i3 = i1;
            for(int i5 = -1; i5 <= 1; i5++)
            {
                for(int i6 = 0; i6 < 3; i6++)
                {
                    for(int j7 = -1; j7 < 3; j7++)
                    {
                        int k8 = l2 + (i6 - 1) * i4 + i5 * j4;
                        int l9 = i3 + j7;
                        int i11 = (l3 + (i6 - 1) * j4) - i5 * i4;
                        boolean flag = j7 < 0;
                        world.setBlockState(new BlockPos(k8, l9, i11), flag ? FRAME_BLOCK : Blocks.AIR.getDefaultState(), 2);
                    }

                }
            }

        }
        return new Vec3d(l2 + i4, i3, l3 + j4);
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        //int i = 128;
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posZ);
        boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);
        if (this.destinationCoordinateCache.containsKey(l))
        {
            Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.get(l);
            d0 = 0.0D;
            blockpos = teleporter$portalposition;
            teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            flag = false;
        }
        else
        {
            BlockPos blockpos3 = new BlockPos(entityIn);

            for (int i1 = -128; i1 <= 128; ++i1)
            {
                BlockPos blockpos2;

                for (int j1 = -128; j1 <= 128; ++j1)
                {
                    for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
                    {
                        blockpos2 = blockpos1.down();

                        if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == TofuTeleporter.PORTAL_BLOCK)
                        {

                            for (blockpos2 = blockpos1.down(); this.worldServerInstance.getBlockState(blockpos2).getBlock() == TofuTeleporter.PORTAL_BLOCK; blockpos2 = blockpos2.down())
                            {
                                blockpos1 = blockpos2;
                            }

                            double d1 = blockpos1.distanceSq(blockpos3);

                            if (d0 < 0.0D || d1 < d0)
                            {
                                d0 = d1;
                                blockpos = blockpos1;
                            }
                        }
                    }
                }
            }
        }
        if (d0 >= 0.0D)
        {
            if (flag)
            {
                this.destinationCoordinateCache.put(l, new PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
            }

            double d5 = (double)blockpos.getX() + 0.5D;
            double d7 = (double)blockpos.getZ() + 0.5D;
            BlockPattern.PatternHelper blockpattern$patternhelper = PORTAL_BLOCK.createPatternHelper(this.worldServerInstance, blockpos);
            boolean flag1 = blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double d2 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
            double d6 = (double)(blockpattern$patternhelper.getFrontTopLeft().getY() + 1) - entityIn.getLastPortalVec().y * (double)blockpattern$patternhelper.getHeight();

            if (flag1)
            {
                ++d2;
            }

            if (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X)
            {
                d7 = d2 + (1.0D - entityIn.getLastPortalVec().x) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
            }
            else
            {
                d5 = d2 + (1.0D - entityIn.getLastPortalVec().x) * (double)blockpattern$patternhelper.getWidth() * (double)blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
            }

            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection())
            {
                f = 1.0F;
                f1 = 1.0F;
            }
            else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().getOpposite())
            {
                f = -1.0F;
                f1 = -1.0F;
            }
            else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().rotateY())
            {
                f2 = 1.0F;
                f3 = -1.0F;
            }
            else
            {
                f2 = -1.0F;
                f3 = 1.0F;
            }

            double d3 = entityIn.motionX;
            double d4 = entityIn.motionZ;
            entityIn.motionX = d3 * (double)f + d4 * (double)f3;
            entityIn.motionZ = d3 * (double)f2 + d4 * (double)f1;
            entityIn.rotationYaw = rotationYaw - (float)(entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float)(blockpattern$patternhelper.getForwards().getHorizontalIndex() * 90);

            if (entityIn instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)entityIn).connection.setPlayerLocation(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
            }
            else
            {
                entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
            }

            return true;
        }
        else
        {
            return false;
        }

    }

    @Override
    public boolean makePortal(Entity entityIn) {
        //int i = 16;
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posY);
        int l = MathHelper.floor(entityIn.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j2 = j - 16; j2 <= j + 16; ++j2)
        {
            double d1 = (double)j2 + 0.5D - entityIn.posX;

            for (int l2 = l - 16; l2 <= l + 16; ++l2)
            {
                double d2 = (double)l2 + 0.5D - entityIn.posZ;
                label146:

                for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; --j3)
                {
                    if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
                    {
                        while (j3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
                        {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if (k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4)
                            {
                                for (int k4 = 0; k4 < 4; ++k4)
                                {
                                    for (int l4 = -1; l4 < 4; ++l4)
                                    {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockpos$mutableblockpos.setPos(i5, j5, k5);

                                        if (l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label146;
                                        }
                                    }
                                }
                            }

                            double d5 = (double)j3 + 0.5D - entityIn.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if (d0 < 0.0D || d7 < d0)
                            {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (int l5 = j - 16; l5 <= j + 16; ++l5)
            {
                double d3 = (double)l5 + 0.5D - entityIn.posX;

                for (int j6 = l - 16; j6 <= l + 16; ++j6)
                {
                    double d4 = (double)j6 + 0.5D - entityIn.posZ;
                    label567:

                    for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; --i7)
                    {
                        if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6)))
                        {
                            while (i7 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
                            {
                                --i7;
                            }

                            for (int k7 = i2; k7 < i2 + 2; ++k7)
                            {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for (int j10 = 0; j10 < 4; ++j10)
                                {
                                    for (int j11 = -1; j11 < 4; ++j11)
                                    {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);

                                        if (j11 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                        {
                                            continue label567;
                                        }
                                    }
                                }

                                double d6 = (double)i7 + 0.5D - entityIn.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if (d0 < 0.0D || d8 < d0)
                                {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if (l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D)
        {
            j1 = MathHelper.clamp(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            // Keep a room
            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int l7 = 1; l7 < 3; ++l7)
                {
                    for (int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? FRAME_BLOCK : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        // Place blocks
        {
            EnumFacing.Axis axis = l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X;
            IBlockState iblockstate = PORTAL_BLOCK.getDefaultState().withProperty(BlockTofuPortal.AXIS, axis);

            for (int i8 = 0; i8 < 4; ++i8)
            {
                for (int l8 = 0; l8 < 4; ++l8)
                {
                    for (int l9 = -1; l9 < 4; ++l9)
                    {
                        int l10 = i6 + (l8 - 1) * l6;
                        int l11 = k2 + l9;
                        int k12 = k6 + (l8 - 1) * i3;
                        boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                        int frameMeta;// = l9 == -1 || !flag1 ? 0 : frameMetaTable[l1];
                        if(!flag1) {
                            frameMeta = 0;
                        } else {
                            frameMeta = l9 == -1 ? 1 : frameMetaTable[l1];
                        }
                        this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12),FRAME_BLOCK);
                    }
                }

                for (int i9 = 0; i9 < 4; ++i9)
                {
                    for (int i10 = -1; i10 < 4; ++i10)
                    {
                        int i11 = i6 + (i9 - 1) * l6;
                        int i12 = k2 + i10;
                        int l12 = k6 + (i9 - 1) * i3;
                        BlockPos blockpos = new BlockPos(i11, i12, l12);
                        this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock(),false);
                    }
                }
            }
        }

        return true;

    }

    /**
     * called periodically to remove out-of-date portal locations from the cache list. Argument par1 is a
     * WorldServer.getTotalWorldTime() value.
     */
    @Override
    public void removeStalePortalLocations(long worldTime)
    {
        if (worldTime % 100L == 0L)
        {
            long i = worldTime - 300L;
            ObjectIterator<PortalPosition> objectiterator = this.destinationCoordinateCache.values().iterator();

            while (objectiterator.hasNext())
            {
                PortalPosition teleporter$portalposition = (PortalPosition)objectiterator.next();

                if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i)
                {
                    objectiterator.remove();
                }
            }
        }
    }

}
