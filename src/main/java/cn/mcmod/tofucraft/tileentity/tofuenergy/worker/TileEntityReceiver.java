package cn.mcmod.tofucraft.tileentity.tofuenergy.worker;

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
        if (this.world.isRemote) return;
        drain(1, false);
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
