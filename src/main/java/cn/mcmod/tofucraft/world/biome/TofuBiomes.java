package cn.mcmod.tofucraft.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistry;


public class TofuBiomes {

    public static BiomeTofu TOFU_PLAINS =new BiomeTofuPlains(new Biome.BiomeProperties("TofuPlains")
            .setBaseHeight(BiomeTofu.height_Tofu_Base)
            .setHeightVariation(BiomeTofu.height_Tofu_Variation)
            .setWaterColor(9286496)
            .setTemperature(0.422f)
            .setRainfall(0.917f));
    public static BiomeTofu ZUNDATOFU_PLAINS =new BiomeZundaTofuPlains(new Biome.BiomeProperties("ZundaTofuPlains")
            .setBaseHeight(BiomeTofu.height_Tofu_Base)
            .setHeightVariation(BiomeTofu.height_Tofu_Variation)
            .setWaterColor(9286496)
            .setTemperature(0.422f)
            .setRainfall(0.917f));
    public static BiomeTofu TOFU_FOREST = new BiomeTofuForest(new Biome.BiomeProperties("TofuForest")
                .setBaseHeight(BiomeTofu.height_Tofu_Base)
                .setHeightVariation(BiomeTofu.height_Tofu_Variation)
                .setWaterColor(9286496)
                .setTemperature(0.475F)
                .setRainfall(0.969F));
    public static BiomeTofu TOFU_BUILDINGS =new BiomeTofuBuildings(new Biome.BiomeProperties("TofuBuildings")
            .setBaseHeight(BiomeTofu.height_Tofu_Base)
            .setHeightVariation(BiomeTofu.height_Tofu_Variation)
            .setWaterColor(9286496)
            .setTemperature(0.422F)
            .setRainfall(0.917F));
    public static BiomeTofu TOFU_PLAIN_HILLS =new BiomeTofuPlains(new Biome.BiomeProperties("TofuPlainHills")
            .setBaseHeight(0.3F)
            .setHeightVariation(0.7F)
            .setWaterColor(9286496)
            .setTemperature(0.422F)
            .setRainfall(0.917F));
    public static BiomeTofu TOFU_FOREST_HILLS=new BiomeTofuForest(new Biome.BiomeProperties("TofuForestHills")
            .setBaseHeight(0.3F)
            .setHeightVariation(0.7F)
            .setWaterColor(9286496)
            .setTemperature(0.475F)
            .setRainfall(0.969F));
    public static BiomeTofu TOFU_LEEK_PLAINS=new BiomeLeekPlains(new Biome.BiomeProperties("LeekPlains")
            .setBaseHeight(BiomeTofu.height_Tofu_Base)
            .setHeightVariation(BiomeTofu.height_Tofu_Variation)
            .setWaterColor(9286496)
            .setTemperature(0.510F)
            .setRainfall(0.934F));
    public static BiomeTofu TOFU_RIVER = new BiomeTofuRiver(new Biome.BiomeProperties("TofuRiver")
            .setBaseHeight(BiomeTofu.height_ShallowWaters_Base)
            .setHeightVariation(BiomeTofu.height_ShallowWaters_Variation)
            .setWaterColor(9286496)
            .setTemperature(0.510F)
            .setRainfall(0.934F));

    public static BiomeTofu TOFU_EXTREME_HILLS=new BiomeTofuHills(new Biome.BiomeProperties("TofuExtremeHills")
            .setBaseHeight(1.2F)
            .setHeightVariation(0.3F)
            .setWaterColor(9286496)
            .setTemperature(0.317F)
            .setRainfall(0.759F));
    public static BiomeTofu TOFU_HILLS_EDGE=new BiomeTofuHills(new Biome.BiomeProperties("TofuExtremeHillsEdge")
            .setBaseHeight(0.2F)
            .setHeightVariation(0.8F)
            .setWaterColor(9286496)
            .setTemperature(0.317F)
            .setRainfall(0.759F));

    public static BiomeTofu[] decorationBiomes;

    public static void register(IForgeRegistry<Biome> registry) {

        registry.register(ZUNDATOFU_PLAINS.setRegistryName("zunda_tofuplain"));
        registry.register(TOFU_PLAINS.setRegistryName("tofuplain"));
        registry.register(TOFU_BUILDINGS.setRegistryName("tofubuilding"));
        registry.register(TOFU_FOREST.setRegistryName("tofuforest"));
        registry.register(TOFU_FOREST_HILLS.setRegistryName("tofuforest_hills"));
        registry.register(TOFU_LEEK_PLAINS.setRegistryName("tofuleek_plain"));
        registry.register(TOFU_PLAIN_HILLS.setRegistryName("tofuplain_hills"));
        registry.register(TOFU_RIVER.setRegistryName("tofuriver"));

        decorationBiomes = new BiomeTofu[]{
                TOFU_PLAINS, TOFU_LEEK_PLAINS, TOFU_PLAINS, TOFU_FOREST, TOFU_BUILDINGS, TOFU_EXTREME_HILLS,ZUNDATOFU_PLAINS};
    }


}
