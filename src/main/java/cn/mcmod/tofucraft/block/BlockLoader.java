package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.door.BlockTofuDoor;
import cn.mcmod.tofucraft.block.fluid.*;
import cn.mcmod.tofucraft.block.half.BlockTofuSlab;
import cn.mcmod.tofucraft.block.ore.BlockTofuGemOre;
import cn.mcmod.tofucraft.block.ore.BlockTofuOreDiamond;
import cn.mcmod.tofucraft.block.plants.*;
import cn.mcmod.tofucraft.block.plants.vine.BlockTofuYuba;
import cn.mcmod.tofucraft.block.plants.vine.BlockUnderVine;
import cn.mcmod.tofucraft.block.torch.BlockTofuTorch;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.item.ItemTofuSlab;
import cn.mcmod.tofucraft.material.TofuMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.JSON_Creator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

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
	public static Block ISHITOFU = new BlockTofu(TofuType.ishi,Material.ROCK).setDrain(5).setHardness(1.8F).setResistance(9.0F);
	public static Block METALTOFU = new BlockTofu(TofuType.metal,Material.IRON).setHardness(5.0F).setResistance(11.0F);
	public static Block ANNINTOFU = new BlockTofu(TofuType.annin);
	public static Block EGGTOFU = new BlockTofu(TofuType.egg);
	public static Block GRILD = new BlockTofu(TofuType.grilled);
	public static Block TOFUDRIED = new BlockTofu(TofuType.dried);
	public static Block TOFUZUNDA = new BlockTofu(TofuType.zunda);
	public static Block TOFUHELL = new BlockTofu(TofuType.hell);

	public static BlockLeek LEEK = new BlockLeek();
	public static Block SOYBEAN = new BlockSoybean();
	public static Block SOYBEAN_NETHER = new BlockSoybeanNether();
    public static Block SPROUTS = new BlockSprouts();
	public static Block RICECROP = new BlockRice();
	public static Block tofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
	public static Block zundatofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
	public static BlockUnderVine yubaGrass = new BlockTofuYuba();
	public static BlockTofuSapling TOFU_SAPLING = new BlockTofuSapling();
	public static BlockTofuLeaves TOFU_LEAVE = new BlockTofuLeaves();
	public static Block TOFU_LOG = new BlockTofuLog();
	public static BlockApricotSapling APRICOT_SAPLING = new BlockApricotSapling();
	public static BlockApricotLeaves APRICOT_LEAVE = new BlockApricotLeaves();
	public static BlockCake tofu_Cake = new BlockTofuCake();
	public static BlockTofuPortal tofu_PORTAL = new BlockTofuPortal();
	public static Block TOFUISHI_BRICK = new Block(Material.ROCK).setHardness(1.82F).setResistance(9.5F).setCreativeTab(CommonProxy.tab);

	public static BlockTofuTorch TOFUKINU_TORCH = new BlockTofuTorch();
	public static BlockTofuTorch TOFUMOMEN_TORCH = new BlockTofuTorch();
	public static BlockTofuTorch TOFUISHI_TORCH = new BlockTofuTorch(SoundType.STONE);
	public static BlockTofuTorch TOFUMETAL_TORCH = new BlockTofuTorch(SoundType.METAL);
	public static BlockTofuTorch TOFUZUNDA_TORCH = new BlockTofuTorch();

	public static BlockTofuDoor TOFUKINU_DOOR = new BlockTofuDoor(TofuMaterial.WOOD,TofuType.kinu);
	public static BlockTofuDoor TOFUMOMEN_DOOR = new BlockTofuDoor(TofuMaterial.WOOD,TofuType.momen);
	public static BlockTofuDoor TOFUISHI_DOOR = new BlockTofuDoor(Material.ROCK,TofuType.ishi);
	public static BlockTofuDoor TOFUMETAL_DOOR = new BlockTofuDoor(Material.IRON,TofuType.metal);
	public static BlockTofuDoor TOFUZUNDA_DOOR = new BlockTofuDoor(Material.WOOD,TofuType.zunda);

	public static Block TOFUKINU_STAIRS = new BlockTofuStairs(KINUTOFU.getDefaultState()).setFragile();
	public static Block TOFUMOMEN_STAIRS = new BlockTofuStairs(MOMENTOFU.getDefaultState());
	public static Block TOFUISHI_STAIRS = new BlockTofuStairs(ISHITOFU.getDefaultState());
	public static Block TOFUMETAL_STAIRS = new BlockTofuStairs(METALTOFU.getDefaultState());
	public static Block TOFUZUNDA_STAIRS = new BlockTofuStairs(TOFUZUNDA.getDefaultState());
	public static Block TOFUISHI_BRICK_STAIRS = new BlockTofuStairs(TOFUISHI_BRICK.getDefaultState());
	public static Block TOFUCHEST = new BlockTofuChest();
	public static Block SALTPAN = new BlockSaltPan().setHardness(0.2F).setCreativeTab(CommonProxy.tab);
	
	public static BlockTofuSlab TOFUKINU_SLAB = new BlockTofuSlab(TofuMaterial.softtofu);
	public static BlockTofuSlab TOFUMOMEN_SLAB = new BlockTofuSlab(TofuMaterial.tofu);
	public static BlockTofuSlab TOFUISHI_SLAB = new BlockTofuSlab(Material.ROCK);
	public static BlockTofuSlab TOFUMETAL_SLAB = new BlockTofuSlab(Material.IRON);
	public static BlockTofuSlab TOFUZUNDA_SLAB = new BlockTofuSlab(TofuMaterial.tofu);
	public static BlockTofuSlab TOFUISHI_BRICK_SLAB = new BlockTofuSlab(Material.ROCK);

	public static Block TOFUBEDROCK = new Block(Material.ROCK).setCreativeTab(CommonProxy.tab).setBlockUnbreakable().setResistance(1000000.0F);
	public static Block TOFUFARMLAND = new BlockTofuFarmLand();
    public static Block TOFUORE_DIAMOND = new BlockTofuOreDiamond();
    public static Block TOFUGEM_ORE = new BlockTofuGemOre();

	public static BlockBarrel MISOBARREL = new BlockMisoBarrel().setDrain(3);
    public static BlockNattoBed NATTO = new BlockNattoBed(new ItemStack(ItemLoader.material, 6, 8), new ItemStack[]{
            new ItemStack(Items.WHEAT, 3), new ItemStack(ItemLoader.soybeans, 3)
    }).setDrain(2);
	public static BlockBarrel DOUBANJIANGBARREL = new BlockDoubanjiangBarrel().setDrain(3);
	public static BlockBarrel MISOTOFUBARREL = new BlockBarrel(new ItemStack(ItemLoader.tofu_food,3,11), new ItemStack[]{
			new ItemStack(ItemLoader.tofu_food,3,1),new ItemStack(ItemLoader.material,3,2)
	}).setDrain(3);
	
	public static BlockYuba YUBA_FLOW = new BlockYuba();
	public BlockLoader(FMLPreInitializationEvent event) {
		SOYMILK_FLUID = SoyMilkFluid.instance;
		FluidRegistry.addBucketForFluid(SOYMILK_FLUID);
		SOYMILK = registerFluidBlock(SOYMILK_FLUID, new BlockSoyMilk(SOYMILK_FLUID), "soymilk");
		SOYMILKHELL_FLUID = SoyMilkHellFluid.instance;
		FluidRegistry.addBucketForFluid(SOYMILKHELL_FLUID);
		SOYMILKHELL = registerFluidBlock(SOYMILKHELL_FLUID, new BlockSoyMilk(SOYMILKHELL_FLUID), "soymilk_hell");

		ZUNDASOYMILK_FLUID = ZundaSoyMilkFluid.instance;
		FluidRegistry.addBucketForFluid(ZUNDASOYMILK_FLUID);
		ZUNDASOYMILK = registerFluidBlock(ZUNDASOYMILK_FLUID, new BlockSoyMilk(ZUNDASOYMILK_FLUID), "zunda_soymilk");

		NIGARI_FLUID = NigariFluid.instance;
		FluidRegistry.addBucketForFluid(NIGARI_FLUID);
		NIGARI = registerFluidBlock(NIGARI_FLUID, new BlockNigari(NIGARI_FLUID), "nigari");
		
		SOYSAUCE_FLUID = SoySauceFluid.instance;
		FluidRegistry.addBucketForFluid(SOYSAUCE_FLUID);
		SOYSAUCE = registerFluidBlock(SOYSAUCE_FLUID, new BlockSoySauce(SOYSAUCE_FLUID), "soysauce");

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
		register(GRILD, new ItemBlock(GRILD), "blocktofugrilled");
		register(TOFUDRIED, new ItemBlock(TOFUDRIED), "blocktofudried");
		register(TOFUZUNDA, new ItemBlock(TOFUZUNDA), "blocktofuzunda");
		register(TOFUISHI_BRICK, new ItemBlock(TOFUISHI_BRICK), "tofuishi_brick");

		register(MISOBARREL, new ItemBlock(MISOBARREL), "barrelmiso");
		register(DOUBANJIANGBARREL, new ItemBlock(DOUBANJIANGBARREL), "barreldoubanjiang");
		register(MISOTOFUBARREL, new ItemBlock(MISOTOFUBARREL), "barrelmisotofu");
		
		register(LEEK, new ItemBlock(LEEK), "blockleek");
//		SOYBEAN_NETHER
		registerNoItem(SOYBEAN, "soybean");
		registerNoItem(SOYBEAN_NETHER, "soybean_nether");
		registerNoItem(SPROUTS, "blocksprouts");
		register(RICECROP, new ItemBlock(RICECROP), "ricecrop");
		register(yubaGrass, new ItemBlock(yubaGrass), "yubagrass");
		register(tofuTerrain, new ItemBlock(tofuTerrain), "tofu_terrain");
		register(zundatofuTerrain, new ItemBlock(zundatofuTerrain), "zundatofu_terrain");
		register(TOFU_SAPLING, new ItemBlock(TOFU_SAPLING), "sapling_tofu");
		register(TOFU_LEAVE, new ItemBlock(TOFU_LEAVE), "leaves_tofu");
		register(TOFU_LOG, new ItemBlock(TOFU_LOG), "tofulog");
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

		register(TOFUKINU_SLAB, new ItemTofuSlab(TOFUKINU_SLAB), "tofuslab_kinu");
		register(TOFUMOMEN_SLAB, new ItemTofuSlab(TOFUMOMEN_SLAB), "tofuslab_momen");
		register(TOFUISHI_SLAB, new ItemTofuSlab(TOFUISHI_SLAB), "tofuslab_ishi");
		register(TOFUMETAL_SLAB, new ItemTofuSlab(TOFUMETAL_SLAB), "tofuslab_metal");
		register(TOFUZUNDA_SLAB, new ItemTofuSlab(TOFUZUNDA_SLAB), "tofuslab_zunda");
		register(TOFUISHI_BRICK_SLAB, new ItemTofuSlab(TOFUISHI_BRICK_SLAB), "tofuslab_ishibrick");

		register(TOFUKINU_TORCH, new ItemBlock(TOFUKINU_TORCH), "tofutorch_kinu");
		register(TOFUMOMEN_TORCH, new ItemBlock(TOFUMOMEN_TORCH), "tofutorch_momen");
		register(TOFUISHI_TORCH, new ItemBlock(TOFUISHI_TORCH), "tofutorch_ishi");
		register(TOFUMETAL_TORCH, new ItemBlock(TOFUMETAL_TORCH), "tofutorch_metal");
		register(TOFUZUNDA_TORCH, new ItemBlock(TOFUZUNDA_TORCH), "tofutorch_zunda");

		register(TOFUCHEST, new ItemBlock(TOFUCHEST), "tofuchest");

		register(TOFUBEDROCK,new ItemBlock(TOFUBEDROCK),"tofubedrock");
		register(TOFUFARMLAND,new ItemBlock(TOFUFARMLAND),"tofu_farmland");
        register(TOFUORE_DIAMOND, new ItemBlock(TOFUORE_DIAMOND), "ore_tofudiamond");
        register(TOFUGEM_ORE, new ItemBlock(TOFUGEM_ORE), "blockoretofu");

        registerNoItem(YUBA_FLOW, "blockyuba");
		registerNoItem(TOFUKINU_DOOR, "tofudoor_kinu");
		registerNoItem(TOFUMOMEN_DOOR, "tofudoor_momen");
		registerNoItem(TOFUISHI_DOOR, "tofudoor_ishi");
		registerNoItem(TOFUMETAL_DOOR, "tofudoor_metal");
		registerNoItem(TOFUZUNDA_DOOR, "tofudoor_zunda");
	}

	private static void register(Block block, Item itemBlock, String string) {
		block.setRegistryName(string);
		block.setUnlocalizedName(TofuMain.MODID+"."+string);

		ForgeRegistries.BLOCKS.register(block);
		if (itemBlock != null) {
			itemBlock.setRegistryName(string);
			itemBlock.setUnlocalizedName(TofuMain.MODID+"."+string);
			ForgeRegistries.ITEMS.register(itemBlock);
		}
		GameData.getBlockItemMap().put(block, itemBlock);
	}

	private static void registerNoItem(Block block, String string) {
		block.setRegistryName(string);
		block.setUnlocalizedName(TofuMain.MODID+"."+string);

		ForgeRegistries.BLOCKS.register(block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(TOFUHELL);
		registerRender(YUBA_FLOW);
		registerRender(MISOBARREL);
		registerRender(DOUBANJIANGBARREL);
		registerRender(MISOTOFUBARREL);
        registerRender(NATTO);
        registerRender(SPROUTS);
		registerRender(tofu_Cake);
		registerRender(TOFUDRIED);
		registerRender(SALTFURNACE);
		registerRender(SALTFURNACE_LIT);
		registerRender(KINUTOFU);
		registerRender(MOMENTOFU);
		registerRender(ISHITOFU);
		registerRender(METALTOFU);
		registerRender(ANNINTOFU);
		registerRender(EGGTOFU);
		registerRender(GRILD);
		registerRender(TOFUZUNDA);
		registerRender(LEEK);
		registerRender(TOFU_SAPLING);
		registerRender(yubaGrass);
		registerRender(tofuTerrain);
		registerRender(zundatofuTerrain);
		registerRender(TOFU_LEAVE);
		registerRender(TOFU_LOG);
		registerRender(APRICOT_SAPLING);
		registerRender(APRICOT_LEAVE);
		registerRender(tofu_PORTAL);
		registerRender(TOFUISHI_BRICK);

		registerRender(TOFUKINU_TORCH);
		registerRender(TOFUMOMEN_TORCH);
		registerRender(TOFUISHI_TORCH);
		registerRender(TOFUMETAL_TORCH);
		registerRender(TOFUZUNDA_TORCH);

		registerRender(TOFUKINU_DOOR);
		registerRender(TOFUMOMEN_DOOR);
		registerRender(TOFUISHI_DOOR);
		registerRender(TOFUMETAL_DOOR);
		registerRender(SALTPAN);
		registerRender(TOFUKINU_STAIRS);
		registerRender(TOFUISHI_BRICK_STAIRS);
		registerRender(TOFUMOMEN_STAIRS);
		registerRender(TOFUISHI_STAIRS);
		registerRender(TOFUMETAL_STAIRS);
		registerRender(TOFUZUNDA_STAIRS);

		registerRender(TOFUKINU_SLAB);
		registerRender(TOFUISHI_BRICK_SLAB);
		registerRender(TOFUMOMEN_SLAB);
		registerRender(TOFUISHI_SLAB);
		registerRender(TOFUMETAL_SLAB);
		registerRender(TOFUZUNDA_SLAB);

		registerRender(TOFUBEDROCK);
		registerRender(TOFUORE_DIAMOND);
        registerRender(TOFUGEM_ORE);

		registerRender(TOFUFARMLAND);
		registerRender(TOFUCHEST);
	}

	public static Block registerFluidBlock(Fluid fluid, Block fluidBlock, String name) {

		fluidBlock.setRegistryName(new ResourceLocation(TofuMain.MODID, name));

		ForgeRegistries.BLOCKS.register(fluidBlock);

		TofuMain.proxy.registerFluidBlockRendering(fluidBlock, name);

		fluid.setBlock(fluidBlock);

		return fluidBlock;

	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block) {
		ModelResourceLocation model = new ModelResourceLocation(TofuMain.MODID + ":" + block.getRegistryName().getResourcePath(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}


	@SideOnly(Side.CLIENT)
	public static void registerCakeRender(Block block, String name) {
		JSON_Creator.genCake(block.getRegistryName().toString().substring(1 + TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block, String name) {
		JSON_Creator.genBlock(block.getRegistryName().toString().substring(1 + TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}

}
