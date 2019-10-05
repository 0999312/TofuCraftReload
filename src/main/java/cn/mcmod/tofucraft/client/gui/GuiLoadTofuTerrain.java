package cn.mcmod.tofucraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;


@SideOnly(Side.CLIENT)
public class GuiLoadTofuTerrain extends GuiDownloadTofuTerrain {
    private boolean loading;
    private int progress;

    private boolean renderWorker;

    @Override
    public String getInfoText() {
        return I18n.format("tofucraft.terrain.load");
    }

    public boolean isTerrainLoaded() {
        if (mc.getRenderViewEntity() == null) {
            return true;
        }

        if (!mc.getRenderViewEntity().addedToChunk) {
            renderWorker = true;
        } else if (renderWorker) {
            return true;
        }

        return false;
    }

    @Override
    public void updateScreen() {
        if (!isTerrainLoaded()) {
            loading = renderWorker;
        } else if (loading) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }

        if (++progress > 400) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }

        super.updateScreen();
    }

    @Override
    protected void keyTyped(char c, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
    }
}
