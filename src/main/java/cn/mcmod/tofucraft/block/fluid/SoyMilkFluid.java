package cn.mcmod.tofucraft.block.fluid;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class SoyMilkFluid extends Fluid {

    public static final String name = "soymilk";
    public static final SoyMilkFluid instance = new SoyMilkFluid();

    public SoyMilkFluid() {
        super(name, new ResourceLocation(TofuMain.MODID, "blocks/soymilk"), new ResourceLocation(TofuMain.MODID, "blocks/soymilk_flow"));
    }

}