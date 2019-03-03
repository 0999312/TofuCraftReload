package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;

public class ItemSwordBasic extends ItemSword {
    public ItemSwordBasic(ToolMaterial material,String name, String... subNames) {
        super(material);
        this.setUnlocalizedName(TofuMain.MODID+"."+name);
    }
}
