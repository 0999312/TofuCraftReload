package cn.mcmod.tofucraft.event;

import cn.mcmod.tofucraft.TofuConfig;
import cn.mcmod.tofucraft.client.gui.GuiDownloadTofuTerrain;
import cn.mcmod.tofucraft.client.gui.GuiLoadTofuTerrain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TofuClientEventLoader {
    private final Minecraft client = Minecraft.getMinecraft();
    private int lastDimension = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == client.player) {
            lastDimension = event.player.dimension;
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        GuiScreen gui = event.getGui();

        if (event.getGui() instanceof GuiDownloadTerrain && client.player != null) {

            int tofuDimension = TofuConfig.dimensionID;

            if (client.player.dimension == tofuDimension || lastDimension == tofuDimension) {

                if (client.player.dimension == tofuDimension) {
                    event.setGui(new GuiLoadTofuTerrain());
                } else {
                    event.setGui(new GuiDownloadTofuTerrain());
                }
            }

        }
    }
}
