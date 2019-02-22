package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.util.JSON_Creator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

public class BlockLoader {
	
	public BlockLoader(FMLPreInitializationEvent event) {
		
	}
    private static void register(Block block,Item itemBlock)
    {
    	ForgeRegistries.BLOCKS.register(block);
    	if(itemBlock!=null){
        ForgeRegistries.ITEMS.register(itemBlock);
        }
    	GameData.getBlockItemMap().put(block, itemBlock);
    }

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		
	}
	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block) {
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(),"inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerCakeRender(Block block,String name) {
		JSON_Creator.genCake(block.getRegistryName().toString().substring(6+TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(),"inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block,String name) {
		JSON_Creator.genBlock(block.getRegistryName().toString().substring(6+TofuMain.MODID.length()), name, "json_create");
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(),"inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
	}
	
}
