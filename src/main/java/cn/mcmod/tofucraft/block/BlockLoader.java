package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.fluid.BlockSoyMilk;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.util.JSON_Creator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
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

	public static Block KINUTOFU = new BlockTofu();
	public static Block MOMENTOFU = new BlockTofu();
	public static Block ISHITOFU = new BlockTofu(Material.ROCK).setHardness(1.8F).setResistance(9.0F);
	public static Block METALTOFU = new BlockTofu(Material.IRON).setHardness(5.0F).setResistance(11.0F);

	public BlockLoader(FMLPreInitializationEvent event) {
		SOYMILK_FLUID = SoyMilkFluid.instance;

		FluidRegistry.addBucketForFluid(SOYMILK_FLUID);

		SOYMILK = registerFluidBlock(SOYMILK_FLUID, new BlockSoyMilk(SOYMILK_FLUID), "soymilk");

		SOYMILK_FLUID.setTemperature(15);

		register(KINUTOFU, new ItemBlock(KINUTOFU), "blocktofukinu");
		register(MOMENTOFU, new ItemBlock(MOMENTOFU), "blocktofumomen");
		register(ISHITOFU, new ItemBlock(ISHITOFU), "blocktofuishi");
		register(METALTOFU, new ItemBlock(METALTOFU), "blocktofumetal");
	}

	private static void register(Block block, Item itemBlock, String string) {
		block.setRegistryName(string);
		block.setUnlocalizedName(TofuMain.MODID+"."+string);
		itemBlock.setRegistryName(string);
		itemBlock.setUnlocalizedName(TofuMain.MODID+"."+string);

		ForgeRegistries.BLOCKS.register(block);
		if (itemBlock != null) {
			ForgeRegistries.ITEMS.register(itemBlock);
		}
		GameData.getBlockItemMap().put(block, itemBlock);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(KINUTOFU);
		registerRender(MOMENTOFU);
		registerRender(ISHITOFU);
		registerRender(METALTOFU);
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
		JSON_Creator.genCake(block.getRegistryName().toString().substring(6 + TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block, String name) {
		JSON_Creator.genBlock(block.getRegistryName().toString().substring(6 + TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}

}
