package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.ItemSword;

public class ItemSwordBasic extends ItemSword {
    public ItemSwordBasic(ToolMaterial material,String name, String... subNames) {
        super(material);
        this.setUnlocalizedName(TofuMain.MODID+"."+name);
    }
}
