package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockLeek extends BlockBush {
    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.10000001192092896D, 0.0D, 0.10000001192092896D, 0.899999988079071D, 0.9000000238418579D, 0.899999988079071D);
    public BlockLeek() {
        super();
        setCreativeTab(CommonProxy.tab);
        this.setSoundType(SoundType.PLANT);
        this.setUnlocalizedName("blockleek");
    }


    @Override
    protected boolean canSustainBush(IBlockState state) {

        Block block = state.getBlock();
        return block == BlockLoader.MOMENTOFU || block == BlockLoader.KINUTOFU|| block == BlockLoader.tofuTerrain|| block == BlockLoader.zundatofuTerrain;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos down = pos.down();

        IBlockState soil = world.getBlockState(down);

        return soil.getBlock().canSustainPlant(soil, world, down, EnumFacing.UP, this);

    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BUSH_AABB;
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemLoader.material;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 16;
    }

}