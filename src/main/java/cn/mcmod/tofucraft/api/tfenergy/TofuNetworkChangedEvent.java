package cn.mcmod.tofucraft.api.tfenergy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TofuNetworkChangedEvent extends Event {

    private String uuid;
    private TileEntity te;
    private boolean isSystem; //This tells the event if the post is caused by the game mechanism itself.

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public boolean hasResult() {
        return false;
    }

    public String getUUID() {
        return uuid;
    }

    public TileEntity getTE() {
        return te;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public TofuNetworkChangedEvent(String uuid, TileEntity te, boolean isSystem){
        this.uuid = uuid;
        this.te = te;
        this.isSystem = isSystem;
    }

    public static class NetworkLoaded extends TofuNetworkChangedEvent{

        public NetworkLoaded(String uuid, TileEntity te, boolean isSystem) {
            super(uuid, te, isSystem);
        }
    }

    public static class NetworkRemoved extends TofuNetworkChangedEvent{

        public NetworkRemoved(String uuid, TileEntity te, boolean isSystem) {
            super(uuid, te, isSystem);
        }
    }

    public static class NetworkCleared extends TofuNetworkChangedEvent{

        public NetworkCleared(boolean isSystem) {
            super(null, null, isSystem);
        }
    }
}
