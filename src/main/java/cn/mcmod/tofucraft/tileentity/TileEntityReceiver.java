package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.base.tileentity.TileEntityEnergyBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;

public class TileEntityReceiver extends TileEntityEnergyBase implements ITickable {
    public TileEntityReceiver() {
        super(2000);
    }

    @Override
    public void update() {
        if (world.isRemote) return;
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        return new SPacketUpdateTileEntity(pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
    }
}
