package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

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
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Chunk chunk = this.world.getChunkFromBlockCoords(blockpos);
        if (this.getSlimeSize() == 1 || this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            int lightValue = this.world.getLightFromNeighbors(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)));
            Biome biome = this.world.getBiome(new BlockPos(this.posX, 0, this.posZ));
            if (this.dimension == CommonProxy.TOFU_DIMENSION.getId() && this.rand.nextInt(30) == 0
                    && this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(48.0D, 24.0D, 48.0D)).size() == 0) {
                return this.baseGetCanSpawnHere();
            }

            if (this.dimension == 0
                    && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D
                    && lightValue <= this.rand.nextInt(8)) {
                return this.baseGetCanSpawnHere();
            }
        }

        return false;
    }

    /**
     * Must be the same as EntityLiving.getCanSpawnHere!
     */
    public boolean baseGetCanSpawnHere() {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return iblockstate.canEntitySpawn(this);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofuslime;
    }
    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SNOWBALL;
    }
}
