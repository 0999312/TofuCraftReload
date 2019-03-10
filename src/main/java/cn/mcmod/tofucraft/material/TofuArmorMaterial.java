package cn.mcmod.tofucraft.material;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class TofuArmorMaterial {
    public static ItemArmor.ArmorMaterial KINU;
    public static ItemArmor.ArmorMaterial MOMEN;
    public static ItemArmor.ArmorMaterial SOLID;
    public static ItemArmor.ArmorMaterial METAL;
    public static ItemArmor.ArmorMaterial DIAMOND;

    static
    {
        KINU    = EnumHelper.addArmorMaterial("TOFU_KINU",   "tofucraft:textures/armor/armor_kinu",  0, new int[]{1, 1, 1, 1},  1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
        MOMEN   = EnumHelper.addArmorMaterial("TOFU_MOMEN",  "tofucraft:textures/armor/armor_momne",  1, new int[]{1, 2, 1, 1},  3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);
        SOLID   = EnumHelper.addArmorMaterial("TOFU_SOLID",  "tofucraft:textures/armor/armor_solid",  8, new int[]{2, 4, 3, 2},  8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);
        METAL   = EnumHelper.addArmorMaterial("TOFU_METAL",  "tofucraft:textures/armor/armor_metal", 13, new int[]{2, 7, 6, 2},  9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f); // IRON={2, 6, 5, 2}
        DIAMOND = EnumHelper.addArmorMaterial("TOFU_DIAMOND","tofucraft:textures/armor/armor_diamond", 80, new int[]{3, 8, 6, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f); // DIAMOND={3, 8, 6, 3}
    }
}
