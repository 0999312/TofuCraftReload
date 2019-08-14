package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.material.TofuToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemDiamondTofuAxe extends ItemAxeBasic {

    private DiamondTofuToolHandler impl;

    public ItemDiamondTofuAxe()
    {
        super(TofuToolMaterial.DIAMOND, 8.0F, -2.95F, "tooldiamondaxe");
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
        return super.onBlockStartBreak(stack, pos, owner);
    }
}
