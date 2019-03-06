package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.material.TofuMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.TofuBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockTofu extends BlockTofuBase {
    public static final PropertyInteger DRY = PropertyInteger.create("dry", 0, 7);
    private boolean isFragile = false;
    private boolean canDrain = false;
    private boolean canFreeze = false;
    private int drainRate;
    private TofuType tofuType;

    public BlockTofu(TofuType tofuType) {
        super(TofuMaterial.tofu);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(0.35F);
        this.setResistance(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DRY, Integer.valueOf(0)));

        this.setSoundType(SoundType.CLOTH);
        this.tofuType = tofuType;
    }

    public BlockTofu(TofuType tofuType, Material material) {
        super(material);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(0.3F);
        this.setResistance(0.9F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DRY, Integer.valueOf(0)));

        this.tofuType = tofuType;
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


    @Override
    public Item getItemDropped(IBlockState state, Random par2Random, int par3) {
        return tofuType.getItemStack().getItem();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return tofuType.getItemStack().getMetadata();
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (isFragile) {
            TofuBlockUtils.onFallenUponFragileTofu(worldIn, entityIn, worldIn.getBlockState(pos).getBlock(), fallDistance);
        }
    }


    public int getMaxDry() {
        return 7;
    }

    protected int getDry(IBlockState state) {
        return state.getValue(this.DRY).intValue();
    }

    public IBlockState withDry(int age) {
        return this.getDefaultState().withProperty(DRY, Integer.valueOf(age));
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isUnderWeight(worldIn, pos)) {
            if (canDrain) {
                if (this != BlockLoader.ISHITOFU) {
                    if (rand.nextInt(4)==0) {
                        double d4 = rand.nextBoolean() ? 0.8 : -0.8;
                        double d0 = ((float) pos.getX() + 0.5 + (rand.nextFloat() * d4));
                        double d1 = (double) ((float) pos.getY() + rand.nextFloat());
                        double d2 = ((float) pos.getZ() + 0.5 + rand.nextFloat()* d4);

                        worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }else {
                    if (rand.nextInt(10)==0) {
                        double d4 = rand.nextBoolean() ? 0.8 : -0.8;
                        double d0 = ((float) pos.getX() + 0.5 + (rand.nextFloat() * d4));
                        double d1 = (double) ((float) pos.getY() + rand.nextFloat());
                        double d2 = ((float) pos.getZ() + 0.5 + rand.nextFloat()* d4);

                        worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {

        super.updateTick(par1World, pos, state, par5Random);
        if (isFragile || canDrain) {
            if (isUnderWeight(par1World, pos)) {
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

    public boolean isUnderWeight(World world, BlockPos pos) {
        IBlockState weightBlock = world.getBlockState(pos.up());
        IBlockState baseBlock = world.getBlockState(pos.down());

        boolean isWeightValid = weightBlock != null
                && (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON);

        float baseHardness = baseBlock.getBlockHardness(world, pos.down());
        boolean isBaseValid = baseBlock.isNormalCube() &&
                (baseBlock.getMaterial() == Material.ROCK || baseBlock.getMaterial() == Material.IRON || baseHardness >= 1.0F || baseHardness < 0.0F);

        return isWeightValid && isBaseValid;
    }

    public void drainOneStep(World par1World, BlockPos pos, Random par5Random, IBlockState state) {
        IBlockState state2 = par1World.getBlockState(pos);
        int drainStep = state2.getValue(DRY);

        if (drainStep < 7&&par5Random.nextInt(drainRate) == 0) {
            ++drainStep;

            par1World.setBlockState(pos, this.withDry(drainStep), 2);
        } else if (drainStep == 7 && par5Random.nextInt(2 * drainRate) == 0) {
            IBlockState newBlock;
            if (this == BlockLoader.MOMENTOFU) {
                newBlock = BlockLoader.ISHITOFU.getDefaultState();
            } else if (this == BlockLoader.ISHITOFU) {
                newBlock = BlockLoader.METALTOFU.getDefaultState();
            } else {
                newBlock = this.getDefaultState();
            }

            par1World.setBlockState(pos, newBlock);
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

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DRY, Integer.valueOf(MathHelper.clamp(meta, 0, 7)));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return this.getDry(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DRY);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(this == BlockLoader.ISHITOFU) {
            return true;
        }else {
            return false;
        }
    }
}