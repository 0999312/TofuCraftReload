package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.material.TofuMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTofuBase extends Block {

    public BlockTofuBase() {
        super(TofuMaterial.tofu);
    }

    public BlockTofuBase(Material material) {
        super(material);
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 4;
    }

    /**
     * Whether the tofu can be scooped with Tofu Scoop
     */
    private boolean scoopable = true;

    public BlockTofuBase setScoopable(boolean b) {
        this.scoopable = b;
        return this;
    }

    public boolean isScoopable() {
        return this.scoopable;
    }


    public ItemStack createScoopedBlockStack() {
        return new ItemStack(this);
    }

}