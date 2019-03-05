package cn.mcmod.tofucraft.advancements;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TofuAdvancements {
    public static void grantAdvancement(EntityPlayer player, String advancementName) {
        if (!(player instanceof EntityPlayerMP))
            return;

        AdvancementManager manager = player.world.getMinecraftServer().getAdvancementManager();
        Advancement advancement = manager.getAdvancement(new ResourceLocation(TofuMain.MODID, "tofucraft/" + advancementName));
        if (advancement == null)
            return;

        ((EntityPlayerMP) player).getAdvancements().grantCriterion(advancement, "done");
    }
}
