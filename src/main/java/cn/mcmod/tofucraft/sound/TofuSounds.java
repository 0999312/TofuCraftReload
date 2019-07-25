package cn.mcmod.tofucraft.sound;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TofuMain.MODID)
public final class TofuSounds {
    public static final SoundEvent TOFUNIAN_YES = createEvent("mob.tofunian.yes");
    public static final SoundEvent TOFUNIAN_AMBIENT = createEvent("mob.tofunian.ambient");

    private static SoundEvent createEvent(String name) {

        SoundEvent sound = new SoundEvent(new ResourceLocation(TofuMain.MODID, name));

        sound.setRegistryName(new ResourceLocation(TofuMain.MODID, name));

        return sound;
    }


    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        evt.getRegistry().register(TOFUNIAN_YES);
        evt.getRegistry().register(TOFUNIAN_AMBIENT);
    }

    private TofuSounds() {

    }
}