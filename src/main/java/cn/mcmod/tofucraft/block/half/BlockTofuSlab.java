package cn.mcmod.tofucraft.block.half;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.material.TofuMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTofuSlab extends Block {
    public static final PropertyEnum<EnumBlockTofuSlab> HALF = PropertyEnum.create("half", EnumBlockTofuSlab.class);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockTofuSlab(Material materialIn) {
        super(materialIn);
        this.setDefaultState(blockState.getBaseState().withProperty(HALF, EnumBlockTofuSlab.BOTTOM));
        this.useNeighborBrightness = true;
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
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(HALF).equals(EnumBlockTofuSlab.FULL);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return (state.getValue(HALF).equals(EnumBlockTofuSlab.BOTTOM) && face == EnumFacing.DOWN) || (state.getValue(HALF).equals(EnumBlockTofuSlab.TOP) && face == EnumFacing.UP) || state.getValue(HALF).equals(EnumBlockTofuSlab.FULL);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = getStateFromMeta(meta);
        return state.getValue(HALF).equals(EnumBlockTofuSlab.FULL) ? state : (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? state.withProperty(HALF, EnumBlockTofuSlab.BOTTOM) : state.withProperty(HALF, EnumBlockTofuSlab.TOP));
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(HALF).equals(EnumBlockTofuSlab.FULL) ? 2 : 1;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return state.getValue(HALF).equals(EnumBlockTofuSlab.FULL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumBlockTofuSlab half = state.getValue(HALF);
        switch (half) {
            case TOP:
                return AABB_TOP_HALF;
            case BOTTOM:
                return AABB_BOTTOM_HALF;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(HALF).equals(EnumBlockTofuSlab.FULL) || state.getValue(HALF).equals(EnumBlockTofuSlab.TOP);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (state.getValue(HALF) == EnumBlockTofuSlab.FULL) {
            return BlockFaceShape.SOLID;
        } else if (face == EnumFacing.UP && state.getValue(HALF) == EnumBlockTofuSlab.TOP) {
            return BlockFaceShape.SOLID;
        } else {
            return face == EnumFacing.DOWN && state.getValue(HALF) == EnumBlockTofuSlab.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (!heldItem.isEmpty() && ((state.getValue(HALF).equals(EnumBlockTofuSlab.TOP) && facing.equals(EnumFacing.DOWN)) || (state.getValue(HALF).equals(EnumBlockTofuSlab.BOTTOM) && facing.equals(EnumFacing.UP)))) {
            if (heldItem.getItem() == Item.getItemFromBlock(this)) {
                worldIn.setBlockState(pos, state.withProperty(HALF, EnumBlockTofuSlab.FULL));
                if (!playerIn.capabilities.isCreativeMode)
                    heldItem.setCount(heldItem.getCount() - 1);
                SoundType soundtype = this.getSoundType(state, worldIn, pos, playerIn);
                worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(HALF, EnumBlockTofuSlab.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF).ordinal();
    }

public enum EnumBlockTofuSlab implements IStringSerializable {

    TOP("top"),

    BOTTOM("bottom"),

    FULL("full");

    private final String name;

    EnumBlockTofuSlab(String name) {

        this.name = name;

    }

    @Override
    public String toString() {

        return this.name;

    }

    @Override
    public String getName() {

        return this.name;

    }

    public static EnumBlockTofuSlab byMetadata(int metadata) {
        if (metadata < 0 || metadata >= values().length) {
            metadata = 0;
        }
        return values()[metadata];
    }
}
}