package cn.mcmod.tofucraft.api.recipes.recipe;

import net.minecraft.block.state.IBlockState;

public class TofuTransmutation {
    private final double chance;
    private final int extra;
    private final IBlockState result;

    public TofuTransmutation(double chance, int extra, IBlockState result) {
        this.chance = chance;
        this.extra = extra;
        this.result = result;
    }

    public double getChance() {
        return chance;
    }

    public IBlockState getResult() {
        return result;
    }

    public int getExtra() {
        return extra;
    }
}
