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

    public TofuTeleporter(WorldServer world) {
        super(world);
    }

    /**
     * Place an entity in a nearby portal, creating one if necessary.
     */
    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        if (!this.placeInExistingPortal(entityIn, rotationYaw)) {
            this.makePortal(entityIn);
            this.placeInExistingPortal(entityIn, rotationYaw);
        }
    }


    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {final long chunkId = ChunkPos.asLong(MathHelper.floor(entity.posX), MathHelper.floor(entity.posZ));

        double distance = -1.0D;
        boolean doesPortalExist = true;
        BlockPos location = BlockPos.ORIGIN;

        if (this.destinationCoordinateCache.containsKey(chunkId)) {
            final PortalPosition portalPosition = this.destinationCoordinateCache.get(chunkId);
            distance = 0.0D;
            location = portalPosition;
            portalPosition.lastUpdateTime = this.world.getTotalWorldTime();
            doesPortalExist = false;
        } else {
            final BlockPos entityPos = new BlockPos(entity);
            for (int offsetX = -128; offsetX <= 128; ++offsetX) {
                BlockPos positionCache;

                for (int offsetZ = -128; offsetZ <= 128; ++offsetZ) {

                    for (BlockPos currentPos = entityPos.add(offsetX, this.world.getActualHeight() - 1 - entityPos.getY(), offsetZ); currentPos.getY() >= 0; currentPos = positionCache) {
                        positionCache = currentPos.down();
                        if (this.world.getBlockState(currentPos).getBlock() == BlockLoader.tofu_PORTAL) {
                            while (this.world.getBlockState(positionCache = currentPos.down()).getBlock() == BlockLoader.tofu_PORTAL) {
                                currentPos = positionCache;
                            }
                            final double distanceToEntity = currentPos.distanceSq(entityPos);

                            if (distance < 0.0D || distanceToEntity < distance) {
                                distance = distanceToEntity;
                                location = currentPos;
                            }
                        }
                    }
                }
            }
        }

        if (distance >= 0.0D) {
            if (doesPortalExist) {
                this.destinationCoordinateCache.put(chunkId, new PortalPosition(location, this.world.getTotalWorldTime()));
            }

            double tpX = location.getX() + 0.5D;
            double tpY = location.getY() + 0.5D;
            double tpZ = location.getZ() + 0.5D;
            EnumFacing direction = null;

            if (this.world.getBlockState(location.west()).getBlock() == Blocks.AIR) {
                direction = EnumFacing.NORTH;
            }

            if (this.world.getBlockState(location.east()).getBlock() == Blocks.AIR) {
                direction = EnumFacing.SOUTH;
            }

            if (this.world.getBlockState(location.north()).getBlock() == Blocks.AIR) {
                direction = EnumFacing.EAST;
            }

            if (this.world.getBlockState(location.south()).getBlock() == Blocks.AIR) {
                direction = EnumFacing.WEST;
            }

            final EnumFacing enumfacing1 = EnumFacing.getHorizontal(MathHelper.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3);

            if (direction != null) {
                EnumFacing enumfacing2 = direction.rotateYCCW();
                final BlockPos blockpos2 = location.offset(direction);
                boolean flag2 = this.isAirBlock(blockpos2);
                boolean flag3 = this.isAirBlock(blockpos2.offset(enumfacing2));

                if (flag3 && flag2) {
                    location = location.offset(enumfacing2);
                    direction = direction.getOpposite();
                    enumfacing2 = enumfacing2.getOpposite();
                    final BlockPos blockpos3 = location.offset(direction);
                    flag2 = this.isAirBlock(blockpos3);
                    flag3 = this.isAirBlock(blockpos3.offset(enumfacing2));
                }

                float f6 = 0.5F;
                float f1 = 0.5F;

                if (!flag3 && flag2) {
                    f6 = 1.0F;
                } else if (flag3 && !flag2) {
                    f6 = 0.0F;
                } else if (flag3) {
                    f1 = 0.0F;
                }

                tpX = location.getX() + 0.5D;
                tpY = location.getY() + 0.5D;
                tpZ = location.getZ() + 0.5D;
                tpX += enumfacing2.getFrontOffsetX() * f6 + direction.getFrontOffsetX() * f1;
                tpZ += enumfacing2.getFrontOffsetY() * f6 + direction.getFrontOffsetY() * f1;
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;

                if (direction == enumfacing1) {
                    f2 = 1.0F;
                    f3 = 1.0F;
                } else if (direction == enumfacing1.getOpposite()) {
                    f2 = -1.0F;
                    f3 = -1.0F;
                } else if (direction == enumfacing1.rotateY()) {
                    f4 = 1.0F;
                    f5 = -1.0F;
                } else {
                    f4 = -1.0F;
                    f5 = 1.0F;
                }

                final double d2 = entity.motionX;
                final double d3 = entity.motionZ;
                entity.motionX = d2 * f2 + d3 * f5;
                entity.motionZ = d2 * f4 + d3 * f3;
                entity.rotationYaw = rotationYaw - enumfacing1.getHorizontalIndex() * 90 + direction.getHorizontalIndex() * 90;
            } else {
                entity.motionX = entity.motionY = entity.motionZ = 0.0D;
            }

            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(tpX, tpY, tpZ, entity.rotationYaw, entity.rotationPitch);
            } else {
                entity.setLocationAndAngles(tpX, tpY, tpZ, entity.rotationYaw, entity.rotationPitch);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isAirBlock(BlockPos position) {
        return this.world.isAirBlock(position) ;
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

        for (int j2 = j - 16; j2 <= j + 16; ++j2) {
            double d1 = (double) j2 + 0.5D - entityIn.posX;

            for (int l2 = l - 16; l2 <= l + 16; ++l2) {
                double d2 = (double) l2 + 0.5D - entityIn.posZ;
                label146:

                for (int j3 = this.world.getActualHeight() - 1; j3 >= 0; --j3) {
                    if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2))) {
                        while (j3 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2))) {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3) {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if (k3 % 4 >= 2) {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4) {
                                for (int k4 = 0; k4 < 4; ++k4) {
                                    for (int l4 = -1; l4 < 4; ++l4) {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockpos$mutableblockpos.setPos(i5, j5, k5);

                                        if (l4 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label146;
                                        }
                                    }
                                }
                            }

                            double d5 = (double) j3 + 0.5D - entityIn.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if (d0 < 0.0D || d7 < d0) {
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

        if (d0 < 0.0D) {
            for (int l5 = j - 16; l5 <= j + 16; ++l5) {
                double d3 = (double) l5 + 0.5D - entityIn.posX;

                for (int j6 = l - 16; j6 <= l + 16; ++j6) {
                    double d4 = (double) j6 + 0.5D - entityIn.posZ;
                    label567:

                    for (int i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7) {
                        if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6))) {
                            while (i7 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6))) {
                                --i7;
                            }

                            for (int k7 = i2; k7 < i2 + 2; ++k7) {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for (int j10 = 0; j10 < 4; ++j10) {
                                    for (int j11 = -1; j11 < 4; ++j11) {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);

                                        if (j11 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label567;
                                        }
                                    }
                                }

                                double d6 = (double) i7 + 0.5D - entityIn.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if (d0 < 0.0D || d8 < d0) {
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

        if (l1 % 4 >= 2) {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D) {
            j1 = MathHelper.clamp(j1, 70, this.world.getActualHeight() - 10);
            k2 = j1;

            // Keep a room
            for (int j7 = -1; j7 <= 1; ++j7) {
                for (int l7 = 1; l7 < 3; ++l7) {
                    for (int k8 = -1; k8 < 3; ++k8) {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.world.setBlockState(new BlockPos(k9, k10, k11), flag ? FRAME_BLOCK : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        // Place blocks

        IBlockState iblockstate = PORTAL_BLOCK.getDefaultState().withProperty(BlockTofuPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for (int i8 = 0; i8 < 4; ++i8) {
            for (int l8 = 0; l8 < 4; ++l8) {
                for (int l9 = -1; l9 < 4; ++l9) {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9;
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    this.world.setBlockState(new BlockPos(l10, l11, k12), flag1 ? BlockLoader.GRILD.getDefaultState() : iblockstate,2);
                }
            }

            for (int i9 = 0; i9 < 4; ++i9) {
                for (int i10 = -1; i10 < 4; ++i10) {
                    int i11 = i6 + (i9 - 1) * l6;
                    int i12 = k2 + i10;
                    int l12 = k6 + (i9 - 1) * i3;
                    BlockPos blockpos = new BlockPos(i11, i12, l12);
                    this.world.notifyNeighborsOfStateChange(blockpos, this.world.getBlockState(blockpos).getBlock(), false);
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
    public void removeStalePortalLocations(long worldTime) {
        if (worldTime % 100L == 0L) {
            long i = worldTime - 300L;
            ObjectIterator<PortalPosition> objectiterator = this.destinationCoordinateCache.values().iterator();

            while (objectiterator.hasNext()) {
                PortalPosition teleporter$portalposition = (PortalPosition) objectiterator.next();

                if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i) {
                    objectiterator.remove();
                }
            }
        }
    }

}
