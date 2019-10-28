package cn.mcmod.tofucraft.world;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.sky.TofuWeatherRenderer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WorldProviderTofu extends WorldProvider {

    @SideOnly(Side.CLIENT)
    private static TofuWeatherRenderer weatherRenderer;

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

    @SuppressWarnings("deprecation")
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

    @SideOnly(Side.CLIENT)
    public static TofuWeatherRenderer getTofuWeatherRenderer() {
        if (weatherRenderer == null) {
            weatherRenderer = new TofuWeatherRenderer();
        }
        return weatherRenderer;
    }

    @Nullable
    @Override
    public IRenderHandler getWeatherRenderer() {
        return getTofuWeatherRenderer();
    }
}
