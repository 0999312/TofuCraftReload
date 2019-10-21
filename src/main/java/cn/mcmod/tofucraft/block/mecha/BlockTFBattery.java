package cn.mcmod.tofucraft.block.mecha;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.tileentity.tofuenergy.reservoir.TileEntityTofuBattery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
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

public class BlockTFBattery extends Block {
    public BlockTFBattery() {
        super(Material.IRON);
        setCreativeTab(CommonProxy.tab);
        this.setHardness(5.0F);
        this.setResistance(12.0F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote) {
            ItemStack stack = playerIn.getHeldItem(hand);
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityTofuBattery) {
                IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
                if (handler != null) {
                    FluidUtil.interactWithFluidHandler(playerIn, hand, tileentity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing));
                    return true;
                } else {
                    playerIn.openGui(TofuMain.instance, TofuGuiHandler.ID_BATTERY_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityTofuBattery) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityTofuBattery) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }


        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTofuBattery();
    }
}
