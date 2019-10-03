package cn.mcmod.tofucraft.base.tileentity;

import cn.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetworkChangedEvent;
import cn.mcmod.tofucraft.base.block.IAnntena;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public abstract class TileEntitySenderBase extends TileEntityEnergyBase implements ITickable {

    /*
     * Comment:
     * This is the base of a TESTE which will send energy 'through' the antenna.
     * They are not able to receive energy by default, but there are some exceptions, for example the battery box.
     * */

    protected List<TileEntity> cache = new ArrayList<>();
    protected boolean isCached = false;

    private Block antenna;

    public TileEntitySenderBase(int energyMax) {
        super(energyMax);
    }

    //Notify all the registered TileEntitySenderBase instance on network change
    @SubscribeEvent
    public static void onLoaded(TofuNetworkChangedEvent.NetworkLoaded event) {
        List<TileEntity> tes = TofuNetwork.toTiles(
                TofuNetwork.Instance.getReference()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() instanceof TileEntitySenderBase &&
                                ((TileEntitySenderBase) entry.getValue()).isValid()));
        tes.forEach(te -> ((TileEntitySenderBase) te).onCache());

    }

    @SubscribeEvent
    public static void onRemoved(TofuNetworkChangedEvent.NetworkRemoved event) {
        List<TileEntity> tes = TofuNetwork.toTiles(
                TofuNetwork.Instance.getReference()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() instanceof TileEntitySenderBase &&
                                ((TileEntitySenderBase) entry.getValue()).isValid()));
        tes.forEach(te -> ((TileEntitySenderBase) te).onCache());
    }

    @Override
    public void update() {
        if (!world.isRemote && this.energy > 0) {
            antenna = world.getBlockState(pos.up()).getBlock();
            if (isValid()) {
                if (!isCached) onCache();
                if (cache.size() > 0) {
                    int packSize = Math.max(Math.min(getTransferPower(), this.energy) / cache.size(), 1);
                    cache.forEach(te -> this.drain(((ITofuEnergy) te).receive(Math.min(packSize, this.energy), false), false));
                isCached = true;
                }
            } else {
                cache.clear();
                isCached = false;
            }
        }
    }

    public boolean isValid() {
        return antenna instanceof IAnntena;
    }

    //The onCache decides what TileEntities will be cached into the function and be send energy to.
    public void onCache() {
        cache = TofuNetwork.toTiles(TofuNetwork.Instance.getInsertableWithinRadius(this, ((IAnntena) antenna).getRadius(pos.up(), world)));
    }

    public int getTransferPower() {
        return isValid() ? ((IAnntena) world.getBlockState(pos.up()).getBlock()).getPower(pos.up(), world) : 0;
    }

    public double getRadius(){
        return ((IAnntena) antenna).getRadius(pos, world);
    }

    @Override
    public boolean canDrain(TileEntity to) {
        return true;
    }

    @Override
    public boolean canReceive(TileEntity from) {
        return false;
    }
}
