package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemSoybeansNether extends Item implements IPlantable {
    public ItemSoybeansNether(){
        this.setUnlocalizedName(TofuMain.MODID+"."+"seeds_soybeans_hell");
    }
    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack item = playerIn.getHeldItem(hand);

        if(item.getItem() == this &&!item.isEmpty()) {
            if (side != EnumFacing.UP) {
                return EnumActionResult.PASS;
            } else if (playerIn.canPlayerEdit(pos, side, item) && playerIn.canPlayerEdit(pos.up(), side, item)) {
                IBlockState soil = worldIn.getBlockState(pos);

                if (soil != null && worldIn.isAirBlock(pos.up())) {
                    boolean isPlanted = false;

                    if (soil.getBlock() == BlockLoader.TOFUFARMLAND||soil.getBlock()==Blocks.SOUL_SAND) {
                        worldIn.setBlockState(pos.up(), BlockLoader.SOYBEAN_NETHER.getDefaultState());
                        isPlanted = true;
                    }

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
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return BlockLoader.SOYBEAN_NETHER.getDefaultState();
    }

}
