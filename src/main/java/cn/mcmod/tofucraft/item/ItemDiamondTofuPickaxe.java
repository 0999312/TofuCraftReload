package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.material.TofuToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemDiamondTofuPickaxe extends ItemPickaxeBasic {

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
}
