package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.tileentity.TileEntityTofuChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class BlockTofuChest extends BlockChest {
    public static BlockChest.Type TOFU_CHEST = EnumHelper.addEnum(BlockChest.Type.class, "TOFU_CHEST", new Class[0]);
    public BlockTofuChest() {
        super(TOFU_CHEST);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(1.82F);
        this.setResistance(9.5F);
        this.setHarvestLevel("pickaxe", 0);
        this.setSoundType(SoundType.STONE);
        this.disableStats();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityTofuChest) {
            tileentity.updateContainingBlockInfo();
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityTofuChest();
    }
}
