package cn.mcmod.tofucraft.world;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class WorldProviderTofu extends WorldProvider {

    @Override
    public void init(){
        super.biomeProvider = new BiomeProviderTofu(this.world.getWorldInfo());
        this.hasSkyLight = true;
    }

    @Override
    public DimensionType getDimensionType() {
        return TofuMain.TOFU_DIMENSION;
    }

    /**
     * Returns a new chunk provider register generates chunks for this world
     */
    @Override
    public IChunkGenerator createChunkGenerator()
    {
       /* long newSeed = Utils.getSeedForTofuWorld(this.world);*/
        return new ChunkProviderTofu(this.world, this.world.getSeed());
    }

    @Override
    public void resetRainAndThunder() {

        super.resetRainAndThunder();

        if(this.world.getGameRules().getBoolean("doDaylightCycle"))
        {
            WorldInfo worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo) world.getWorldInfo(), "theWorldInfo", "field_76115_a");
            long i = worldInfo.getWorldTime() + 24000L;
            worldInfo.setWorldTime(i - i % 24000L);
        }
    }

    @Override
    public boolean canRespawnHere()
    {
        return true;
    }


}
