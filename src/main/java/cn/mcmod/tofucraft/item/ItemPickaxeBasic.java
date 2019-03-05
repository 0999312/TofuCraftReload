package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ItemPickaxeBasic extends ItemPickaxe {
    public ItemPickaxeBasic(Item.ToolMaterial material, String name) {
        super(material);
        this.setUnlocalizedName(TofuMain.MODID+"."+name);
    }
}
