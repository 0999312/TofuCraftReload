package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemAxeBasic extends ItemAxe {
    public ItemAxeBasic(Item.ToolMaterial material, float damage, float speed, String name) {
        super(material,damage,speed);
        this.setUnlocalizedName(TofuMain.MODID+"."+name);
    }
}
