package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TileEntityRenderHelper;
import cn.mcmod.tofucraft.client.render.tileentity.RenderTofuChest;
import net.minecraft.block.Block;
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
    }
    @SideOnly(Side.CLIENT)
    public static void render() {
    	TileEntityRenderHelper TEISR = new TileEntityRenderHelper();
        Item.getItemFromBlock(BlockLoader.TOFUCHEST).setTileEntityItemStackRenderer(TEISR);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTofuChest.class, new RenderTofuChest());
    }


    private static void registerTileEntity(Class<? extends TileEntity> cls, String baseName) {

        GameRegistry.registerTileEntity(cls, new ResourceLocation(TofuMain.MODID, baseName));

    }

    private static Item getItem(final Block block) {
        return Item.getItemFromBlock(block);
    }

}