package cn.mcmod.tofucraft.item.tfitem;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.base.item.energyItem.ItemTofuEnergyContained;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemGrowingTofu extends ItemTofuEnergyContained {

    private static final Random rnd = new Random();

    public ItemGrowingTofu() {
        setMaxStackSize(1);
        setUnlocalizedName(TofuMain.MODID + "." + "growing_tofu");
        setMaxDamage(6);
    }

    @Override
    public int getEnergyMax(ItemStack inst) {
        return 10000;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 64;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            if (worldIn.getWorldTime() % 200 == 0) {
                if (rnd.nextBoolean())
                    if (getDamage(stack) > 0 && getEnergy(stack) > 5000) {
                        drain(stack, 500, false);
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
            }
        }

        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(false))
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.tofucraft.growing_tofu1"));
        tooltip.add(I18n.format("tooltip.tofucraft.growing_tofu2"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        stack.damageItem(1, entityLiving);

        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            player.getFoodStats().addStats(5, 5);
        }
        return stack;
    }
}
