package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBase extends Item {
	protected String[] subNames;
	public ItemBase(String name, int stackSize, String... subNames) {
		this.setUnlocalizedName(TofuMain.MODID+"."+name);
		this.setHasSubtypes(subNames!=null&&subNames.length > 0);
		this.setMaxStackSize(stackSize);
		this.subNames = subNames!=null&&subNames.length > 0?subNames: null;
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


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack item = playerIn.getHeldItem(hand);

        if (item.getItem() == this && item.getMetadata() == 16 && !item.isEmpty()) {
            if (side != EnumFacing.UP) {
                return EnumActionResult.PASS;
            } else if (playerIn.canPlayerEdit(pos, side, item) && playerIn.canPlayerEdit(pos.up(), side, item)) {
                IBlockState soil = worldIn.getBlockState(pos);

                if (soil != null && worldIn.isAirBlock(pos.up())) {
                    boolean isPlanted = false;

                    if (soil.getBlock() == BlockLoader.TOFUFARMLAND) {
                        worldIn.setBlockState(pos.up(), BlockLoader.LEEKCROP.getDefaultState());
                        isPlanted = true;
                    }
                    /*TcAchievementMgr.achieve(playerIn, Key.sproutPlanting);*//*

                    }*/
                    if (isPlanted) {
                        item.shrink(1);
                        return EnumActionResult.SUCCESS;
                    } else {
                        return EnumActionResult.PASS;
                    }
                } else {
                    return EnumActionResult.PASS;
                }
                //return EnumActionResult.PASS;
            }
            return EnumActionResult.PASS;
        } else {
            return EnumActionResult.PASS;
        }
    }
}
