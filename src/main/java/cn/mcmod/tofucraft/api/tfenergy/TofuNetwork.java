package cn.mcmod.tofucraft.api.tfenergy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.stream.Collectors;
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

    public TileEntity find(String uid) {
        return reference.get(uid);
    }

    public void register(String uid, TileEntity te) {
        if (!(te instanceof ITofuEnergy)) throw new IllegalArgumentException("Can't register machine which is not Tofu Energy Tile!");
        reference.put(uid, te);
    }


    public Stream<Map.Entry<String, TileEntity>> getTEWithinDim(int dimid) {
        return reference
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getWorld().provider.getDimension() == dimid);
    }

    public Stream<Map.Entry<String, TileEntity>> getTEWithinRadius(TileEntity center, double radius){
        BlockPos pos = center.getPos();
        return getTEWithinDim(center.getWorld().provider.getDimension())
                .filter(entry -> entry.getValue().getPos().getDistance(pos.getX(), pos.getY(), pos.getZ())<=radius);
    }

    public Stream<Map.Entry<String, TileEntity>> getExtractableWithinRadius(TileEntity center, double radius){
        if (!(center instanceof ITofuEnergy)) throw new IllegalArgumentException("The center tile is not able to transfer energy!");
        return getTEWithinRadius(center, radius)
                .filter(entry -> ((ITofuEnergy) entry.getValue()).canDrain(((ITofuEnergy) center).getPriority()));
    }

    public Stream<Map.Entry<String, TileEntity>> getInsertableWithinRadius(TileEntity center, double radius){
        if (!(center instanceof ITofuEnergy)) throw new IllegalArgumentException("The center tile is not able to transfer energy!");
        return getTEWithinRadius(center, radius)
                .filter(entry -> ((ITofuEnergy) entry.getValue()).canReceive(((ITofuEnergy) center).getPriority()));
    }

    public static List<String> toUUIDs(Stream<Map.Entry<String, TileEntity>> map){
        List<String> uids = new ArrayList<>();
        map.forEach(entry -> uids.add(entry.getKey()));
        return uids;
    }

    public static List<TileEntity> toTiles(Stream<Map.Entry<String, TileEntity>> map){
        List<TileEntity> tes = new ArrayList<>();
        map.forEach(entry -> tes.add(entry.getValue()));
        return tes;
    }

    public void unload(String uid) {
        reference.remove(uid);
    }

    public void clear(){
        reference.clear();
    }

    //Clears out the tofu network cache
    @SubscribeEvent
    public static void onUnloadWorld(WorldEvent.Unload event) {
        World world = event.getWorld();
        for (String uid : toUUIDs(Instance.getTEWithinDim(world.provider.getDimension()))){
            Instance.unload(uid);
        }
    }
}
