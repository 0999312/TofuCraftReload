package cn.mcmod.tofucraft.material;

import cn.mcmod.tofucraft.block.BlockTofuBase;

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

    public BlockTofuBase getBlock() {
        return this.getBlock();
    }

  /*  public TofuInfo getBlockInfo()
    {
        return TcBlocks.tofuInfoMap.get(this);
    }*/

 /*   public Item getItem()
    {
        return ItemLoader.tofu_material.getContainerItem(this);
    }*/

    public static TofuType get(int id) {
        return values()[id];
    }

    public int id() {
        return this.ordinal();
    }
}