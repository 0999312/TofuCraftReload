package cn.mcmod.tofucraft.item;

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

public class DrinkSoymilk extends ItemFood {
	private PotionEffect[] effect;
	
	public String[] subNames;
	public int[] amount;
	public float[] saturation;
	
	public DrinkSoymilk(String name,int[] amounts,float[] saturations, String[] subNames, PotionEffect[] effects) {
		super(amounts[0], saturations[0], false);

		this.setUnlocalizedName(TofuMain.MODID+"."+name);
		this.setAlwaysEdible();
		this.setHasSubtypes(subNames!=null&&subNames.length > 0);
		this.setMaxStackSize(1);
		this.effect=effects!=null&&effects.length>0?effects:null;
		this.subNames = subNames!=null&&subNames.length > 0?subNames: null;
		this.amount = amounts!=null&&amounts.length > 0?amounts: null;
		this.saturation = saturations!=null&&saturations.length > 0?saturations: null;
	}

	@Override
	public int getHealAmount(ItemStack stack) {
		return stack.getMetadata() < getAmounts().length?getAmounts()[stack.getMetadata()]: 0;
	}

	@Override
	public float getSaturationModifier(ItemStack stack) {
		return stack.getMetadata() < getSaturations().length?getSaturations()[stack.getMetadata()]: 0;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(this.isInCreativeTab(tab))
			if(getSubNames()!=null)
			{
				for(int i = 0; i < getSubNames().length; i++)
						list.add(new ItemStack(this, 1, i));
			}
			else
				list.add(new ItemStack(this));
	}
	public int[] getAmounts() {
		return amount;
	}
	
	public float[] getSaturations() {
		return saturation;
	}
	
	public String[] getSubNames() {
		return subNames;
	}
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if(getSubNames()!=null) {
			String subName = stack.getMetadata() < getSubNames().length?"item."+getSubNames()[stack.getMetadata()]: "";
			return subName;
		}
		return this.getUnlocalizedName();
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
		if(getEffectList()!=null&&getEffectList().length>0){
			PotionEffect effect1 = getEffectList()[stack.getMetadata()];
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
