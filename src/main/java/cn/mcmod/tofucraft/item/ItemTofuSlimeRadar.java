package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.EntityTofuSlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemTofuSlimeRadar extends Item {
	 public ItemTofuSlimeRadar()
	 {
	        super();
	        this.setMaxStackSize(1);
	        this.setMaxDamage(87);
	 }
	 @Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		 boolean flag = playerIn.capabilities.isCreativeMode;

	        if (flag || playerIn.getHeldItem(handIn).getItemDamage() <= playerIn.getHeldItem(handIn).getMaxDamage())
	        {
	            if (!worldIn.isRemote)
	            {
	                boolean isSpawnChunk = playerIn.dimension == TofuMain.TOFU_DIMENSION.getId() || EntityTofuSlime.isSpawnChunk(playerIn.world, playerIn.posX, playerIn.posZ);
	            
	                if(isSpawnChunk) playerIn.sendMessage(new TextComponentTranslation("tofucraft.radar.result.success", new Object()));
	                else playerIn.sendMessage(new TextComponentTranslation("tofucraft.radar.result.failed", new Object()));
	            }

	            if (!playerIn.capabilities.isCreativeMode && playerIn.getHeldItem(handIn).isItemStackDamageable())
	            {
	            	playerIn.getHeldItem(handIn).damageItem(1, playerIn);
	            }
	            playerIn.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5F, 1.0F);
	        }
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
