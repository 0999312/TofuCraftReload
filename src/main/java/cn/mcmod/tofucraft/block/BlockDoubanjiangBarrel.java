package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoubanjiangBarrel extends BlockBarrel {
	private boolean hasSoy(IBlockState state){
		return getFerm(state)==7;
	}

	public BlockDoubanjiangBarrel() {
		super(ItemStack.EMPTY, new ItemStack[]{
				new ItemStack(ItemLoader.material,3,0),
				new ItemStack(ItemLoader.material,3,31)
		});
	}
    

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
    		int fortune) {
    	if(getFerm(state)!=8){
    		drops.clear();
    		drops.add(new ItemStack(state.getBlock()));
    	}else
    	super.getDrops(drops, world, pos, state, fortune);
    }
	
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if(playerIn.getHeldItem(hand).getItem() ==Items.GLASS_BOTTLE && hasSoy(state)){

    		if(playerIn.getHeldItem(hand).getCount()>1){
    			playerIn.getHeldItem(hand).shrink(1);
    			playerIn.inventory.addItemStackToInventory(new ItemStack(ItemLoader.doubanjiang_bottle));
    		}
    		else{
    		playerIn.setHeldItem(hand, new ItemStack(ItemLoader.doubanjiang_bottle));
    		}
    		worldIn.setBlockState(pos, this.withFerm(8), 2);
    	}
    	return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
}
