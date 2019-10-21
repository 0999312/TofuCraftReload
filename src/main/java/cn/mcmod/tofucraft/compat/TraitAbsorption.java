package cn.mcmod.tofucraft.compat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitAbsorption extends AbstractTrait {
    public TraitAbsorption() {
        super("absorption", TextFormatting.AQUA);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        super.onHit(tool, player, target, damage, isCritical);
        if (player instanceof EntityPlayer && player.world.rand.nextFloat() < 0.4F) {
            FoodStats foodStats = ((EntityPlayer) player).getFoodStats();

            foodStats.addStats(1, damage * 0.3F);
        }
    }
}
