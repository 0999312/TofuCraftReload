package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerSaltFurnace;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TofuGuiHandler implements IGuiHandler {
    public static final int ID_SALTFURNACE_Gui = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ID_SALTFURNACE_Gui) {
            TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof TileEntitySaltFurnace) {
                return new ContainerSaltFurnace(player.inventory,(TileEntitySaltFurnace)entity);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ID_SALTFURNACE_Gui) {
            TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof TileEntitySaltFurnace) {
                return new GuiSaltFurnace(player.inventory,(TileEntitySaltFurnace)entity);
            }
        }

        return null;
    }
}