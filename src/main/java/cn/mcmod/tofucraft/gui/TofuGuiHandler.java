package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerSaltFurnace;
import cn.mcmod.tofucraft.inventory.ContainerTFStorage;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
import cn.mcmod.tofucraft.tileentity.tofuenergy.sender.TileEntityTFStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TofuGuiHandler implements IGuiHandler {
    public static final int ID_SALTFURNACE_Gui = 0;
    public static final int ID_STORAGE_MACHINE_GUI = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ID_SALTFURNACE_Gui) {
            TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof TileEntitySaltFurnace) {
                return new ContainerSaltFurnace(player.inventory,(TileEntitySaltFurnace)entity);
            }
        }

        if (ID == ID_STORAGE_MACHINE_GUI) {
            TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof TileEntityTFStorage) {
                return new ContainerTFStorage(player.inventory, (TileEntityTFStorage) entity);
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

        if (ID == ID_STORAGE_MACHINE_GUI) {
            TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof TileEntityTFStorage) {
                return new GuiTFStorage(player.inventory, (TileEntityTFStorage) entity);
            }
        }

        return null;
    }
}