package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.client.render.*;
import cn.mcmod.tofucraft.entity.projectile.EntityFukumame;
import cn.mcmod.tofucraft.entity.projectile.EntityZundaArrow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TofuEntityRegister {

    public static EnumCreatureAttribute tofunian = EnumHelper.addCreatureAttribute("tofunian");


    public static void entityRegister() {
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofucow"), EntityTofuCow.class, prefix("TofuCow"), 1, TofuMain.instance, 80, 3, true, 0xEBE8E8, 0xA3A3A3);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofuslime"), EntityTofuSlime.class, prefix("TofuSlime"), 2, TofuMain.instance, 80, 3, true, 0xEBE8E8, 0x2E2E2E);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofunian"), EntityTofunian.class, prefix("Tofunian"), 3, TofuMain.instance, 80, 3, false, 0xEBE8E8, 0xCACFA1);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofuchinger"), EntityTofuChinger.class, prefix("TofuChinger"), 4, TofuMain.instance, 80, 3, true, 0xEBE8E8, 0xB3B3B3);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofuspider"), EntityTofuSpider.class, prefix("TofuSpider"), 5, TofuMain.instance, 80, 3, true, 0xEBE8E8, 0x080808);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "fukumame"), EntityFukumame.class, prefix("Fukumame"), 6, TofuMain.instance, 100, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "zundaarrow"), EntityZundaArrow.class, prefix("ZundaArrow"), 7, TofuMain.instance, 100, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(TofuMain.MODID, "tofufish"), EntityTofuFish.class, prefix("TofuFish"), 8, TofuMain.instance, 80, 3, true);

        EntitySpawnPlacementRegistry.setPlacementType(EntityTofuFish.class, EntityLiving.SpawnPlacementType.IN_WATER);
    }

    private static String prefix(String path) {

        return TofuMain.MODID + "." + path;

    }

    @SideOnly(Side.CLIENT)
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuCow.class, RenderTofuCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuSlime.class, RenderTofuSlime::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTofunian.class, RenderTofunian::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuChinger.class, RenderTofuChinger::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuSpider.class, RenderTofuSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFukumame.class, RenderFukumame::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityZundaArrow.class, RenderZundaArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTofuFish.class, RenderTofuFish::new);
    }
    public static void entitySpawn() {

        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<BiomeManager.BiomeEntry>();

        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL));

        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT));

        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.ICY));

        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.WARM));

        List<Biome> biomes = new ArrayList<Biome>();

        for (BiomeManager.BiomeEntry b : biomeEntries) {

            biomes.add(b.biome);

        }

        biomes.addAll(BiomeManager.oceanBiomes);


        EntityRegistry.addSpawn(EntityTofuSlime.class, 12, 1, 1, EnumCreatureType.MONSTER,biomes.toArray(new Biome[biomes.size()]));
    }
}
