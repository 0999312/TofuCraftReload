package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    private TileEntityRegistry() {
    }


    public static void init() {
        registerTileEntity(TileEntitySaltFurnace.class, "saltfurnace");
    }

    public static void render() {
    }


    private static void registerTileEntity(Class<? extends TileEntity> cls, String baseName) {

        GameRegistry.registerTileEntity(cls, new ResourceLocation(TofuMain.MODID, baseName));

    }

    private static Item getItem(final Block block) {
        return Item.getItemFromBlock(block);
    }

}