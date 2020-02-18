package cn.mcmod.tofucraft.item.tfitem;

import cn.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.base.item.energyItem.IEnergyContained;
import cn.mcmod.tofucraft.base.item.energyItem.IEnergyExtractable;
import cn.mcmod.tofucraft.base.item.energyItem.IEnergyInsertable;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;
import cn.mcmod.tofucraft.item.ItemSwordBasic;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.util.NBTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class ItemTofuForceSword extends ItemSwordBasic implements IEnergyExtractable, IEnergyInsertable, IEnergyContained {

    public ItemTofuForceSword() {
        super(TofuToolMaterial.FORCE, "tofuforce_sword");
        this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isUsable(stack) ? 0.0F : 1.0F;
            }
        });
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (isUsable(stack)) {
            if (getEnergy(stack) >= 5) {
                drain(stack, 5, false);

            } else {
                stack.damageItem(1, attacker);
            }
            return true;
        }

        return false;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            if (isUsable(stack)) {

                if (getEnergy(stack) >= 10) {
                    drain(stack, 10, false);

                } else {
                    stack.damageItem(1, entityLiving);
                }
            }
        }

        return true;
    }


    public static boolean isUsable(ItemStack stack) {
        return stack.getItemDamage() <= stack.getMaxDamage();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }


    public static final String TAG_TF = "tf_energy";
    public static final String TAG_TFMAX = "tf_energymax";

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!isUsable(stack)) {
            tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.tofucraft.tofuforce_core.broken"));
        }
        tooltip.add(I18n.format("tooltip.tofucraft.energy", getEnergy(stack), getEnergyMax(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private boolean getShowState(ItemStack stack) {
        return !Minecraft.getMinecraft().player.isSneaking() && getEnergy(stack) != 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getShowState(stack) || super.showDurabilityBar(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getShowState(stack) ?
                1.0 - (double) getEnergy(stack) / (double) getEnergyMax(stack) : super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return getShowState(stack) ? Color.white.getRGB() : super.getRGBDurabilityForDisplay(stack);
    }

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
        return 5000;
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