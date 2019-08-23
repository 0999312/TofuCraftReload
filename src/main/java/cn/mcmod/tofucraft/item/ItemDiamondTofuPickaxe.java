package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.base.item.EnergyItem.IEnergyExtractable;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.util.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemDiamondTofuPickaxe extends ItemPickaxeBasic implements IEnergyExtractable
{
    private DiamondTofuToolHandler impl;

    public ItemDiamondTofuPickaxe()
    {
        super(TofuToolMaterial.DIAMOND, "tooldiamondpickaxe");
        this.impl = new DiamondTofuToolHandler(this);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer owner)
    {
        if (owner.world.isRemote)
        {
            Block blockDestroyed = owner.getEntityWorld().getBlockState(pos).getBlock();
            this.impl.onBlockStartBreak(stack, owner.world, blockDestroyed, pos, owner);
        }
        return false;
    }

    @Override
    public int drain(ItemStack inst, int amount, boolean simulate) {
        if (!simulate) ItemUtils.damageItemStack(inst, 50);
        return 50;
    }
}
