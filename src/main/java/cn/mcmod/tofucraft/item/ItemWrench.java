package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.Item;

public class ItemWrench extends Item {
    public ItemWrench(){
        setCreativeTab(CommonProxy.tab);
        setMaxStackSize(1);
        setUnlocalizedName(TofuMain.MODID+"."+"wrench");
    }
}
