package cn.mcmod.tofucraft.block.fluid;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ZundaSoyMilkFluid extends Fluid {

    public static final String name = "zunda_soymilk";
    public static final ZundaSoyMilkFluid instance = new ZundaSoyMilkFluid();

    public ZundaSoyMilkFluid() {
        super(name, new ResourceLocation(TofuMain.MODID, "blocks/zunda_soymilk"), new ResourceLocation(TofuMain.MODID, "blocks/zunda_soymilk_flow"));
    }

}