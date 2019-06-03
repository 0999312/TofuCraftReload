package cn.mcmod.tofucraft.block;

import cn.mcmod.tofucraft.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import javax.annotation.Nullable;

public class BlockNattoBed extends Block{
    public static final PropertyInteger FERM = PropertyInteger.create("ferm", 0, 7);
    private boolean canFerm = false;
    private int fermRate;
    private ItemStack drop;
    private ItemStack[] in;
    public BlockNattoBed(ItemStack drops,ItemStack[] in) {
        super(Material.WOOD);
        this.setCreativeTab(CommonProxy.tab);
        this.setHardness(0.25F);
        this.setResistance(0.4F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FERM, Integer.valueOf(0)));
        this.drop = drops;
        this.in = in;
        this.setSoundType(SoundType.CLOTH);
        this.setHarvestLevel("axe", 0);
    }

    public BlockNattoBed setDrain(int rate) {
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
    public int quantityDropped(IBlockState state, int fortune, Random random) {
    	return 0;
    }
    
    public int getMaxDry() {
        return 7;
    }
    
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    	entityIn.setInWeb();
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entityLiving = (EntityLivingBase)entityIn;
            entityLiving.addPotionEffect(new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "slowness")), 300, 2));
        }
    }

    public int getFerm(IBlockState state) {
        return state.getValue(FERM).intValue();
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
        IBlockState baseBlock = world.getBlockState(pos.down());
        return baseBlock.isNormalCube();
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