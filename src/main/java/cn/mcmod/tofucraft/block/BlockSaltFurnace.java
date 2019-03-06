package cn.mcmod.tofucraft.block;

<<<<<<< HEAD
import java.util.Random;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
=======
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltFurnace;
import net.minecraft.block.Block;
>>>>>>> refs/remotes/origin/master
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
<<<<<<< HEAD
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
=======
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
>>>>>>> refs/remotes/origin/master
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

<<<<<<< HEAD
public class BlockSaltFurnace extends BlockContainer
{
=======
import javax.annotation.Nullable;
import java.util.Random;

public class BlockSaltFurnace extends BlockContainer {
>>>>>>> refs/remotes/origin/master
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

<<<<<<< HEAD
    protected BlockSaltFurnace(boolean isBurning)
=======
    public BlockSaltFurnace(boolean isBurning)
>>>>>>> refs/remotes/origin/master
    {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
<<<<<<< HEAD
=======

        if(isBurning){
            this.setLightLevel(0.85F);
        }
>>>>>>> refs/remotes/origin/master
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
<<<<<<< HEAD
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.FURNACE);
=======
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockLoader.SALTFURNACE);
>>>>>>> refs/remotes/origin/master
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
<<<<<<< HEAD
=======
    @Override
>>>>>>> refs/remotes/origin/master
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

<<<<<<< HEAD
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
=======
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
>>>>>>> refs/remotes/origin/master
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
<<<<<<< HEAD

=======
    @Override
>>>>>>> refs/remotes/origin/master
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (this.isBurning)
        {
            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
<<<<<<< HEAD
            double d3 = 0.52D;
=======
>>>>>>> refs/remotes/origin/master
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
            {
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing)
            {
                case WEST:
<<<<<<< HEAD
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
=======
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
            }

            int cauldronStatus = TileEntitySaltFurnace.getCauldronStatus(pos, worldIn);
            float topX = pos.getX() + 0.5F;
            float topY = pos.getY() + 1.05F;
            float topZ = pos.getZ() + 0.5F;
            float randX = rand.nextFloat() * 0.6F - 0.3F;
            float randZ = rand.nextFloat() * 0.6F - 0.3F;

            if (cauldronStatus == 1)
            {
                float steamX = pos.getX() + 0.5F;
                float steamY = pos.getY() + 1.8F;
                float steamZ = pos.getZ() + 0.5F;
                for (int i = 0; i < 3; i++)
                {
                    float steamRandX = rand.nextFloat() * 0.6F - 0.3F;
                    float steamRandZ = rand.nextFloat() * 0.6F - 0.3F;
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (steamX + steamRandX), steamY, (steamZ + steamRandZ), 0.0D, 0.0D, 0.0D);
                }
            }
            else if (cauldronStatus == 2)
            {
                float steamX = pos.getX() + 0.5F;
                float steamY = pos.getY() + 1.8F;
                float steamZ = pos.getZ() + 0.5F;
                float steamRandX = rand.nextFloat() * 0.6F - 0.3F;
                float steamRandZ = rand.nextFloat() * 0.6F - 0.3F;
                double gRand1 = rand.nextGaussian() * 0.01D;
                double gRand2 = rand.nextGaussian() * 0.01D;
                double gRand3 = rand.nextGaussian() * 0.01D;
                worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (steamX + steamRandX), steamY, (steamZ + steamRandZ), gRand1, gRand2, gRand3);
            }
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (topX + randX), topY, (topZ + randZ), 0.0D, 0.0D, 0.0D);

        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

//        if (!worldIn.isRemote)
//        {
//            return true;
//        }
//        else
>>>>>>> refs/remotes/origin/master
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySaltFurnace)
            {
<<<<<<< HEAD
                playerIn.openGui(TofuMain.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
=======
                playerIn.openGui(TofuMain.instance, TofuGuiHandler.ID_SALTFURNACE_Gui, worldIn, pos.getX(), pos.getY(), pos.getZ());
                //playerIn.addStat(TcStatList.FURNACE_INTERACTION);
>>>>>>> refs/remotes/origin/master
            }

            return true;
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
            worldIn.setBlockState(pos, BlockLoader.SALTFURNACE_LIT.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlockLoader.SALTFURNACE_LIT.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, BlockLoader.SALTFURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlockLoader.SALTFURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> refs/remotes/origin/master
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySaltFurnace();
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
<<<<<<< HEAD
=======
    @Override
>>>>>>> refs/remotes/origin/master
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

<<<<<<< HEAD
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
=======
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
>>>>>>> refs/remotes/origin/master
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

<<<<<<< HEAD
            if (tileentity instanceof TileEntitySaltFurnace)
            {
                ((TileEntitySaltFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
=======
            if (tileentity instanceof TileEntityFurnace)
            {
                ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
>>>>>>> refs/remotes/origin/master
            }
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
<<<<<<< HEAD
=======
    @Override
>>>>>>> refs/remotes/origin/master
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySaltFurnace)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySaltFurnace)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

<<<<<<< HEAD
=======

>>>>>>> refs/remotes/origin/master
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
<<<<<<< HEAD
        return new ItemStack(Blocks.FURNACE);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
=======
        return new ItemStack(BlockLoader.SALTFURNACE);
    }


>>>>>>> refs/remotes/origin/master
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

<<<<<<< HEAD
}
=======
    public static void updateFurnaceBlockState(boolean b, World worldObj, BlockPos pos) {
        IBlockState blockstate = worldObj.getBlockState(pos);
        TileEntity var6 = worldObj.getTileEntity(pos);

        keepInventory = true;

        if (b)
        {
            worldObj.setBlockState(pos, BlockLoader.SALTFURNACE_LIT.getDefaultState().withProperty(BlockSaltFurnace.FACING, blockstate.getValue(BlockSaltFurnace.FACING)));
        }
        else
        {
            worldObj.setBlockState(pos, BlockLoader.SALTFURNACE.getDefaultState().withProperty(BlockSaltFurnace.FACING, blockstate.getValue(BlockSaltFurnace.FACING)));

            Block blockAbove = worldObj.getBlockState(pos.up()).getBlock();
            if (blockAbove == Blocks.FIRE)
            {
                worldObj.setBlockToAir(pos.up());
            }
        }

        keepInventory = false;
        //worldObj.setBlockState(pos, var5, 2);

        if (var6 != null)
        {
            var6.validate();
            worldObj.setTileEntity(pos, var6);
        }
    }
}
>>>>>>> refs/remotes/origin/master
