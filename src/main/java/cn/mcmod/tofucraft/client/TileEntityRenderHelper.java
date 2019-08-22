package cn.mcmod.tofucraft.client;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.tileentity.TileEntityTofuChest;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderHelper extends TileEntityItemStackRenderer {
    public static final ResourceLocation SHIELD_ISHI_TEXTURE = new ResourceLocation(TofuMain.MODID, "textures/entity/tofuishi_shield.png");
    public static final ResourceLocation SHIELD_METAL_TEXTURE = new ResourceLocation(TofuMain.MODID, "textures/entity/tofumetal_shield.png");

    private TileEntityTofuChest chestRender = new TileEntityTofuChest();
    private final ModelShield modelShield = new ModelShield();

    @Override
    public void renderByItem(ItemStack itemStack) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        Item item = itemStack.getItem();
        if (item == ItemLoader.tofumetal_shield) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_METAL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            this.modelShield.render();
            GlStateManager.popMatrix();
        } else if (item == ItemLoader.tofuishi_shield) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_ISHI_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            this.modelShield.render();
            GlStateManager.popMatrix();
        } else if (block == BlockLoader.TOFUCHEST) {
            TileEntityRendererDispatcher.instance.render(this.chestRender, 0.0D, 0.0D, 0.0D, 0.0F);
        } else {
            super.renderByItem(itemStack);
        }

    }

}