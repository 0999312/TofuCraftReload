package cn.mcmod.tofucraft;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.entity.TofuEntityRegister;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	 	@Override
	    public void preInit(FMLPreInitializationEvent event)
	    {
	        super.preInit(event);
			BlockLoader.registerRenders();
	        ItemLoader.registerRenders();
			TofuEntityRegister.entityRender();
	    }

	    @Override
	    public void init(FMLInitializationEvent event)
	    {
	        super.init(event);
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event)
	    {
	        super.postInit(event);
	    }

	@Override
	public void registerFluidBlockRendering(Block block, String name) {

		final ModelResourceLocation fluidLocation = new ModelResourceLocation(TofuMain.MODID.toLowerCase() + ":fluids", name);

		// use a custom state mapper which will ignore the LEVEL property
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

				return fluidLocation;

			}
		});
	}
}
