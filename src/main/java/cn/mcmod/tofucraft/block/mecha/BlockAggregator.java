package cn.mcmod.tofucraft.block.mecha;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.tileentity.tofuenergy.worker.TileEntitySoymilkAggregator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class BlockAggregator extends BlockContainer {
    public BlockAggregator() {
        super(Material.IRON);
        setCreativeTab(CommonProxy.tab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stack = playerIn.getHeldItem(hand);
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntitySoymilkAggregator) {
                IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
                if (handler != null)
                    FluidUtil.interactWithFluidHandler(playerIn, hand, tileentity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing));

            }
        }
        return true;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntitySoymilkAggregator();
    }
}
