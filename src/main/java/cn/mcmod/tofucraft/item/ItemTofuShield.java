package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.base.item.EnergyItem.IEnergyExtractable;
import cn.mcmod.tofucraft.util.ItemUtils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemTofuShield extends Item implements IEnergyExtractable {
    public ItemTofuShield(int damage) {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(damage);
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return true;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }


    @Override
    public int drain(ItemStack inst, int amount, boolean simulate) {
        if (!simulate) ItemUtils.damageItemStack(inst, 1);
        return 50;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}
