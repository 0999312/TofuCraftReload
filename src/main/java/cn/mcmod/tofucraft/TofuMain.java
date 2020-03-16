package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.compat.TofuCompat;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.entity.TofuVillages;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.world.WorldProviderTofu;
import cn.mcmod.tofucraft.world.biome.TofuBiomes;
import cn.mcmod.tofucraft.world.gen.future.TofuOreGenerator;
import cn.mcmod.tofucraft.world.gen.structure.MapGenTofuVillage;
import cn.mcmod.tofucraft.world.gen.structure.tofucastle.MapGenTofuCastle;
import cn.mcmod.tofucraft.world.gen.structure.tofucastle.TofuCastlePiece;
import cn.mcmod.tofucraft.world.gen.structure.tofumineshaft.StructureTofuMineshaftPieces;
import cn.mcmod.tofucraft.world.gen.structure.tofumineshaft.StructureTofuMineshaftStart;
import cn.mcmod.tofucraft.world.gen.structure.tofuvillage.StructureTofuVillagePieces;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = TofuMain.MODID, name = TofuMain.NAME, version = TofuMain.VERSION, dependencies = "before:tconstruct;required-after:mm_lib;")
public class TofuMain {
    public static final String MODID = "tofucraft";
    public static final String NAME = "TofuCraftReload";
    public static final String VERSION = "@version@";

    @Instance(TofuMain.MODID)
    public static TofuMain instance;

    @SidedProxy(clientSide = "cn.mcmod.tofucraft.ClientProxy", serverSide = "cn.mcmod.tofucraft.CommonProxy")
    public static CommonProxy proxy;

    public static DimensionType TOFU_DIMENSION;

    public static DamageSource zunda;

    public static Logger logger;

    @EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        FluidRegistry.enableUniversalBucket();
    }

    @SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event) {
        IForgeRegistry<Biome> registry = event.getRegistry();

        TofuBiomes.register(registry);

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        logger = event.getModLog();

        TofuEntityRegister.entitySpawn();

        TofuCompat.preInit();

        GameRegistry.registerWorldGenerator(new TofuOreGenerator(), 0);

        MapGenStructureIO.registerStructure(MapGenTofuVillage.Start.class,"TofuVillage");
        StructureTofuVillagePieces.registerVillagePieces();
        MapGenStructureIO.registerStructure(StructureTofuMineshaftStart.class,"TofuMineshaft");
        StructureTofuMineshaftPieces.registerStructurePieces();
        MapGenStructureIO.registerStructure(MapGenTofuCastle.Start.class, "TofuCastle");
        TofuCastlePiece.registerTofuCastlePiece();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TofuGuiHandler());


        zunda = new DamageSource("zunda") {
            @Override
            public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
                String s = "death.attack.zunda";
                String s1 = s + ".player";

                return new TextComponentString(entityLivingBaseIn.getDisplayName().getFormattedText() + " ").appendSibling(new TextComponentTranslation(s1, new Object[]{entityLivingBaseIn.getDisplayName()}));
            }
        }.setDamageIsAbsolute();

        TOFU_DIMENSION = DimensionType.register("Tofu World", "_tofu", TofuConfig.dimensionID, WorldProviderTofu.class, false);
        DimensionManager.registerDimension(TofuConfig.dimensionID, TOFU_DIMENSION);

        TofuVillages.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        TofuCompat.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
