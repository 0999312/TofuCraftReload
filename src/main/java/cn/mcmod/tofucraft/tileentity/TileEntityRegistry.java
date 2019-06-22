package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TileEntityRenderHelper;
import cn.mcmod.tofucraft.client.render.tileentity.RenderSaltPan;
import cn.mcmod.tofucraft.client.render.tileentity.RenderTofuChest;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRegistry {

    public static void init() {
        registerTileEntity(TileEntitySaltFurnace.class, "saltfurnace");
        registerTileEntity(TileEntityTofuChest.class, "tofuchest");
        registerTileEntity(TileEntitySaltPan.class, "saltpan");
    }
    @SideOnly(Side.CLIENT)
    public static void render() {
    	TileEntityRenderHelper TESR = new TileEntityRenderHelper();
        Item.getItemFromBlock(BlockLoader.TOFUCHEST).setTileEntityItemStackRenderer(TESR);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTofuChest.class, new RenderTofuChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySaltPan.class, new RenderSaltPan());
        
    }

    private static void registerTileEntity(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, new ResourceLocation(TofuMain.MODID, baseName));
    }

}