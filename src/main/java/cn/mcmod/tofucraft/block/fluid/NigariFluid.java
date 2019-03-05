package cn.mcmod.tofucraft.block.fluid;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class NigariFluid extends Fluid {

    public static final String name = "nigari";
    public static final NigariFluid instance = new NigariFluid();

    public NigariFluid() {
        super(name, new ResourceLocation(TofuMain.MODID, "blocks/nigari"), new ResourceLocation(TofuMain.MODID, "blocks/nigari"));
    }

}