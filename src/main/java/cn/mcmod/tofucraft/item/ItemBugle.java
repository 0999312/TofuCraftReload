package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.sound.TofuSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBugle extends Item {

    public ItemBugle() {
        super();
        this.setUnlocalizedName(TofuMain.MODID + "." + "bugle");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        playerIn.getCooldownTracker().setCooldown(stack.getItem(), 100);

        playerIn.playSound(TofuSounds.TOFUBUGLE, 20.0F, 1.0F);
        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
}
