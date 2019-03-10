package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ItemArmor {
    private String armorTextureFile;
    public ItemArmorBase(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn, String name) {
        super(materialIn, 0, equipmentSlotIn);
        this.setUnlocalizedName(TofuMain.MODID+"."+name);
    }

    public ItemArmorBase setArmorTexture(String file)
    {
        armorTextureFile = file;
        return this;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return this.armorTextureFile;
    }
}
