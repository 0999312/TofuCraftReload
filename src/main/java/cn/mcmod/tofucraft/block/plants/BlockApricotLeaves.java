package cn.mcmod.tofucraft.block.plants;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockApricotLeaves extends BlockLeaves {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);

    public BlockApricotLeaves() {
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0).withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, true));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {

            int age = state.getValue(STAGE).intValue();
            if (age == 3) {
                IBlockState back = state.withProperty(STAGE, 1);

                worldIn.setBlockState(pos, back, 2);

                spawnAsEntity(worldIn, pos, new ItemStack(ItemLoader.foodsetContain, 1, 14));
            }
            return true;
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

        super.updateTick(worldIn, pos, state, rand);
        if (state != null && state.getBlock() instanceof BlockApricotLeaves) {

            int age = state.getValue(STAGE).intValue();
            if (age < 3) {

                if (age >= 0 && age < 3) {
                    age++;
                    IBlockState next = state.withProperty(STAGE, age);

                    worldIn.setBlockState(pos, next, 2);
                }
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        int i = meta & 3;
        boolean d = (meta & 4) > 0;
        boolean c = (meta & 8) > 0;

        IBlockState state = this.getDefaultState().withProperty(STAGE, i).withProperty(DECAYABLE, d).withProperty(CHECK_DECAY, c);

        return state;
    }

    // state
    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = state.getValue(STAGE);

        if (state.getValue(DECAYABLE)) {
            i += 4;
        }

        if (state.getValue(CHECK_DECAY)) {
            i += 8;
        }
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{
                STAGE,
                DECAYABLE,
                CHECK_DECAY
        });
    }

    @Override
    public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(BlockLoader.APRICOT_SAPLING);
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        int age = state.getValue(STAGE).intValue();
        if (age == 3) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemLoader.foodsetContain, 1, 14));
        }
    }

    public IBlockState getGrownState() {
        return this.getDefaultState().withProperty(STAGE, 3);
    }

    public IBlockState setGrownState(IBlockState state) {
        return state.withProperty(STAGE, 0);
    }

    @Override
    public ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this, 1);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1);
    }

    @Override
    public int getSaplingDropChance(IBlockState state) {
        return 20;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1));
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 30;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube(IBlockState state) {
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return (Minecraft.getMinecraft().gameSettings.fancyGraphics || blockAccess.getBlockState(pos.offset(side)).getBlock() != this) && Blocks.STONE.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

}