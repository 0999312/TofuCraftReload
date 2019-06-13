package cn.mcmod.tofucraft.block.plants.vine;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockTofuYuba extends BlockUnderVine implements net.minecraftforge.common.IShearable {
    public BlockTofuYuba() {
        super();
        this.setCreativeTab(CommonProxy.tab);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemLoader.material;
    }

    public int damageDropped(IBlockState state) {
        return 32;
    }
}
