package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerSaltFurnace;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiSaltFurnace extends GuiContainer {
    private static final ResourceLocation TXTURE = new ResourceLocation("tofucraft", "textures/gui/saltfurnace.png");
    private static final ResourceLocation NIGARI = new ResourceLocation("tofucraft", "textures/blocks/nigari.png");

    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntitySaltFurnace tileFurnace;

    public GuiSaltFurnace(InventoryPlayer playerInv, TileEntitySaltFurnace furnaceInv)
    {
        super(new ContainerSaltFurnace(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = furnaceInv;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.isPointInRegion(114, 29, 6, 35, mouseX, mouseY))
        {
//TODO        	FluidTank tank = this.tileFurnace.getNigariTank();
//        	StringBuilder amount = new StringBuilder(TextFormatting.GRAY + "");
//        	amount.append(tank.getFluidAmount());
//        	amount.append("mb/");
//        	amount.append(tank.getCapacity());
//        	amount.append("mb");
            ArrayList<String> string = Lists.newArrayList(I18n.format("fluid.tofucraft:nigari"));
//TODO        	string.add(amount.toString());
            this.drawHoveringText(string, mouseX, mouseY, fontRenderer);
        }
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

        // Flame
        if (this.tileFurnace.isBurning()) {
            var7 = this.tileFurnace.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 23, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        // Progress arrow
        var7 = this.tileFurnace.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 46, var6 + 34, 176, 14, var7 + 1, 16);

        // Cauldron
        var7 = this.tileFurnace.getCauldronStatus();
        if (var7 < 0) {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 176, 31, 16, 16);
        } else if (var7 == 1) {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 192, 31, 16, 16);
        } else if (var7 >= 2) {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 208, 31, 16, 16);
        }

        // Nigari Gauge
        if (this.tileFurnace.getNigariTank().getFluid() != null) {
            FluidTank fluidTank = this.tileFurnace.getNigariTank();
            int heightInd = (int) (35 * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0) {
                manager.bindTexture(NIGARI);
                int time = (int)this.mc.world.getWorldTime()%64/2;
                GuiSaltFurnace.drawModalRectWithCustomSizedTexture(var5 + 114, var6 + 64 - heightInd, 0.0f, 16*time, 6, heightInd, 16, 512);
            }
        }
    }
}
