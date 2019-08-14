package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class ItemArmorBase extends ItemArmor {
	
    public static Potion[][] registanceList = new Potion[][]{
        {MobEffects.BLINDNESS, MobEffects.NAUSEA}, // Helmet
        {MobEffects.POISON, MobEffects.HUNGER, MobEffects.WITHER}, // Chestplate
        {MobEffects.WEAKNESS, MobEffects.MINING_FATIGUE}, // Leggings
        {MobEffects.SLOWNESS} // Boots
    };
	
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
