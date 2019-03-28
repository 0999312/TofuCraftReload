package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRolling extends Item {
	public ItemRolling() {
		setUnlocalizedName(TofuMain.MODID+"."+"rollingpin");
		setMaxDamage(60);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		 int dmg = itemStack.getItemDamage();
		 if (dmg < this.getMaxDamage(itemStack))
	        {
			 ItemStack stack = itemStack.copy();
			 stack.setItemDamage(dmg +1);
	         return stack;
	        }else
		return super.getContainerItem(itemStack);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		  int dmg = stack.getItemDamage();
		  if (dmg < this.getMaxDamage(stack))
	        {
	            return true;
	        }
	        else
	        {
	            return super.hasContainerItem(stack);
	        }
	}
	

}
