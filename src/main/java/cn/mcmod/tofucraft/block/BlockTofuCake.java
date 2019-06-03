package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;

public class BlockTofuCake extends BlockCake {
	public BlockTofuCake() {
		setCreativeTab(CommonProxy.tab);
		setSoundType(SoundType.CLOTH);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, Integer.valueOf(0)));
        this.setTickRandomly(true);
	}
}
