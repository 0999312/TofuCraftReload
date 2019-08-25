package cn.mcmod.tofucraft.base.item.EnergyItem;

import cn.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;
import cn.mcmod.tofucraft.util.NBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemTofuEnergyContained extends Item implements IEnergyExtractable, IEnergyInsertable, IEnergyContained {

    /*
     * Comment:
     * This is the base class of a TF Energy Item which can insert energy to, extract energy from and store energy.
     * There are some exceptions, so I divided the code into three interfaces.
     * Codes here are highly overridable, but can have function without any overrides.
     * */

    public static final String TAG_TF = "tf_energy";
    public static final String TAG_TFMAX = "tf_energymax";

    @Override
    public int drain(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) - calculated);
        return calculated;
    }

    @Override
    public int fill(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergyMax(inst) - getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) + calculated);
        return calculated;
    }

    @Override
    public int getEnergy(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTagCompound(), TAG_TF, 0);
    }

    //This acts as a way to modify the max energy an item can hold.
    //You can override it to strictly declare how much an item should hold.
    @Override
    public int getEnergyMax(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTagCompound(), TAG_TFMAX, 0);
    }

    @Override
    public void setEnergy(ItemStack inst, int amount) {
        inst.setTagCompound(NBTUtil.setInteger(inst.getTagCompound(), TAG_TF, amount));
    }

    @Override
    public void setEnergyMax(ItemStack inst, int amount) {
        inst.setTagCompound(NBTUtil.setInteger(inst.getTagCompound(), TAG_TFMAX, amount));
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        this.onUpdate(entityItem.getItem(), entityItem.getEntityWorld(), entityItem, 0, false);
        return super.onEntityItemUpdate(entityItem);
    }

    //Items will recharge overtime if there are TileEntitySender nearby.
    //It's tedious to put your item inside the battery box each time it exhausted.
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && getEnergy(stack) < getEnergyMax(stack)) {
            BlockPos entityPos = new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ);
            List<TileEntity> list = TofuNetwork.toTiles(TofuNetwork.Instance.getExtractableWithinRadius(
                    worldIn, entityPos, 64));
            if (!list.isEmpty()) {
                int toDrain = getEnergyMax(stack) - getEnergy(stack);
                for (TileEntity te : list) {
                    if (te instanceof TileEntitySenderBase &&
                            ((TileEntitySenderBase) te).isValid() &&
                            te.getPos().getDistance(entityPos.getX(), entityPos.getY(), entityPos.getZ()) <= ((TileEntitySenderBase) te).getRadius()) {
                        toDrain -= ((ITofuEnergy) te).drain(Math.min(toDrain, ((TileEntitySenderBase) te).getTransferPower()), false);
                        if (toDrain == 0) break;
                    }
                }
                fill(stack, getEnergyMax(stack) - getEnergy(stack) - toDrain, false);
            }
        }
    }
}
