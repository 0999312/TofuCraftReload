package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityTofuSlime extends EntitySlime {
    public EntityTofuSlime(World worldIn) {
        super(worldIn);
    }

    protected EntityTofuSlime createInstance() {
        return new EntityTofuSlime(this.world);
    }

    @Override
    public boolean getCanSpawnHere() {

        if (this.getSlimeSize() == 1 || this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            int lightValue = this.world.getLightFromNeighbors(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)));

            if (this.dimension == TofuMain.TOFU_DIMENSION.getId() && this.rand.nextInt(30) == 0
                    && this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(52.0D, 24.0D, 52.0D)).size() == 0) {
                return this.baseGetCanSpawnHere();
            }

            if (this.dimension == 0
                    && isSpawnChunk(this.world, this.posX, this.posZ)
                    && this.posY > 15.0D && this.posY < 40.0D
                    && lightValue <= this.rand.nextInt(8))
                return this.baseGetCanSpawnHere();
        }
        return false;
    }

    public static boolean isSpawnChunk(World world, double x, double z) {
        BlockPos blockpos = new BlockPos(MathHelper.floor(x), 0, MathHelper.floor(z));
        Chunk var1 = world.getChunkFromBlockCoords(blockpos);
        return var1.getRandomWithSeed(987234911L).nextInt(8) == 0;
    }
    
    /**
     * Must be the same as EntityLiving.getCanSpawnHere!
     */
    public boolean baseGetCanSpawnHere() {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return iblockstate.canEntitySpawn(this);
    }


    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getSlimeSize() == 1 ? TofuLootTables.tofuslime : LootTableList.EMPTY;
    }
    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SNOWBALL;
    }
}
