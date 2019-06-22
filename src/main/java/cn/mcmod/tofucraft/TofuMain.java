package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.entity.TofuVillages;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.world.WorldProviderTofu;
import cn.mcmod.tofucraft.world.biome.TofuBiomes;
import cn.mcmod.tofucraft.world.gen.structure.MapGenTofuVillage;
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
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = TofuMain.MODID, name = TofuMain.NAME, version = TofuMain.VERSION)
public class TofuMain {
    public static final String MODID = "tofucraft";
    public static final String NAME = "TofuCraftReload";
    public static final String VERSION = "0.0.5.5";

    @Instance(TofuMain.MODID)
    public static TofuMain instance;

    @SidedProxy(clientSide = "cn.mcmod.tofucraft.ClientProxy", serverSide = "cn.mcmod.tofucraft.CommonProxy")
    public static CommonProxy proxy;

    public static DimensionType TOFU_DIMENSION;

    public static DamageSource zunda;

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

        TofuEntityRegister.entitySpawn();

        MapGenStructureIO.registerStructure(MapGenTofuVillage.Start.class,"TofuVillage");
        StructureTofuVillagePieces.registerVillagePieces();
        MapGenStructureIO.registerStructure(StructureTofuMineshaftStart.class,"TofuMineshaft");
        StructureTofuMineshaftPieces.registerStructurePieces();

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
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
