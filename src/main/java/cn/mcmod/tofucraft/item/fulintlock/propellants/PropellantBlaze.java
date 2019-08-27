package cn.mcmod.tofucraft.item.fulintlock.propellants;

import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PropellantBlaze extends Propellant {
    private static final ItemStack blaze = new ItemStack(Items.BLAZE_POWDER, 1);

    @Override
    public ItemStack getPropellantItem() {
        return blaze;
    }

    @Override
    public float getPropellantForce() {
        return 2;
    }

    @Override
    public float getPropellantInaccuracy() {
        return 0;
    }
}
