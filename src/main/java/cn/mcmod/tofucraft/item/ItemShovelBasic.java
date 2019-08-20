package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ItemShovelBasic extends ItemSpade {
    public ItemShovelBasic(Item.ToolMaterial material, String name) {
        super(material);
        this.setUnlocalizedName(TofuMain.MODID + "." + name);
    }
}
