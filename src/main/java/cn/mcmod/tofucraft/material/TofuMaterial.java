package cn.mcmod.tofucraft.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class TofuMaterial extends Material {

    public TofuMaterial(MapColor par1MapColor) {
        super(par1MapColor);
    }

    public static final Material tofu = new TofuMaterial(MapColor.CLOTH);


}