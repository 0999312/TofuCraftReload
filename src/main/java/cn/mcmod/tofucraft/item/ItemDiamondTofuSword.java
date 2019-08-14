package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.enchantments.EnchantmentLoader;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;

public class ItemDiamondTofuSword extends ItemSwordBasic {

	public ItemDiamondTofuSword() {
		super(TofuToolMaterial.DIAMOND, "sworddiamond");
	}

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase targetEntity, EntityLivingBase attacker)
    {
        // Drain
        Float healthBeforeAttack = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        if (healthBeforeAttack != null)
        {
            float damage = healthBeforeAttack - targetEntity.getHealth();
            if (damage > 0.0f)
            {
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.Drain, itemStack);
                attacker.heal(damage * (lvl * 0.1f + 0.1f));
            }
        }
        return super.hitEntity(itemStack, targetEntity, attacker);
    }
}
