package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerTFStorage;
import cn.mcmod.tofucraft.tileentity.TileEntityTFStorage;
import cn.mcmod.tofucraft.util.ClientUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public class GuiTFStorage extends GuiContainer {
    private static final ResourceLocation TXTURE = new ResourceLocation("tofucraft", "textures/gui/storage_machine.png");
    /**
     * The player inventory bound to this GUI.
     */
    private final InventoryPlayer playerInventory;
    private final TileEntityTFStorage tileFurnace;

    public GuiTFStorage(InventoryPlayer inventory, TileEntityTFStorage storageMachine) {
        super(new ContainerTFStorage(inventory, storageMachine));
        this.playerInventory = inventory;
        this.tileFurnace = storageMachine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

        this.fontRenderer.drawString(this.tileFurnace.tfAmount + "tf", 80, 40, 4210752);
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
        int var7;

        // Progress arrow
        var7 = this.tileFurnace.getProgressScaled(24);
        this.drawTexturedModalRect(var5 + 46, var6 + 34, 176, 14, var7 + 1, 16);


        if (this.tileFurnace.getTank().getFluid() != null) {
            FluidTank fluidTank = this.tileFurnace.getTank();
            int heightInd = (int) (72 * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
                ClientUtils.drawRepeatedFluidSprite(fluidTank.getFluid(), var5 + 167 - 47 - heightInd, var6 + 54, heightInd, 16f);
            }

        }
    }
}
