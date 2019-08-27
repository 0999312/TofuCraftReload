package cn.mcmod.tofucraft.item.fulintlock.propellants;

import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PropellantGunpowder extends Propellant {
    private static final ItemStack gunpowder = new ItemStack(Items.GUNPOWDER, 1);

    @Override
    public ItemStack getPropellantItem() {
        return gunpowder;
    }

    @Override
    public float getPropellantForce() {
        return 0.5f;
    }

    @Override
    public float getPropellantInaccuracy() {
        return 0;
    }

}
