package cn.mcmod.tofucraft.item.tfitem;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.base.item.energyItem.ItemTofuEnergyContained;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTofuCore extends ItemTofuEnergyContained {

    public ItemTofuCore() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(300);
        this.setUnlocalizedName(TofuMain.MODID + "." + "tofucore");
        this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isUsable(stack) ? 0.0F : 1.0F;
            }
        });
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getItemDamage() <= stack.getMaxDamage();
    }

    @Override
    public int getEnergyMax(ItemStack inst) {
        return 5000;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        //tooltip.add(I18n.translateToLocal("tooltip.tofucraft.tofuforce_core2"));
        if (!isUsable(stack)) {
            tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.tofucraft.tofuforce_core.broken"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

}
