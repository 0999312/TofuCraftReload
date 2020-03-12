package cn.mcmod.tofucraft.block.torch;

import cn.mcmod.tofucraft.CommonProxy;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.util.EnumFacing;

public class BlockTofuTorch extends BlockTorch {
    public BlockTofuTorch(SoundType sound){
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setTickRandomly(true);
        this.setCreativeTab(CommonProxy.tab);
        this.setSoundType(sound);
        this.setResistance(0.35F);
        this.setLightLevel(0.9375F);
    }

    public BlockTofuTorch(){
        this(SoundType.CLOTH);
    }
    
}
