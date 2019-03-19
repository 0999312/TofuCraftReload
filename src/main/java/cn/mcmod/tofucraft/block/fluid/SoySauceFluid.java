package cn.mcmod.tofucraft.block.fluid;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class SoySauceFluid extends Fluid {

    public static final String name = "soysauce";
    public static final SoySauceFluid instance = new SoySauceFluid();

    public SoySauceFluid() {
        super(name, new ResourceLocation(TofuMain.MODID, "blocks/soysauce"), new ResourceLocation(TofuMain.MODID, "blocks/soysauce"));
    }

}