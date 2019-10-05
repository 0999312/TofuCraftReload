package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.projectile.EntityTippedChingerArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChingerToothArrow extends ItemArrow {
    public ItemChingerToothArrow() {
        super();
        this.setUnlocalizedName(TofuMain.MODID + "." + "tofuchinger_tootharrow");
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        EntityTippedChingerArrow entitytippedarrow = new EntityTippedChingerArrow(worldIn, shooter);
        entitytippedarrow.setPotionEffect(stack);
        return entitytippedarrow;
    }

}
