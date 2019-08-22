package cn.mcmod.tofucraft.api.tfenergy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Mod.EventBusSubscriber
public class TofuNetwork {

    /*
     * Feature:
     * 1. Maintain a hashmap of all TofuEnergyStorageTileEntity(TESTE)
     * 2. Provides a set of helper methods to access TESTE.
     *
     * Tofu Network rules:
     * 1. Every tile is registered to the network, no matter what it is.
     *
     * 2. Tiles have what so-called 'priority', which means how will they extract or receive
     *    energy, the higher ones are more likely to send out energy to the lower.
     *
     * 3. Machines are not able to radiate energy themselves, and not likely the production
     *    ones will too. The machines which should give out energy needs a antenna to act as
     *    a medium to radiate energy. But actually the working part is only the machine.
     *
     * 4. The consumer should be passive, as the network load will be much higher if every
     *    machine tried to access the map, the antenna should distribute the energy to other
     *    machines.
     * */

    public static final TofuNetwork Instance = new TofuNetwork();

    private HashMap<String, TileEntity> reference = new HashMap<>();

    public static List<String> toUUIDs(Stream<Map.Entry<String, TileEntity>> map) {
        List<String> uids = new ArrayList<>();
        map.forEach(entry -> uids.add(entry.getKey()));
        return uids;
    }

    public static List<TileEntity> toTiles(Stream<Map.Entry<String, TileEntity>> map) {
        List<TileEntity> tes = new ArrayList<>();
        map.forEach(entry -> tes.add(entry.getValue()));
        return tes;
    }

    //Clears out the tofu network cache
    @SubscribeEvent
    public static void onUnloadWorld(WorldEvent.Unload event) {
        World world = event.getWorld();
        for (String uid : toUUIDs(Instance.getTEWithinDim(world.provider.getDimension()))) {
            //It is a world unload, so isSystem is here to prevent bugs from misdetailed event.
            Instance.unload(uid, true);
        }
    }

    public TileEntity find(String uid) {
        return reference.get(uid);
    }

    public void register(String uid, TileEntity te) {
        register(uid, te, false);
    }

    public void register(String uid, TileEntity te, boolean isSystem) {
        if (!(te instanceof ITofuEnergy))
            throw new IllegalArgumentException("Can't register machine which is not Tofu Energy Tile!");
        reference.put(uid, te);
        MinecraftForge.EVENT_BUS.post(new TofuNetworkChangedEvent.NetworkLoaded(uid, te, isSystem));
    }

    public HashMap<String, TileEntity> getReference() {
        return reference;
    }

    public Stream<Map.Entry<String, TileEntity>> getTEWithinDim(int dimid) {
        return reference
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getWorld().provider.getDimension() == dimid);
    }

    public Stream<Map.Entry<String, TileEntity>> getTEWithinRadius(TileEntity center, double radius) {
        BlockPos pos = center.getPos();
        return getTEWithinDim(center.getWorld().provider.getDimension())
                .filter(entry -> entry.getValue().getPos().getDistance(pos.getX(), pos.getY(), pos.getZ()) <= radius);
    }

    public Stream<Map.Entry<String, TileEntity>> getExtractableWithinRadius(TileEntity center, double radius) {
        if (!(center instanceof ITofuEnergy))
            throw new IllegalArgumentException("The center tile is not able to transfer energy!");
        return getTEWithinRadius(center, radius)
                .filter(entry -> ((ITofuEnergy) entry.getValue()).canDrain(center) && ((ITofuEnergy) center).canReceive(entry.getValue()));
    }

    public Stream<Map.Entry<String, TileEntity>> getInsertableWithinRadius(TileEntity center, double radius) {
        if (!(center instanceof ITofuEnergy))
            throw new IllegalArgumentException("The center tile is not able to transfer energy!");
        return getTEWithinRadius(center, radius)
                .filter(entry -> ((ITofuEnergy) entry.getValue()).canReceive(center) && ((ITofuEnergy) center).canDrain(entry.getValue()));
    }

    public void unload(String uid) {
        unload(uid, false);
    }

    public void unload(String uid, boolean isSystem) {
        reference.remove(uid);
        MinecraftForge.EVENT_BUS.post(new TofuNetworkChangedEvent.NetworkRemoved(uid, reference.get(uid), isSystem));
    }

    public void clear() {
        clear(false);
    }

    public void clear(boolean isSystem) {
        reference.clear();
        MinecraftForge.EVENT_BUS.post(new TofuNetworkChangedEvent.NetworkCleared(isSystem));
    }
}
