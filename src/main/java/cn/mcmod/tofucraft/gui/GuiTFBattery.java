package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerTFBattery;
import cn.mcmod.tofucraft.tileentity.tofuenergy.reservoir.TileEntityTofuBattery;
import cn.mcmod_mmf.mmlib.util.ClientUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiTFBattery extends GuiContainer {
    private static final ResourceLocation TXTURE = new ResourceLocation("tofucraft", "textures/gui/tfbattery.png");

    private final InventoryPlayer playerInventory;
    private final TileEntityTofuBattery tileBattery;

    public GuiTFBattery(InventoryPlayer inventory, TileEntityTofuBattery machine) {
        super(new ContainerTFBattery(inventory, machine));
        this.playerInventory = inventory;
        this.tileBattery = machine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = this.tileBattery.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

        this.fontRenderer.drawString(this.tileBattery.getEnergyStored() + "tf", 80, 40, 4210752);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        TextureManager manager = this.mc.getTextureManager();
        manager.bindTexture(TXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
//        int var7;
        if (this.tileBattery.inputTank.getFluid() != null) {
            FluidTank fluidTank = this.tileBattery.inputTank;
            int heightInd = (int) (72 * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
                ClientUtils.drawRepeatedFluidSprite(fluidTank.getFluid(), var5 + 167 - 47 - heightInd, var6 + 54, heightInd, 16f);
            }

        }

    }
}
