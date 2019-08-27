package cn.mcmod.tofucraft.item.fulintlock.warheads;

import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.ItemStack;

public class WarheadSoybean extends Warhead {
    private static final ItemStack soy = new ItemStack(ItemLoader.soybeans, 1);

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public ItemStack getAmmoItem() {
        return soy;
    }


    @Override
    public float getSize() {
        return 2;
    }
}
