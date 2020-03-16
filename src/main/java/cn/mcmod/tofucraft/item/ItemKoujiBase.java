package cn.mcmod.tofucraft.item;

import java.util.List;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod_mmf.mmlib.util.RecipesUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemKoujiBase extends Item {
	
	public ItemKoujiBase() {
		setUnlocalizedName(TofuMain.MODID+"."+"koujibase");
		setMaxStackSize(1);
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if(entityIn instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entityIn;
		NBTTagCompound nbt = RecipesUtil.getItemTagCompound(stack);
		if(!nbt.hasKey("timer")){nbt.setInteger("timer", 0);}else 
			if(nbt.hasKey("timer")&&nbt.getInteger("timer")>=18000){
			ItemStack newstack = new ItemStack(ItemLoader.material,1,1);
			stack.shrink(1);
			player.inventory.addItemStackToInventory(newstack);
		}else if(nbt.hasKey("timer")){
			nbt.setInteger("timer", nbt.getInteger("timer")+1);
		}
	}
	}
}
