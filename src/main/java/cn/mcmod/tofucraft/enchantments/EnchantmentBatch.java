package cn.mcmod.tofucraft.enchantments;


import cn.mcmod.tofucraft.item.ItemDiamondTofuAxe;
import cn.mcmod.tofucraft.item.ItemDiamondTofuPickaxe;
import cn.mcmod.tofucraft.item.ItemDiamondTofuSpade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentBatch extends Enchantment {
	 public EnchantmentBatch(){
			super(Enchantment.Rarity.COMMON, EnchantmentLoader.typeDiamondTofu, new EntityEquipmentSlot[]{
					EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND
			});
     }
     @Override
     public int getMinEnchantability(int par1)
     {
         return 15 + (par1 - 1) * 9;
     }
     @Override
     public int getMaxEnchantability(int par1)
     {
         return this.getMinEnchantability(par1) + 50;
     }

     @Override
     public int getMaxLevel()
     {
         return 3;
     }

     @Override
     public boolean canApply(ItemStack par1ItemStack)
     {
         return par1ItemStack.getItem() instanceof ItemDiamondTofuAxe||
        		par1ItemStack.getItem() instanceof ItemDiamondTofuSpade||
        		par1ItemStack.getItem() instanceof ItemDiamondTofuPickaxe;
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
