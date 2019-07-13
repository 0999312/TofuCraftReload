package cn.mcmod.tofucraft.block.fluid;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class SoyMilkHellFluid extends Fluid {

    public static final String name = "soymilk_hell";
    public static final SoyMilkHellFluid instance = new SoyMilkHellFluid();

    public SoyMilkHellFluid() {
        super(name, new ResourceLocation(TofuMain.MODID, "blocks/soymilk_hell"), new ResourceLocation(TofuMain.MODID, "blocks/soymilk_hell_flow"));
    }

}