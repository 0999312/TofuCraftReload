package cn.mcmod.tofucraft.enchantments;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentDrain extends Enchantment {

	protected EnchantmentDrain() {
		super(Enchantment.Rarity.COMMON, EnchantmentLoader.typeDiamondTofu, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND
		});
	}
	
    @Override
    public int getMinEnchantability(int par1)
    {
        return 1 + 10 * (par1 - 1);
    }
    @Override
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }
	
    @Override
    public boolean canApply(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() == ItemLoader.diamondTofuSword;
    }

    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return this.canApply(stack);
    }
}
