package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.fluid.BlockNigari;
import cn.mcmod.tofucraft.block.fluid.BlockSoyMilk;
import cn.mcmod.tofucraft.block.fluid.NigariFluid;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.block.torch.BlockTofuTorch;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.JSON_Creator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
	public static Block NIGARI;
	public static Fluid NIGARI_FLUID;

	public static Block SALTFURNACE = new BlockSaltFurnace(false).setCreativeTab(CommonProxy.tab);
	public static Block SALTFURNACE_LIT = new BlockSaltFurnace(true);
	public static Block KINUTOFU = new BlockTofu(TofuType.kinu).setFragile().setHardness(0.2F).setResistance(0.2F);
	public static Block MOMENTOFU = new BlockTofu(TofuType.momen).setDrain(3);
	public static Block ISHITOFU = new BlockTofu(TofuType.ishi,Material.ROCK).setDrain(5).setHardness(1.8F).setResistance(9.0F);
	public static Block METALTOFU = new BlockTofu(TofuType.metal,Material.IRON).setHardness(5.0F).setResistance(11.0F);
	public static Block ANNINTOFU = new BlockTofu(TofuType.annin);
	public static Block EGGTOFU = new BlockTofu(TofuType.egg);
	public static Block GRILD = new BlockTofu(TofuType.grilled);

	public static BlockLeek LEEK = new BlockLeek();
	public static BlockSoybean SOYBEAN = new BlockSoybean();
	public static Block tofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
	public static Block zundatofuTerrain = new BlockTofuTerrain(SoundType.CLOTH);
	public static BlockTofuSapling TOFU_SAPLING = new BlockTofuSapling();
	public static BlockTofuLeaves TOFU_LEAVE = new BlockTofuLeaves();
	public static BlockCake tofu_Cake = new BlockTofuCake();
	public static BlockTofuPortal tofu_PORTAL = new BlockTofuPortal();
	public static Block TOFUISHI_BRICK = new Block(Material.ROCK).setHardness(1.82F).setResistance(9.5F);

	public static BlockTofuTorch TOFUKINU_TORCH = new BlockTofuTorch();
	public static BlockTofuTorch TOFUMOMEN_TORCH = new BlockTofuTorch();
	public static BlockTofuTorch TOFUISHI_TORCH = new BlockTofuTorch(SoundType.STONE);
	public static BlockTofuTorch TOFUMETAL_TORCH = new BlockTofuTorch(SoundType.METAL);

	public BlockLoader(FMLPreInitializationEvent event) {
		SOYMILK_FLUID = SoyMilkFluid.instance;
		FluidRegistry.addBucketForFluid(SOYMILK_FLUID);
		SOYMILK = registerFluidBlock(SOYMILK_FLUID, new BlockSoyMilk(SOYMILK_FLUID), "soymilk");
		SOYMILK_FLUID.setTemperature(15);

		NIGARI_FLUID = NigariFluid.instance;
		FluidRegistry.addBucketForFluid(NIGARI_FLUID);
		NIGARI = registerFluidBlock(NIGARI_FLUID, new BlockNigari(NIGARI_FLUID), "nigari");
		NIGARI_FLUID.setTemperature(12);

		register(SALTFURNACE, new ItemBlock(SALTFURNACE), "saltfurnace");
		register(SALTFURNACE_LIT, new ItemBlock(SALTFURNACE_LIT), "saltfurnace_lit");
		register(KINUTOFU, new ItemBlock(KINUTOFU), "blocktofukinu");
		register(MOMENTOFU, new ItemBlock(MOMENTOFU), "blocktofumomen");
		register(ISHITOFU, new ItemBlock(ISHITOFU), "blocktofuishi");
		register(METALTOFU, new ItemBlock(METALTOFU), "blocktofumetal");
		register(ANNINTOFU, new ItemBlock(ANNINTOFU), "blocktofuannin");
		register(EGGTOFU, new ItemBlock(EGGTOFU), "blocktofuegg");
		register(GRILD, new ItemBlock(GRILD), "blocktofugrilled");
		register(TOFUISHI_BRICK, new ItemBlock(TOFUISHI_BRICK), "tofuishi_brick");

		register(LEEK, new ItemBlock(LEEK), "blockleek");
		register(SOYBEAN, new ItemBlock(SOYBEAN), "soybean");
		register(tofuTerrain, new ItemBlock(tofuTerrain), "tofu_terrain");
		register(zundatofuTerrain, new ItemBlock(zundatofuTerrain), "zundatofu_terrain");
		register(TOFU_SAPLING, new ItemBlock(TOFU_SAPLING), "sapling_tofu");
		register(TOFU_LEAVE, new ItemBlock(TOFU_LEAVE), "leaves_tofu");
		
		register(tofu_Cake, new ItemBlock(tofu_Cake), "tofucake");
		register(tofu_PORTAL, new ItemBlock(tofu_PORTAL), "tofuportal");

		register(TOFUKINU_TORCH, new ItemBlock(TOFUKINU_TORCH), "tofutorch_kinu");
		register(TOFUMOMEN_TORCH, new ItemBlock(TOFUMOMEN_TORCH), "tofutorch_momen");
		register(TOFUISHI_TORCH, new ItemBlock(TOFUISHI_TORCH), "tofutorch_ishi");
		register(TOFUMETAL_TORCH, new ItemBlock(TOFUMETAL_TORCH), "tofutorch_metal");
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

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(tofu_Cake);
		registerRender(SALTFURNACE);
		registerRender(SALTFURNACE_LIT);
		registerRender(KINUTOFU);
		registerRender(MOMENTOFU);
		registerRender(ISHITOFU);
		registerRender(METALTOFU);
		registerRender(ANNINTOFU);
		registerRender(EGGTOFU);
		registerRender(GRILD);
		registerRender(LEEK);
		registerRender(TOFU_SAPLING);
		registerRender(tofuTerrain);
		registerRender(zundatofuTerrain);
		registerRender(TOFU_LEAVE);
		registerRender(tofu_PORTAL);
		registerRender(TOFUISHI_BRICK);

		registerRender(TOFUKINU_TORCH);
		registerRender(TOFUMOMEN_TORCH);
		registerRender(TOFUISHI_TORCH);
		registerRender(TOFUMETAL_TORCH);
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
