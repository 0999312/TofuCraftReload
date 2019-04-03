package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.entity.projectile.EntityZundaArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemZundaArrow extends ItemArrow {

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
    {
        return new EntityZundaArrow(worldIn, shooter);
    }
}
