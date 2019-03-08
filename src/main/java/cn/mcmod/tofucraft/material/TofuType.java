package cn.mcmod.tofucraft.material;

import cn.mcmod.tofucraft.block.BlockTofuBase;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum TofuType {
    kinu,
    momen,
    ishi,
    metal,
    grilled,
    dried,
    friedPouch,
    fried,
    egg,
    annin,
    sesame,
    zunda,
    strawberry,
    hell,
    glow,
    diamond,
    miso;

    TofuType() {
    }

   /* public BlockTofuBase getBlock() {
        return this.getBlock();
    }*/

  /*  public TofuInfo getBlockInfo()
    {
        return TcBlocks.tofuInfoMap.get(this);
    }*/

    public ItemStack getItemStack()
    {
        return ItemLoader.tofuItems.get(this);
    }

    public static TofuType get(int id) {
        return values()[id];
    }

    public int id() {
        return this.ordinal();
    }
}