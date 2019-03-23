package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.util.TofuBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
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

public class BlockBarrel extends Block{
    public static final PropertyInteger FERM = PropertyInteger.create("ferm", 0, 8);
    private boolean canFerm = false;
    private int fermRate;
    private ItemStack drop;
    private ItemStack[] in;
    public BlockBarrel(ItemStack drops,ItemStack[] in) {
        super(Material.WOOD);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(0.5F);
        this.setResistance(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FERM, Integer.valueOf(0)));
        this.drop = drops;
        this.in = in;
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
    }

    public BlockBarrel setDrain(int rate) {
        this.canFerm = true;
        this.fermRate = rate;
        this.setTickRandomly(true);
        return this;
    }


    public boolean canFerm(IBlockState state) {
        return this.canFerm && getFerm(state) < getMaxDry();
    }

    public int getDrainRate() {
        return fermRate;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random par2Random, int par3) {
        return ItemLoader.material;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 5;
    }

    public int getMaxDry() {
        return 7;
    }

    public int getFerm(IBlockState state) {
        return state.getValue(this.FERM).intValue();
    }

    public IBlockState withFerm(int age) {
        return this.getDefaultState().withProperty(FERM, Integer.valueOf(age));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
    		int fortune) {
    	if(!canFerm(state)){
    		drops.add(drop);
    		
    	}else{
    		for(ItemStack stack : in){
    			drops.add(stack);
    		}
    	}
    	super.getDrops(drops, world, pos, state, fortune);
    }
    
    

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isUnderWeight(worldIn, pos)) {
            if (canFerm(stateIn)) {
                    if (rand.nextInt(5)==0) {
                        double d4 = rand.nextBoolean() ? 0.8 : -0.8;
                        double d0 = ((float) pos.getX() + 0.5 + (rand.nextFloat() * d4));
                        double d1 = (double) ((float) pos.getY() + rand.nextFloat());
                        double d2 = ((float) pos.getZ() + 0.5 + rand.nextFloat()* d4);

                        worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
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
         if (canFerm(state) && isUnderWeight(par1World, pos)) {
                    this.drainOneStep(par1World, pos, par5Random, state);
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
        int drainStep = state2.getValue(FERM);

        if (drainStep < 7&&par5Random.nextInt(fermRate) == 0) {
            ++drainStep;

            par1World.setBlockState(pos, this.withFerm(drainStep), 2);
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

        return new ItemStack(this);

    }
    
    public static boolean isValidPlaceForDriedTofu(World world, BlockPos pos) {

        return world.getBiomeForCoordsBody(pos).getTemperature(pos) < 0.15F
                && world.getHeight(pos.getX(), pos.getZ()) - 10 < pos.getY()
                && world.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FERM, Integer.valueOf(MathHelper.clamp(meta, 0, 7)));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return this.getFerm(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FERM);
    }

}