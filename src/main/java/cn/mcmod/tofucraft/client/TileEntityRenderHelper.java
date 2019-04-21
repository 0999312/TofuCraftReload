package cn.mcmod.tofucraft.client;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.tileentity.TileEntityTofuChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderHelper extends TileEntityItemStackRenderer {

    private TileEntityTofuChest chestRender = new TileEntityTofuChest();

    @Override
    public void renderByItem(ItemStack itemStack) {

        Block block = Block.getBlockFromItem(itemStack.getItem());
        Item item = itemStack.getItem();
        if (block == BlockLoader.TOFUCHEST) {
            TileEntityRendererDispatcher.instance.render(this.chestRender, 0.0D, 0.0D, 0.0D, 0.0F);
        } else {
            super.renderByItem(itemStack);
        }

    }

}