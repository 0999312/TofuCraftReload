package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.material.TofuMaterial;
import cn.mcmod.tofucraft.util.TofuBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTofu extends BlockTofuBase {
    public static final PropertyInteger DRY = PropertyInteger.create("dry", 0, 7);
    private boolean isFragile = false;
    private boolean canDrain = false;
    private boolean canFreeze = false;
    private int drainRate;

    public BlockTofu() {
        super(TofuMaterial.tofu);
        this.setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getDryProperty(), Integer.valueOf(0)));
        this.setHardness(0.3F);
        this.setResistance(0.9F);
        this.setSoundType(SoundType.CLOTH);
    }

    public BlockTofu(Material material) {
        super(material);
        this.setCreativeTab(CommonProxy.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getDryProperty(), Integer.valueOf(0)));
        this.setHardness(0.3F);
        this.setResistance(0.9F);

        if (material == Material.IRON) {
            this.setSoundType(SoundType.METAL);
        }
    }

    public BlockTofu setFragile() {
        isFragile = true;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setDrain(int rate) {
        this.canDrain = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setFreeze(int rate) {
        this.canFreeze = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    public boolean canDrain() {
        return this.canDrain;
    }

    public int getDrainRate() {
        return drainRate;
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return setTofuItem();
    }

    
    public ItemStack setTofuItem() {
        //This is not complete yet
        if (BlockLoader.KINUTOFU == this) {
            return new ItemStack(ItemLoader.tofu_food);
        } else if (BlockLoader.MOMENTOFU == this) {
            return new ItemStack(ItemLoader.tofu_food, 1, 1);
        } else if (BlockLoader.ISHITOFU == this) {
            return new ItemStack(ItemLoader.tofu_food, 1, 2);
        } else if (BlockLoader.METALTOFU == this) {
            return new ItemStack(ItemLoader.tofu_material);
        }
        return null;
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
    	 if (isFragile)
         {
             TofuBlockUtils.onFallenUponFragileTofu(worldIn, entityIn, worldIn.getBlockState(pos).getBlock(), fallDistance);
         }
    }

    protected PropertyInteger getDryProperty() {
        return DRY;
    }

    public int getMaxDry() {
        return 7;
    }

    protected int getDry(IBlockState state) {
        return state.getValue(this.getDryProperty()).intValue();
    }

    public IBlockState withDry(int age) {
        return this.getDefaultState().withProperty(this.getDryProperty(), Integer.valueOf(age));
    }

    public boolean isMaxDry(IBlockState state) {
        return state.getValue(this.getDryProperty()).intValue() >= this.getMaxDry();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
        super.updateTick(par1World, pos, state, par5Random);

        if (isFragile || canDrain) {
            if (isUnderWeight(par1World, pos.getX(), pos.getY(), pos.getZ())) {
                if (isFragile) {
                    dropBlockAsItemWithChance(par1World, pos, state, 0.4f, 0);
                    par1World.setBlockToAir(pos);
                } else if (canDrain) {
                    this.drainOneStep(par1World, pos, par5Random, state);
                }
            }
        }
        if (canFreeze) {
            /*if (isValidPlaceForDriedTofu(par1World, pos.getX(), pos.getY(),pos.getZ()))
            {
                int freezeStep = par1World.get(pos.getX(), pos.getY(),pos.getZ());
                if (freezeStep < 7 && par5Random.nextInt((drainRate)) == 0)
                {
                    ++freezeStep;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, freezeStep, 2);
                }
                else if (freezeStep == 7)
                {
                    Block newBlock = TcBlocks.tofuDried;
                    par1World.setBlock(par2, par3, par4, newBlock, 0, 2);
                }
            }*/
        }
    }

    public boolean isUnderWeight(World world, int x, int y, int z) {
        BlockPos weightBlock = new BlockPos(x, y + 1, z);
        BlockPos baseBlock = new BlockPos(x, y - 1, z);

        boolean isWeightValid = weightBlock != null
                && (world.getBlockState(weightBlock).getMaterial() == Material.ROCK || world.getBlockState(weightBlock).getMaterial() == Material.IRON);

        float baseHardness = world.getBlockState(baseBlock).getBlockHardness(world, baseBlock);
        boolean isBaseValid = world.getBlockState(baseBlock).isNormalCube() &&
                (world.getBlockState(baseBlock).getMaterial() == Material.ROCK || world.getBlockState(baseBlock).getMaterial() == Material.IRON || baseHardness >= 1.0F || baseHardness < 0.0F);

        return isWeightValid && isBaseValid;
    }

    public void drainOneStep(World par1World, BlockPos pos, Random par5Random, IBlockState state) {
        int drainStep = getDry(state);

        if (drainStep < 7 && par5Random.nextInt(drainRate) == 0) {
            ++drainStep;
            setDrain(drainRate);
        } else if (drainStep == 7 && par5Random.nextInt(2 * drainRate) == 0) {
            Block newBlock;
            if (this == BlockLoader.MOMENTOFU) {
                newBlock = BlockLoader.ISHITOFU;
            } else if (this == BlockLoader.ISHITOFU) {
                newBlock = BlockLoader.METALTOFU;
            } else {
                newBlock = this;
            }

            par1World.setBlockState(pos, newBlock.getDefaultState());
        }

    }

//    @SideOnly(Side.CLIENT)
//
//    /**
//     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
//     */
//    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
//    {
//        for (int var4 = 0; var4 < 3; ++var4)
//        {
//            par3List.add(new ItemStack(par1, 1, var4));
//        }
//    }

    public static boolean isValidPlaceForDriedTofu(World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        return world.getBiomeForCoordsBody(pos).getTemperature(pos) < 0.15F
                && world.getHeight(x, z) - 10 < y
                && world.isAirBlock(pos.up());
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.withDry(meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return this.getDry(state);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DRY);
    }
}