package cn.mcmod.tofucraft.client.gui;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.util.PanoramaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

@SideOnly(Side.CLIENT)
public class GuiDownloadTofuTerrain extends GuiDownloadTerrain {
    private static int panoramaTimer;

    private final DynamicTexture viewportTexture;
    private final ResourceLocation panoramaBackground;
    private final long prevTime;

    public PanoramaUtils currentPanoramaPaths;

    public GuiDownloadTofuTerrain() {
        super();
        this.mc = FMLClientHandler.instance().getClient();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.panoramaBackground = mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
        this.prevTime = Minecraft.getSystemTime();
    }

    public PanoramaUtils getPanoramaPaths() {
        ResourceLocation[] paths = new ResourceLocation[6];

        for (int j = 0; j < paths.length; ++j) {
            paths[j] = new ResourceLocation(TofuMain.MODID, String.format("textures/gui/panorama/panorama_%d.png", j));
        }

        if (currentPanoramaPaths == null) {
            currentPanoramaPaths = new PanoramaUtils(paths[0], paths[1], paths[2], paths[3], paths[4], paths[5]);
        }


        return currentPanoramaPaths;
    }

    private void drawPanorama(float ticks) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        byte b0 = 8;

        for (int k = 0; k < b0 * b0; ++k) {
            GlStateManager.pushMatrix();
            float f1 = ((float) (k % b0) / (float) b0 - 0.5F) / 64.0F;
            float f2 = ((float) (k / b0) / (float) b0 - 0.5F) / 64.0F;
            float f3 = 0.0F;
            GlStateManager.translate(f1, f2, f3);
            GlStateManager.rotate(MathHelper.sin((panoramaTimer + ticks) / 400.0F) * 15.0F + 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-(panoramaTimer + ticks) * 0.08F, 0.0F, 1.0F, 0.0F);

            for (int l = 0; l < 6; ++l) {
                GlStateManager.pushMatrix();

                switch (l) {
                    case 1:
                        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 2:
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 3:
                        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 4:
                        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                        break;
                    case 5:
                        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                        break;
                }

                mc.getTextureManager().bindTexture(getPanoramaPaths().getPath(l));
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int i = 255 / (k + 1);
                buffer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, i).endVertex();
                buffer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, i).endVertex();
                buffer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, i).endVertex();
                buffer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, i).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        buffer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float ticks) {
        mc.getTextureManager().bindTexture(panoramaBackground);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GlStateManager.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        byte b0 = 3;

        for (int i = 0; i < b0; ++i) {
            float f = 1.0F / (i + 1);
            int j = width;
            int k = height;
            float f1 = (i - b0 / 2) / 256.0F;
            buffer.pos(j, k, 0.0D).tex(0.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            buffer.pos(j, 0.0D, 0.0D).tex(1.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            buffer.pos(0.0D, 0.0D, 0.0D).tex(1.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            buffer.pos(0.0D, k, 0.0D).tex(0.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(float ticks) {
        mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        drawPanorama(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        rotateAndBlurSkybox(ticks);
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
        float f1 = width > height ? 120.0F / width : 120.0F / height;
        float f2 = height * f1 / 256.0F;
        float f3 = width * f1 / 256.0F;
        int k = width;
        int l = height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0.0D, l, 0.0D).tex(0.5F - f2, 0.5F + f3).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.pos(k, l, 0.0D).tex(0.5F - f2, 0.5F - f3).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.pos(k, 0.0D, 0.0D).tex(0.5F + f2, 0.5F - f3).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        buffer.pos(0.0D, 0.0D, 0.0D).tex(0.5F + f2, 0.5F + f3).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        long time = Minecraft.getSystemTime() - prevTime;

        if (getPanoramaPaths() != null) {
            if (time > 200L) {
                ++panoramaTimer;
            }

            GlStateManager.disableAlpha();
            renderSkybox(partialTicks);
            GlStateManager.enableAlpha();
        } else {
            drawBackground(0);
        }

        if (time > 500L) {
            drawCenteredString(fontRenderer, getInfoText(), width / 2, height / 2 + 40, 0xFFFFFF);
        }

        if (time > 2000L) {
            drawCenteredString(fontRenderer, getSubText(), width / 2, height / 2 + 65, 0xCCCCCC);
        }
    }

    public String getInfoText() {
        return I18n.format("multiplayer.downloadingTerrain");
    }

    public String getSubText() {
        return I18n.format("tofucraft.terrain.wait");
    }
}