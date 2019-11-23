package cn.mcmod.tofucraft.block.tofu;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.material.TofuMaterial;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class BlockTofuLadder extends BlockLadder {
    public BlockTofuLadder(Material materialIn) {
        super();
        if (materialIn == TofuMaterial.softtofu) {
            this.setHardness(0.3F);
            this.setResistance(0.9F);
            this.setSoundType(SoundType.CLOTH);
        } else if (materialIn == TofuMaterial.tofu) {
            this.setHardness(0.35F);
            this.setResistance(1.0F);
            this.setSoundType(SoundType.CLOTH);
        } else if (materialIn == Material.ROCK) {
            this.setHardness(1.8F);
            this.setResistance(9.0F);
            this.setSoundType(SoundType.STONE);
        } else if (materialIn == Material.IRON) {
            this.setHardness(5.0F);
            this.setResistance(11.0F);
            this.setSoundType(SoundType.METAL);
        }

        this.setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
}