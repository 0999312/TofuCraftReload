package cn.mcmod.tofucraft.gui;

import cn.mcmod.tofucraft.inventory.ContainerSaltFurnace;
import cn.mcmod.tofucraft.inventory.ContainerTFBattery;
import cn.mcmod.tofucraft.inventory.ContainerTFCompressor;
import cn.mcmod.tofucraft.inventory.ContainerTFCrasher;
import cn.mcmod.tofucraft.inventory.ContainerTFOven;
import cn.mcmod.tofucraft.inventory.ContainerTFStorage;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
import cn.mcmod.tofucraft.tileentity.tofuenergy.reservoir.TileEntityTofuBattery;
import cn.mcmod.tofucraft.tileentity.tofuenergy.sender.TileEntityTFStorage;
import cn.mcmod.tofucraft.tileentity.tofuenergy.worker.TileEntityTFCompressor;
import cn.mcmod.tofucraft.tileentity.tofuenergy.worker.TileEntityTFCrasher;
import cn.mcmod.tofucraft.tileentity.tofuenergy.worker.TileEntityTFOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TofuGuiHandler implements IGuiHandler {
    public static final int ID_SALTFURNACE_Gui = 0;
    public static final int ID_STORAGE_MACHINE_GUI = 1;
    public static final int ID_BATTERY_GUI = 2;
    public static final int ID_TFOVEN_GUI = 3;
    public static final int ID_TFCRASHER_GUI = 4;
    public static final int ID_TFCOMPRESSOR_GUI = 5;
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
			case ID_SALTFURNACE_Gui:
	            if (entity instanceof TileEntitySaltFurnace) {
	                return new ContainerSaltFurnace(player.inventory,(TileEntitySaltFurnace)entity);
	            }
				break;
			case ID_STORAGE_MACHINE_GUI:
	            if (entity instanceof TileEntityTFStorage) {
	                return new ContainerTFStorage(player.inventory, (TileEntityTFStorage) entity);
	            }
				break;
			case ID_BATTERY_GUI:
	            if (entity instanceof TileEntityTofuBattery) {
	                return new ContainerTFBattery(player.inventory, (TileEntityTofuBattery) entity);
	            }
				break;
			case ID_TFOVEN_GUI:
	            if (entity instanceof TileEntityTFOven) {
	                return new ContainerTFOven(player.inventory, (TileEntityTFOven) entity);
	            }
				break;
			case ID_TFCRASHER_GUI:
	            if (entity instanceof TileEntityTFCrasher) {
	                return new ContainerTFCrasher(player.inventory, (TileEntityTFCrasher) entity);
	            }
				break;
			case ID_TFCOMPRESSOR_GUI:
	            if (entity instanceof TileEntityTFCompressor) {
	                return new ContainerTFCompressor(player.inventory, (TileEntityTFCompressor) entity);
	            }
				break;
			default:
				break;
		}
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
			case ID_SALTFURNACE_Gui:
	            if (entity instanceof TileEntitySaltFurnace) {
	                return new GuiSaltFurnace(player.inventory,(TileEntitySaltFurnace)entity);
	            }
				break;
			case ID_STORAGE_MACHINE_GUI:
	            if (entity instanceof TileEntityTFStorage) {
	                return new GuiTFStorage(player.inventory, (TileEntityTFStorage) entity);
	            }
				break;
			case ID_BATTERY_GUI:
	            if (entity instanceof TileEntityTofuBattery) {
	                return new GuiTFBattery(player.inventory, (TileEntityTofuBattery) entity);
	            }
				break;
			case ID_TFOVEN_GUI:
	            if (entity instanceof TileEntityTFOven) {
	                return new GuiTFOven(player.inventory, (TileEntityTFOven) entity);
	            }
				break;
			case ID_TFCRASHER_GUI:
	            if (entity instanceof TileEntityTFCrasher) {
	                return new GuiTFCrasher(player.inventory, (TileEntityTFCrasher) entity);
	            }
				break;
			case ID_TFCOMPRESSOR_GUI:
	            if (entity instanceof TileEntityTFCompressor) {
	                return new GuiTFCompressor(player.inventory, (TileEntityTFCompressor) entity);
	            }
				break;
			default:
				break;
		}
        return null;
    }
}