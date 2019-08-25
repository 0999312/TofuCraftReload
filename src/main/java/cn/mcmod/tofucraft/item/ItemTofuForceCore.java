package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.base.item.EnergyItem.ItemTofuEnergyContained;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTofuForceCore extends ItemTofuEnergyContained {

    public ItemTofuForceCore() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(360);
        this.setUnlocalizedName(TofuMain.MODID + "." + "tofuforce_core");
        this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isUsable(stack) ? 0.0F : 1.0F;
            }
        });
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getItemDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public int getEnergyMax(ItemStack inst) {
        return 10000;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entityIn;

            if (entityLivingBase.ticksExisted % 400 == 0 && isUsable(stack)) {
                if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                    if (getEnergy(stack) >= 5)
                        drain(stack, 5, false);
                    else
                        stack.damageItem(1, entityLivingBase);

                    entityLivingBase.heal(1);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.translateToLocal("tooltip.tofucraft.tofuforce_core1"));
        //tooltip.add(I18n.translateToLocal("tooltip.tofucraft.tofuforce_core2"));
        tooltip.add("");
        if (!isUsable(stack)) {
            tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("tooltip.tofucraft.tofuforce_core.broken"));
        }
        tooltip.add(String.format(I18n.translateToLocal("tooltip.tofucraft.energy"), getEnergy(stack), getEnergyMax(stack)));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Item.getItemFromBlock(BlockLoader.METALTOFU) ? true : super.getIsRepairable(toRepair, repair);
    }
}
