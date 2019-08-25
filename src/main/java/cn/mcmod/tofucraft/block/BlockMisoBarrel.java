package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BlockMisoBarrel extends BlockBarrel {
    public BlockMisoBarrel() {
        super(new ItemStack(ItemLoader.material, 6, 2), new ItemStack[]{
                new ItemStack(ItemLoader.material, 3, 0),
                new ItemStack(ItemLoader.material, 3, 1)
        });
    }

    private boolean hasSoy(IBlockState state) {
        return getFerm(state) == 7;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand).getItem() == Items.BUCKET && hasSoy(state)) {

            if (playerIn.getHeldItem(hand).getCount() > 1) {
                playerIn.getHeldItem(hand).shrink(1);
                playerIn.inventory.addItemStackToInventory(FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYSAUCE_FLUID, 1000)));
            } else {
                playerIn.setHeldItem(hand, FluidUtil.getFilledBucket(new FluidStack(BlockLoader.SOYSAUCE_FLUID, 1000)));
            }
            worldIn.setBlockState(pos, this.withFerm(8), 2);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

}
