package cn.mcmod.tofucraft.world.gen.layer;

import cn.mcmod.tofucraft.world.biome.BiomeTofu;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public abstract class GenLayerTofu extends GenLayer {

    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static GenLayer[] initializeAllBiomeGeneratorsTofu(long seed, WorldType worldType)
    {
        byte biomeSize = getModdedBiomeSize(worldType, (byte) (worldType == WorldType.LARGE_BIOMES ? 7 : 5));


        GenLayerIsland genlayerisland = new GenLayerIsland(1L);
        GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerisland);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(50L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(70L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(3L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayeraddisland);
        GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland, GenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        genlayerzoom = new GenLayerZoom(2002L, genlayeredge);
        genlayerzoom = new GenLayerZoom(2003L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);

        GenLayerTofu genlayer3 = GenLayerZoom.magnify(1000L, genlayeraddisland, 0);
        GenLayerTofu genlayer = GenLayerZoom.magnify(1000L, genlayer3, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
        Object object = GenLayerTofu.getBiomeLayer(seed, genlayer3, worldType);

        GenLayerTofu genlayer1 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayerHills genlayerhills = new GenLayerHills(1000L, (GenLayerTofu)object, genlayer1);
        genlayer = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer = GenLayerZoom.magnify(1000L, genlayer, biomeSize);
        GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        object = GenLayerZoom.magnify(1000L, genlayerhills, 2);

        for (int j = 0; j < biomeSize; ++j)
        {
            object = new GenLayerZoom((long)(1000 + j), (GenLayerTofu)object);

            if (j == 0)
            {
                object = new GenLayerAddIsland(3L, (GenLayerTofu)object);
            }
            if (j == 1)
            {
                object = new GenLayerShore(1000L, (GenLayerTofu)object);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayerTofu)object);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);


        GenLayerTofu layerVoronoi = new GenLayerTofuVoronoiZoom(10L, genlayerrivermix);

        genlayerrivermix.initWorldGenSeed(seed);

        layerVoronoi.initWorldGenSeed(seed);

        return new GenLayer[] {genlayerrivermix, layerVoronoi};
    }

    public static GenLayerTofu getBiomeLayer(long worldSeed, GenLayerTofu parentLayer, WorldType worldType)
    {
        GenLayerTofu ret = new GenLayerBiome(200L, parentLayer, worldType);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }

    public GenLayerTofu(long par1)
    {
        super(par1);
    }

    protected static boolean compareBiomesById(final int p_151616_0_, final int p_151616_1_)
    {
        if (p_151616_0_ == p_151616_1_)
        {
            return true;
        }
        else
        {
            try
            {
                return BiomeTofu.getBiome(p_151616_0_) != null && BiomeTofu.getBiome(p_151616_1_) != null ? isEqualTo(BiomeTofu.getBiome(p_151616_0_), BiomeTofu.getBiome(p_151616_1_)) : false;
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Comparing biomes");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Biomes being compared");
                crashreportcategory.addCrashSection("Biome A ID", Integer.valueOf(p_151616_0_));
                crashreportcategory.addCrashSection("Biome B ID", Integer.valueOf(p_151616_1_));
                throw new ReportedException(crashreport);
            }
        }
    }

    public static byte getModdedBiomeSize(WorldType worldType, byte original) {

        WorldTypeEvent.BiomeSize event = new WorldTypeEvent.BiomeSize(worldType, original);

        MinecraftForge.TERRAIN_GEN_BUS.post(event);

        return (byte) event.getNewSize();

    }

    /**
     * returns true if the biome specified is equal to this biome
     */
    public static boolean isEqualTo(Biome p_150569_1_, Biome par2)
    {
        return p_150569_1_ == par2 ? true : (p_150569_1_ == null ? false : par2.getBiomeClass() == p_150569_1_.getBiomeClass());
    }
}
