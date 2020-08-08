package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.door.BlockTofuDoor;
import cn.mcmod.tofucraft.block.fluid.*;
import cn.mcmod.tofucraft.block.mecha.*;
import cn.mcmod.tofucraft.block.mecha.antennas.BlockAntennaBasic;
import cn.mcmod.tofucraft.block.ore.BlockTofuGemOre;
import cn.mcmod.tofucraft.block.ore.BlockTofuOreDiamond;
import cn.mcmod.tofucraft.block.plants.*;
import cn.mcmod.tofucraft.block.plants.vine.BlockTofuYuba;
import cn.mcmod.tofucraft.block.plants.vine.BlockUnderVine;
import cn.mcmod.tofucraft.block.tofu.BlockTofu;
import cn.mcmod.tofucraft.block.tofu.BlockTofuLadder;
import cn.mcmod.tofucraft.block.tofu.BlockTofuStairs;
import cn.mcmod.tofucraft.block.torch.BlockTofuTorch;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.material.TofuMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod_mmf.mmlib.block.slab.BlockSlabBase;
import cn.mcmod_mmf.mmlib.item.ItemSlabBase;
import cn.mcmod_mmf.mmlib.register.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader {
    public static Block SOYMILK;
    public static Fluid SOYMILK_FLUID;
    public static Block ZUNDASOYMILK;
    public static Fluid ZUNDASOYMILK_FLUID;
    public static Block NIGARI;
    public static Fluid NIGARI_FLUID;
    public static Block SOYSAUCE;
    public static Fluid SOYSAUCE_FLUID;
    public static Block SOYMILKHELL;
    public static Fluid SOYMILKHELL_FLUID;

    public static Block SALTFURNACE = new BlockSaltFurnace(false).setCreativeTab(CommonProxy.tab);
    public static Block SALTFURNACE_LIT = new BlockSaltFurnace(true);
    public static Block KINUTOFU = new BlockTofu(TofuType.kinu).setFragile().setFreeze(3).setHardness(0.2F).setResistance(0.2F);
    public static Block MOMENTOFU = new BlockTofu(TofuType.momen).setDrain(3);
    public static Block ISHITOFU = new BlockTofu(TofuType.ishi, Material.ROCK).setDrain(5).setHardness(1.8F).setResistance(9.0F);
    public static Block METALTOFU = new BlockTofu(TofuType.metal, Material.IRON).setHardness(5.0F).setResistance(11.0F);
    public static Block ANNINTOFU = new BlockTofu(TofuType.annin);
    public static Block EGGTOFU = new BlockTofu(TofuType.egg);
    public static Block GRILD = new BlockTofu(TofuType.grilled);
    public static Block TOFUDRIED = new BlockTofu(TofuType.dried);
    public static Block TOFUZUNDA = new BlockTofu(TofuType.zunda);
    public static Block TOFUHELL = new BlockTofu(TofuType.hell);
    public static Block TOFUDIAMOND = new BlockTofu(TofuType.diamond, Material.IRON).setHardness(3.5F).setResistance(9.5F);
    public static Block TF_MACHINE_CASE= new Block(Material.IRON).setCreativeTab(CommonProxy.tab).setHardness(5.5F).setResistance(11.5F);
    public static Block ADVTOFUGEM_BLOCK= new Block(Material.IRON).setCreativeTab(CommonProxy.tab).setHardness(3.5F).setResistance(9.5F);

    public static BlockLeek LEEK = new BlockLeek();
    public static BlockLeekCrop LEEKCROP = new BlockLeekCrop();
    public static Block SOYBEAN = new BlockSoybean();
    public static Block SOYBEAN_NETHER = new BlockSoybeanNether();
    public static Block SPROUTS = new BlockSprouts();
    public static Block RICECROP = new BlockRice();
    public static Block tofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
    public static Block zundatofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
    public static BlockUnderVine yubaGrass = new BlockTofuYuba();
    public static BlockTofuSapling TOFU_SAPLING = new BlockTofuSapling();
    public static BlockTofuLeaves TOFU_LEAVE = new BlockTofuLeaves();
    public static BlockApricotSapling APRICOT_SAPLING = new BlockApricotSapling();
    public static BlockApricotLeaves APRICOT_LEAVE = new BlockApricotLeaves();
    public static BlockCake tofu_Cake = new BlockTofuCake();
    public static BlockTofuPortal tofu_PORTAL = new BlockTofuPortal();
    public static Block TOFUISHI_BRICK = new Block(Material.ROCK).setHardness(1.82F).setResistance(9.5F).setCreativeTab(CommonProxy.tab);
    public static Block TOFUZUNDA_BRICK = new Block(Material.ROCK).setHardness(1.82F).setResistance(9.5F).setCreativeTab(CommonProxy.tab);
    public static Block TOFUZUNDA_SMOOTHBRICK = new Block(Material.ROCK).setHardness(1.82F).setResistance(9.5F).setCreativeTab(CommonProxy.tab);


    public static BlockTofuTorch TOFUKINU_TORCH = new BlockTofuTorch();
    public static BlockTofuTorch TOFUMOMEN_TORCH = new BlockTofuTorch();
    public static BlockTofuTorch TOFUISHI_TORCH = new BlockTofuTorch(SoundType.STONE);
    public static BlockTofuTorch TOFUMETAL_TORCH = new BlockTofuTorch(SoundType.METAL);
    public static BlockTofuTorch TOFUZUNDA_TORCH = new BlockTofuTorch();

    public static BlockTofuDoor TOFUKINU_DOOR = new BlockTofuDoor(TofuMaterial.WOOD, TofuType.kinu);
    public static BlockTofuDoor TOFUMOMEN_DOOR = new BlockTofuDoor(TofuMaterial.WOOD, TofuType.momen);
    public static BlockTofuDoor TOFUISHI_DOOR = new BlockTofuDoor(Material.ROCK, TofuType.ishi);
    public static BlockTofuDoor TOFUMETAL_DOOR = new BlockTofuDoor(Material.IRON, TofuType.metal);
    public static BlockTofuDoor TOFUZUNDA_DOOR = new BlockTofuDoor(Material.WOOD, TofuType.zunda);

    public static Block TOFUKINU_STAIRS = new BlockTofuStairs(KINUTOFU.getDefaultState()).setFragile();
    public static Block TOFUMOMEN_STAIRS = new BlockTofuStairs(MOMENTOFU.getDefaultState());
    public static Block TOFUISHI_STAIRS = new BlockTofuStairs(ISHITOFU.getDefaultState());
    public static Block TOFUMETAL_STAIRS = new BlockTofuStairs(METALTOFU.getDefaultState());
    public static Block TOFUZUNDA_STAIRS = new BlockTofuStairs(TOFUZUNDA.getDefaultState());
    public static Block TOFUISHI_BRICK_STAIRS = new BlockTofuStairs(TOFUISHI_BRICK.getDefaultState());
    public static Block TOFUCHEST = new BlockTofuChest();
    public static Block SALTPAN = new BlockSaltPan().setHardness(0.2F).setCreativeTab(CommonProxy.tab);

    public static BlockSlabBase TOFUKINU_SLAB = (BlockSlabBase) new BlockSlabBase(TofuMaterial.softtofu).setHardness(0.4F).setResistance(1.0F);
    public static BlockSlabBase TOFUMOMEN_SLAB = (BlockSlabBase) new BlockSlabBase(TofuMaterial.tofu).setHardness(0.6F).setResistance(2.0F);
    public static BlockSlabBase TOFUISHI_SLAB = (BlockSlabBase) new BlockSlabBase(Material.ROCK).setHardness(1.8F).setResistance(9.0F);
    public static BlockSlabBase TOFUMETAL_SLAB = (BlockSlabBase) new BlockSlabBase(Material.IRON).setHardness(5.0F).setResistance(11.0F);
    public static BlockSlabBase TOFUZUNDA_SLAB = (BlockSlabBase) new BlockSlabBase(TofuMaterial.tofu).setHardness(0.6F).setResistance(2.0F);
    public static BlockSlabBase TOFUISHI_BRICK_SLAB = (BlockSlabBase) new BlockSlabBase(Material.ROCK).setHardness(1.8F).setResistance(9.0F);

    public static BlockTofuLadder TOFUKINU_LADDER = new BlockTofuLadder(TofuMaterial.softtofu);
    public static BlockTofuLadder TOFUMOMEN_LADDER = new BlockTofuLadder(TofuMaterial.tofu);
    public static BlockTofuLadder TOFUISHI_LADDER = new BlockTofuLadder(Material.ROCK);
    public static BlockTofuLadder TOFUMETAL_LADDER = new BlockTofuLadder(Material.IRON);
    public static BlockTofuLadder TOFUZUNDA_LADDER = new BlockTofuLadder(TofuMaterial.tofu);
    public static BlockTofuLadder TOFUISHI_BRICK_LADDER = new BlockTofuLadder(Material.ROCK);

    public static Block TOFUBEDROCK = new Block(Material.ROCK).setCreativeTab(CommonProxy.tab).setBlockUnbreakable().setResistance(1000000.0F);
    public static Block TOFUFARMLAND = new BlockTofuFarmLand();
    public static BlockBush TOFUFLOWER = new BlockTofuFlower();
    public static Block TOFUORE_DIAMOND = new BlockTofuOreDiamond();
    public static Block TOFUGEM_ORE = new BlockTofuGemOre();

    public static Block YUBA_NOREN = new BlockYubaNoren();

    public static BlockBarrel MISOBARREL = new BlockMisoBarrel().setDrain(3);
    public static BlockNattoBed NATTO = new BlockNattoBed(new ItemStack(ItemLoader.material, 6, 8), new ItemStack[]{
            new ItemStack(Items.WHEAT, 3), new ItemStack(ItemLoader.soybeans, 3)
    }).setDrain(2);
    public static BlockBarrel DOUBANJIANGBARREL = new BlockDoubanjiangBarrel().setDrain(3);
    public static BlockBarrel MISOTOFUBARREL = new BlockBarrel(new ItemStack(ItemLoader.tofu_food, 3, 11), new ItemStack[]{
            new ItemStack(ItemLoader.tofu_food, 3, 1), new ItemStack(ItemLoader.material, 3, 2)
    }).setDrain(3);
    public static Block TOFUSTORAGEMACHINE = new BlockTFStorage();

    public static Block ANTENNA_BASIC = new BlockAntennaBasic();

    public static Block TFOVEN = new BlockTFOven();
    public static Block TFOVEN_LIT = new BlockTFOven();
    public static Block TFCRASHER = new BlockTFCrasher();
    public static Block TFCRASHER_LIT = new BlockTFCrasher();
    public static Block TFCOMPRESSOR = new BlockTFCompressor();
    public static Block TFCOMPRESSOR_LIT = new BlockTFCompressor();
    public static Block TFCOLLECTOR = new BlockTFCollector();
    public static Block TFAGGREGATOR = new BlockAggregator();
    public static Block TFAGGREGATOR_LIT = new BlockAggregator();
    public static Block TFAdvancedAGGREGATOR = new BlockAdvancedAggregator();
    public static Block TFAdvancedAGGREGATOR_LIT = new BlockAdvancedAggregator();
    
    public static BlockYuba YUBA_FLOW = new BlockYuba();
    public static BlockTofuStation TOFUSTATION = new BlockTofuStation();
    
    

    public BlockLoader(FMLPreInitializationEvent event) {
        SOYMILK_FLUID = SoyMilkFluid.instance;
        FluidRegistry.addBucketForFluid(SOYMILK_FLUID);
        SOYMILK = BlockRegister.getInstance().registerFluidBlock(TofuMain.MODID,SOYMILK_FLUID, new BlockSoyMilk(SOYMILK_FLUID), "soymilk");
        SOYMILKHELL_FLUID = SoyMilkHellFluid.instance;
        FluidRegistry.addBucketForFluid(SOYMILKHELL_FLUID);
        SOYMILKHELL = BlockRegister.getInstance().registerFluidBlock(TofuMain.MODID,SOYMILKHELL_FLUID, new BlockSoyMilk(SOYMILKHELL_FLUID), "soymilk_hell");

        ZUNDASOYMILK_FLUID = ZundaSoyMilkFluid.instance;
        FluidRegistry.addBucketForFluid(ZUNDASOYMILK_FLUID);
        ZUNDASOYMILK = BlockRegister.getInstance().registerFluidBlock(TofuMain.MODID,ZUNDASOYMILK_FLUID, new BlockSoyMilk(ZUNDASOYMILK_FLUID), "zunda_soymilk");

        NIGARI_FLUID = NigariFluid.instance;
        FluidRegistry.addBucketForFluid(NIGARI_FLUID);
        NIGARI = BlockRegister.getInstance().registerFluidBlock(TofuMain.MODID,NIGARI_FLUID, new BlockNigari(NIGARI_FLUID), "nigari");

        SOYSAUCE_FLUID = SoySauceFluid.instance;
        FluidRegistry.addBucketForFluid(SOYSAUCE_FLUID);
        SOYSAUCE = BlockRegister.getInstance().registerFluidBlock(TofuMain.MODID,SOYSAUCE_FLUID, new BlockSoySauce(SOYSAUCE_FLUID), "soysauce");

        register(TF_MACHINE_CASE, new ItemBlock(TF_MACHINE_CASE), "tf_machine_case");
        register(TOFUSTORAGEMACHINE, new ItemBlock(TOFUSTORAGEMACHINE), "tfstorage");
        register(TFCOLLECTOR, new ItemBlock(TFCOLLECTOR), "tfcollector");
        register(ANTENNA_BASIC, new ItemBlock(ANTENNA_BASIC), "antenna_basic");
        register(TFOVEN, new ItemBlock(TFOVEN), "tfoven");
        registerNoItem(TFOVEN_LIT, "tfoven_lit");
        register(TFCRASHER, new ItemBlock(TFCRASHER), "tfcrasher");
        registerNoItem(TFCRASHER_LIT, "tfcrasher_lit");
        register(TFCOMPRESSOR, new ItemBlock(TFCOMPRESSOR), "tfcompressor");
        registerNoItem(TFCOMPRESSOR_LIT, "tfcompressor_lit");
        register(TFAGGREGATOR, new ItemBlock(TFAGGREGATOR), "tfaggregator");
        registerNoItem(TFAGGREGATOR_LIT, "tfaggregator_lit");
        register(TFAdvancedAGGREGATOR, new ItemBlock(TFAdvancedAGGREGATOR), "tfadvanced_aggregator");
        registerNoItem(TFAdvancedAGGREGATOR_LIT, "tfadvanced_aggregator_lit");

        register(TOFUSTATION, new ItemBlock(TOFUSTATION), "tofuworkstation");

        register(SALTFURNACE, new ItemBlock(SALTFURNACE), "saltfurnace");
        register(SALTFURNACE_LIT, new ItemBlock(SALTFURNACE_LIT), "saltfurnace_lit");
        register(SALTPAN, new ItemBlock(SALTPAN), "blocksaltpan");
        register(KINUTOFU, new ItemBlock(KINUTOFU), "blocktofukinu");
        register(MOMENTOFU, new ItemBlock(MOMENTOFU), "blocktofumomen");
        register(ISHITOFU, new ItemBlock(ISHITOFU), "blocktofuishi");
        register(METALTOFU, new ItemBlock(METALTOFU), "blocktofumetal");
        register(ANNINTOFU, new ItemBlock(ANNINTOFU), "blocktofuannin");
        register(EGGTOFU, new ItemBlock(EGGTOFU), "blocktofuegg");
        register(TOFUHELL, new ItemBlock(TOFUHELL), "blocktofuhell");
        register(TOFUDIAMOND, new ItemBlock(TOFUDIAMOND), "blocktofudiamond");
        register(GRILD, new ItemBlock(GRILD), "blocktofugrilled");
        register(TOFUDRIED, new ItemBlock(TOFUDRIED), "blocktofudried");
        register(TOFUZUNDA, new ItemBlock(TOFUZUNDA), "blocktofuzunda");
        register(TOFUISHI_BRICK, new ItemBlock(TOFUISHI_BRICK), "tofuishi_brick");
        register(TOFUZUNDA_BRICK, new ItemBlock(TOFUZUNDA_BRICK), "tofuzunda_brick");
        register(TOFUZUNDA_SMOOTHBRICK, new ItemBlock(TOFUZUNDA_SMOOTHBRICK), "tofuzunda_smoothbrick");

        register(MISOBARREL, new ItemBlock(MISOBARREL), "barrelmiso");
        register(DOUBANJIANGBARREL, new ItemBlock(DOUBANJIANGBARREL), "barreldoubanjiang");
        register(MISOTOFUBARREL, new ItemBlock(MISOTOFUBARREL), "barrelmisotofu");
        register(ADVTOFUGEM_BLOCK, new ItemBlock(ADVTOFUGEM_BLOCK), "advtofugem_block");
        register(LEEK, new ItemBlock(LEEK), "blockleek");
        registerNoItem(LEEKCROP, "blockleek_crop");
        registerNoItem(SOYBEAN, "soybean");
        registerNoItem(SOYBEAN_NETHER, "soybean_nether");
        registerNoItem(SPROUTS, "blocksprouts");
        registerNoItem(RICECROP, "ricecrop");
        register(yubaGrass, new ItemBlock(yubaGrass), "yubagrass");
        register(tofuTerrain, new ItemBlock(tofuTerrain), "tofu_terrain");
        register(zundatofuTerrain, new ItemBlock(zundatofuTerrain), "zundatofu_terrain");
        register(TOFU_SAPLING, new ItemBlock(TOFU_SAPLING), "sapling_tofu");
        register(TOFU_LEAVE, new ItemBlock(TOFU_LEAVE), "leaves_tofu");
        register(APRICOT_SAPLING, new ItemBlock(APRICOT_SAPLING), "sapling_apricot");
        register(APRICOT_LEAVE, new ItemBlock(APRICOT_LEAVE), "leaves_apricot");

        register(NATTO, new ItemBlock(NATTO), "nattobed");

        register(tofu_Cake, new ItemBlock(tofu_Cake), "tofucake");
        register(tofu_PORTAL, new ItemBlock(tofu_PORTAL), "tofuportal");

        register(TOFUKINU_STAIRS, new ItemBlock(TOFUKINU_STAIRS), "tofustair_kinu");
        register(TOFUMOMEN_STAIRS, new ItemBlock(TOFUMOMEN_STAIRS), "tofustair_momen");
        register(TOFUISHI_STAIRS, new ItemBlock(TOFUISHI_STAIRS), "tofustair_ishi");
        register(TOFUMETAL_STAIRS, new ItemBlock(TOFUMETAL_STAIRS), "tofustair_metal");
        register(TOFUZUNDA_STAIRS, new ItemBlock(TOFUZUNDA_STAIRS), "tofustair_zunda");
        register(TOFUISHI_BRICK_STAIRS, new ItemBlock(TOFUISHI_BRICK_STAIRS), "tofustair_ishibrick");

        register(TOFUKINU_SLAB, new ItemSlabBase(TOFUKINU_SLAB), "tofuslab_kinu");
        register(TOFUMOMEN_SLAB, new ItemSlabBase(TOFUMOMEN_SLAB), "tofuslab_momen");
        register(TOFUISHI_SLAB, new ItemSlabBase(TOFUISHI_SLAB), "tofuslab_ishi");
        register(TOFUMETAL_SLAB, new ItemSlabBase(TOFUMETAL_SLAB), "tofuslab_metal");
        register(TOFUZUNDA_SLAB, new ItemSlabBase(TOFUZUNDA_SLAB), "tofuslab_zunda");
        register(TOFUISHI_BRICK_SLAB, new ItemSlabBase(TOFUISHI_BRICK_SLAB), "tofuslab_ishibrick");

        register(TOFUKINU_LADDER, new ItemBlock(TOFUKINU_LADDER), "tofuladder_kinu");
        register(TOFUMOMEN_LADDER, new ItemBlock(TOFUMOMEN_LADDER), "tofuladder_momen");
        register(TOFUISHI_LADDER, new ItemBlock(TOFUISHI_LADDER), "tofuladder_ishi");
        register(TOFUMETAL_LADDER, new ItemBlock(TOFUMETAL_LADDER), "tofuladder_metal");
        register(TOFUZUNDA_LADDER, new ItemBlock(TOFUZUNDA_LADDER), "tofuladder_zunda");
        register(TOFUISHI_BRICK_LADDER, new ItemBlock(TOFUISHI_BRICK_LADDER), "tofuladder_ishibrick");

        register(TOFUKINU_TORCH, new ItemBlock(TOFUKINU_TORCH), "tofutorch_kinu");
        register(TOFUMOMEN_TORCH, new ItemBlock(TOFUMOMEN_TORCH), "tofutorch_momen");
        register(TOFUISHI_TORCH, new ItemBlock(TOFUISHI_TORCH), "tofutorch_ishi");
        register(TOFUMETAL_TORCH, new ItemBlock(TOFUMETAL_TORCH), "tofutorch_metal");
        register(TOFUZUNDA_TORCH, new ItemBlock(TOFUZUNDA_TORCH), "tofutorch_zunda");

        register(TOFUCHEST, new ItemBlock(TOFUCHEST), "tofuchest");

        register(TOFUBEDROCK, new ItemBlock(TOFUBEDROCK), "tofubedrock");
        register(TOFUFARMLAND, new ItemBlock(TOFUFARMLAND), "tofu_farmland");
        register(TOFUFLOWER, new ItemBlock(TOFUFLOWER), "tofuflower");
        register(TOFUORE_DIAMOND, new ItemBlock(TOFUORE_DIAMOND), "ore_tofudiamond");
        register(TOFUGEM_ORE, new ItemBlock(TOFUGEM_ORE), "blockoretofu");

        register(YUBA_NOREN, new ItemBlock(YUBA_NOREN), "yuba_noren");

        registerNoItem(YUBA_FLOW, "blockyuba");
        registerNoItem(TOFUKINU_DOOR, "tofudoor_kinu");
        registerNoItem(TOFUMOMEN_DOOR, "tofudoor_momen");
        registerNoItem(TOFUISHI_DOOR, "tofudoor_ishi");
        registerNoItem(TOFUMETAL_DOOR, "tofudoor_metal");
        registerNoItem(TOFUZUNDA_DOOR, "tofudoor_zunda");
    }

    private static void register(Block block, Item itemBlock, String string) {
        block.setCreativeTab(CommonProxy.tab);
        BlockRegister.getInstance().register(TofuMain.MODID, block, itemBlock, string);
    }
    private static void registerNoItem(Block block, String string) {
        BlockRegister.getInstance().registerNoItem(TofuMain.MODID, block, string);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
    	BlockRegister.getInstance().registerFluidBlockRendering(TofuMain.MODID, SOYMILK, "soymilk");
    	BlockRegister.getInstance().registerFluidBlockRendering(TofuMain.MODID, SOYMILKHELL, "soymilk_hell");
    	BlockRegister.getInstance().registerFluidBlockRendering(TofuMain.MODID, ZUNDASOYMILK, "zunda_soymilk");
    	BlockRegister.getInstance().registerFluidBlockRendering(TofuMain.MODID, NIGARI, "nigari");
    	BlockRegister.getInstance().registerFluidBlockRendering(TofuMain.MODID, SOYSAUCE, "soysauce");
    	BlockRegister.getInstance().registerRender(ADVTOFUGEM_BLOCK);
    	BlockRegister.getInstance().registerRender(TF_MACHINE_CASE);
    	BlockRegister.getInstance().registerRender(TFCOLLECTOR);
    	BlockRegister.getInstance().registerRender(TFAGGREGATOR);
    	BlockRegister.getInstance().registerRender(TFAGGREGATOR_LIT);
    	BlockRegister.getInstance().registerRender(TFAdvancedAGGREGATOR);
    	BlockRegister.getInstance().registerRender(TFAdvancedAGGREGATOR_LIT);
    	BlockRegister.getInstance().registerRender(TFCOMPRESSOR);
    	BlockRegister.getInstance().registerRender(TFCOMPRESSOR_LIT);
    	BlockRegister.getInstance().registerRender(TFOVEN);
    	BlockRegister.getInstance().registerRender(TFOVEN_LIT);
    	BlockRegister.getInstance().registerRender(TFCRASHER);
    	BlockRegister.getInstance().registerRender(TFCRASHER_LIT);
    	BlockRegister.getInstance().registerRender(TOFUHELL);
    	BlockRegister.getInstance().registerRender(TOFUDIAMOND);
    	BlockRegister.getInstance().registerRender(YUBA_FLOW);
    	BlockRegister.getInstance().registerRender(MISOBARREL);
    	BlockRegister.getInstance().registerRender(DOUBANJIANGBARREL);
    	BlockRegister.getInstance().registerRender(MISOTOFUBARREL);
    	BlockRegister.getInstance().registerRender(NATTO);
    	BlockRegister.getInstance().registerRender(SPROUTS);
    	BlockRegister.getInstance().registerRender(tofu_Cake);
    	BlockRegister.getInstance().registerRender(TOFUDRIED);
    	BlockRegister.getInstance().registerRender(SALTFURNACE);
    	BlockRegister.getInstance().registerRender(SALTFURNACE_LIT);
    	BlockRegister.getInstance().registerRender(TOFUSTORAGEMACHINE);
    	BlockRegister.getInstance().registerRender(ANTENNA_BASIC);
    	BlockRegister.getInstance().registerRender(KINUTOFU);
    	BlockRegister.getInstance().registerRender(MOMENTOFU);
    	BlockRegister.getInstance().registerRender(ISHITOFU);
    	BlockRegister.getInstance().registerRender(METALTOFU);
    	BlockRegister.getInstance().registerRender(ANNINTOFU);
    	BlockRegister.getInstance().registerRender(EGGTOFU);
    	BlockRegister.getInstance().registerRender(GRILD);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA);
    	BlockRegister.getInstance().registerRender(LEEK);
    	BlockRegister.getInstance().registerRender(LEEKCROP);
    	BlockRegister.getInstance().registerRender(TOFU_SAPLING);
    	BlockRegister.getInstance().registerRender(yubaGrass);
    	BlockRegister.getInstance().registerRender(tofuTerrain);
    	BlockRegister.getInstance().registerRender(zundatofuTerrain);
    	BlockRegister.getInstance().registerRender(TOFU_LEAVE);
    	BlockRegister.getInstance().registerRender(APRICOT_SAPLING);
    	BlockRegister.getInstance().registerRender(APRICOT_LEAVE);
    	BlockRegister.getInstance().registerRender(tofu_PORTAL);
    	BlockRegister.getInstance().registerRender(TOFUISHI_BRICK);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_BRICK);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_SMOOTHBRICK);

    	BlockRegister.getInstance().registerRender(TOFUKINU_TORCH);
    	BlockRegister.getInstance().registerRender(TOFUMOMEN_TORCH);
    	BlockRegister.getInstance().registerRender(TOFUISHI_TORCH);
    	BlockRegister.getInstance().registerRender(TOFUMETAL_TORCH);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_TORCH);

    	BlockRegister.getInstance().registerRender(TOFUKINU_DOOR);
    	BlockRegister.getInstance().registerRender(TOFUMOMEN_DOOR);
    	BlockRegister.getInstance().registerRender(TOFUISHI_DOOR);
    	BlockRegister.getInstance().registerRender(TOFUMETAL_DOOR);
    	BlockRegister.getInstance().registerRender(SALTPAN);
    	BlockRegister.getInstance().registerRender(TOFUKINU_STAIRS);
    	BlockRegister.getInstance().registerRender(TOFUISHI_BRICK_STAIRS);
    	BlockRegister.getInstance().registerRender(TOFUMOMEN_STAIRS);
    	BlockRegister.getInstance().registerRender(TOFUISHI_STAIRS);
    	BlockRegister.getInstance().registerRender(TOFUMETAL_STAIRS);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_STAIRS);

    	BlockRegister.getInstance().registerRender(TOFUKINU_SLAB);
    	BlockRegister.getInstance().registerRender(TOFUISHI_BRICK_SLAB);
    	BlockRegister.getInstance().registerRender(TOFUMOMEN_SLAB);
    	BlockRegister.getInstance().registerRender(TOFUISHI_SLAB);
    	BlockRegister.getInstance().registerRender(TOFUMETAL_SLAB);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_SLAB);

    	BlockRegister.getInstance().registerRender(TOFUKINU_LADDER);
    	BlockRegister.getInstance().registerRender(TOFUISHI_BRICK_LADDER);
    	BlockRegister.getInstance().registerRender(TOFUMOMEN_LADDER);
    	BlockRegister.getInstance().registerRender(TOFUISHI_LADDER);
    	BlockRegister.getInstance().registerRender(TOFUMETAL_LADDER);
    	BlockRegister.getInstance().registerRender(TOFUZUNDA_LADDER);

    	BlockRegister.getInstance().registerRender(TOFUBEDROCK);
    	BlockRegister.getInstance().registerRender(TOFUORE_DIAMOND);
    	BlockRegister.getInstance().registerRender(TOFUGEM_ORE);

    	BlockRegister.getInstance().registerRender(TOFUFARMLAND);
    	BlockRegister.getInstance().registerRender(TOFUFLOWER);
    	BlockRegister.getInstance().registerRender(TOFUCHEST);
    	BlockRegister.getInstance().registerRender(YUBA_NOREN);
    	BlockRegister.getInstance().registerRender(TOFUSTATION);
    }

}
