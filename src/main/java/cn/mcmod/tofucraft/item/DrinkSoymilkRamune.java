package cn.mcmod.tofucraft.item;

import java.util.Random;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;

public class DrinkSoymilkRamune extends ItemFood {
	private PotionEffect[] effect;

	
	public DrinkSoymilkRamune(String name,int amounts,float saturations, PotionEffect[] effects) {
		super(amounts, saturations, false);

		this.setUnlocalizedName(TofuMain.MODID+"."+name);
		this.setAlwaysEdible();
		this.setMaxStackSize(1);
		this.effect=effects!=null&&effects.length>0?effects:null;
		
	}

	public PotionEffect[] getEffectList() {
		return effect;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(true)||playerIn.isCreative())
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }
	 public EnumAction getItemUseAction(ItemStack stack)
	    {
	        return EnumAction.DRINK;
	    }
	 public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	    {
		 if (entityLiving instanceof EntityPlayer)
	        {
	            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
	            entityplayer.getFoodStats().addStats(this, stack);
	            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
	            this.onFoodEaten(stack, worldIn, entityplayer);
	            entityplayer.addStat(StatList.getObjectUseStats(this));
	            if (entityplayer instanceof EntityPlayerMP)
	            {
	                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
	            }
	        }
		 return new ItemStack(Items.GLASS_BOTTLE);
	    }
	 
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(!worldIn.isRemote){
		if(getEffectList()!=null&&getEffectList().length>0){
			Random rand = worldIn.rand;
			PotionEffect effect1 = getEffectList()[rand.nextInt(getEffectList().length)];
					if (effect1 != null && effect1.getPotion() != null) {
						Potion por = effect1.getPotion();
						int amp = effect1.getAmplifier();
						int dur = effect1.getDuration();
						if (player.isPotionActive(effect1.getPotion())) {
							PotionEffect check = player.getActivePotionEffect(por);
							dur += check.getDuration();
							amp ++;
						}
						player.addPotionEffect(new PotionEffect(effect1.getPotion(), dur, amp));
					}
			
			}
		}
	}

}
