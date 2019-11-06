package cn.mcmod.tofucraft.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

import cn.mcmod.tofucraft.inventory.ContainerTFAggregator;
import cn.mcmod.tofucraft.tileentity.tofuenergy.worker.TileEntitySoymilkAggregator;


public class GuiTFAggreagator extends GuiContainer {
    private static final ResourceLocation TXTURE = new ResourceLocation("tofucraft", "textures/gui/aggregator_machine.png");
    private final TileEntitySoymilkAggregator tileFurnace;

    public GuiTFAggreagator(InventoryPlayer inventory, TileEntitySoymilkAggregator storageMachine) {
        super(new ContainerTFAggregator(inventory, storageMachine));
        this.tileFurnace = storageMachine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.tileFurnace.getEnergyStored() + "tf", 125, 55, 0x000000);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        int var7,var8;
        var8 = this.getEnergyScaled(55);
        this.drawTexturedModalRect(var5 + 61, var6 + 58, 176, 31, var8 , 8);
        
        // Progress arrow
        var7 = this.getProgressScaled(55);
        this.drawTexturedModalRect(var5 + 62, var6 + 16, 176, 31, var7, 8);
        
        if (this.tileFurnace.getTank().getFluid() != null) {
            FluidTank fluidTank = this.tileFurnace.getTank();
            int heightInd = (int) (55 * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
            	this.drawTexturedModalRect(var5 + 18, var6 + 66 - heightInd, 176, 39, 9, heightInd);
            }

        }
    }
    
    private int getEnergyScaled(int pixels)
    {
        int i = this.tileFurnace.getEnergyStored();
        int j = this.tileFurnace.getMaxEnergyStored();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
    private int getProgressScaled(int pixels)
    {
        int i = this.tileFurnace.getField(0);
        int j = this.tileFurnace.getField(1);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
