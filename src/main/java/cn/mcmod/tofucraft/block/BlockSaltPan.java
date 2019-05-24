package cn.mcmod.tofucraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.tileentity.TileEntitySaltPan;
import cn.mcmod.tofucraft.util.TileScanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSaltPan extends BlockContainer {
	
	public static final PropertyEnum<Stat> STATUS = PropertyEnum.create("stat", Stat.class);
    /** Whether this fence connects in the northern direction */
    public static final PropertyBool NORTH = PropertyBool.create("north");
    /** Whether this fence connects in the eastern direction */
    public static final PropertyBool EAST = PropertyBool.create("east");
    /** Whether this fence connects in the southern direction */
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    /** Whether this fence connects in the western direction */
    public static final PropertyBool WEST = PropertyBool.create("west");
	
	public static AxisAlignedBB SALT_PAN_AABB = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f * 6, 1.0f);

    public BlockSaltPan()
    {
        super(Material.WOOD);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.WOOD);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SALT_PAN_AABB;
    }
    
    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState stat)
    {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
    	return new TileEntitySaltPan();
    }
    
    public Stat getStat(IBlockAccess world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() == this)
        {
            IBlockState meta = world.getBlockState(pos);
            return this.getStat(meta);
        }
        else
        {
            return Stat.NA;
        }
    }
    
    public Stat getStat(IBlockState meta) {
    	
        if (meta.getBlock() == this)
        {
        	return meta.getValue(STATUS);
        }
        else
        {
            return Stat.NA;
        }
    }
    
    public Stat getStat(int meta)
    {
    	return Stat.byMetadata(meta);
    }
    
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        return block == this;
    }
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		  if (worldIn.isRemote)
	        {
	            return true;
	        }
	        else
	        {
	            ItemStack itemHeld = playerIn.getHeldItem(hand);
	            Stat stat = this.getStat(worldIn, pos);
	            if (stat == Stat.EMPTY && itemHeld != null && itemHeld.getItem() == Items.WATER_BUCKET)
	            {
	                if (!playerIn.capabilities.isCreativeMode)
	                {
	            	  playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
	                }

	                TileScanner tileScanner = new TileScanner(worldIn, pos);
	                tileScanner.scan(1, TileScanner.Method.fullSimply, new TileScanner.Impl<Object>()
	                {
	                    @Override
	                    public void apply(World world, BlockPos pos)
	                    {
	                        if (BlockSaltPan.this.getStat(world, pos) == Stat.EMPTY)
	                        {
	                            world.setBlockState(pos, BlockLoader.SALTPAN.getDefaultState().withProperty(STATUS, Stat.WATER), 3);
	                        }
	                    }
	                });

	                worldIn.setBlockState(pos, BlockLoader.SALTPAN.getDefaultState().withProperty(STATUS, Stat.WATER), 3);

	                return true;
	            }
	            else if (stat == Stat.BITTERN && itemHeld != null && itemHeld.getItem() == Items.GLASS_BOTTLE)
	            {
	                ItemStack nigari = new ItemStack(ItemLoader.nigari);
	                if (itemHeld.getCount() == 1)
	                {
	                    playerIn.setHeldItem(hand, nigari);
	                }
	                else
	                {
	                    if (!playerIn.inventory.addItemStackToInventory(nigari))
	                    {
	                        worldIn.spawnEntity(new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 0.5D, nigari));
	                    }
	                    
	                    itemHeld.shrink(1);
	                }
	                worldIn.setBlockState(pos, BlockLoader.SALTPAN.getDefaultState().withProperty(STATUS, Stat.EMPTY), 3);

	                return true;
	            }
	            else if (stat == Stat.BITTERN && itemHeld == null)
	            {
	                worldIn.setBlockState(pos, BlockLoader.SALTPAN.getDefaultState().withProperty(STATUS, Stat.EMPTY), 3);
	            }
	            else if (stat == Stat.SALT)
	            {
	                ItemStack salt = new ItemStack(ItemLoader.material, 1,0);

	                float f = 0.7F;
	                double d0 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                double d1 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
	                double d2 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, salt);
	                entityitem.setPickupDelay(10);
	                worldIn.spawnEntity(entityitem);

	                worldIn.setBlockState(pos, BlockLoader.SALTPAN.getDefaultState().withProperty(STATUS, Stat.BITTERN), 3);

	                return true;
	            }
	            return false;
	        }
	}
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState blockState, Random rand)
    {
        super.updateTick(worldIn, pos, blockState, rand);
        
        Stat stat = blockState.getValue(STATUS);
        int l = stat.getMeta();

        if (stat == Stat.WATER)
        {
            float f = this.calcAdaptation(worldIn, pos);

            if (f > 0.0F && rand.nextInt((int) (25.0F / f) + 1) == 0)
            {
                ++l;
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(STATUS, Stat.byMetadata(l)), 2);
            }
        }
    }
    
    private float calcAdaptation(World world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        boolean isUnderTheSun = world.canBlockSeeSky(pos);
        boolean isRaining = world.isRaining();
        boolean isDaytime = world.getWorldTime() % 24000 < 12000;
        float humidity = biome.getRainfall();
        float temperature = biome.getTemperature(pos);
        float rate;

        if (!isUnderTheSun || isRaining)
        {
            rate = 0.0F;
        }
        else
        {
            rate = isDaytime ? 2.0F : 1.0F;
            rate *= humidity < 0.2D ? 4.0D : humidity < 0.7D ? 2.0D : humidity < 0.9 ? 1.0D : 0.5D;
            rate *= temperature < 0.0D ? 1.0D : temperature < 0.6D ? 1.5D : temperature < 1.0D ? 2.0D : 4.0D;
        }
        return rate;
    }
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
    	BlockPos posDown = pos.down();
    	return worldIn.getBlockState(posDown).isSideSolid(worldIn, posDown, EnumFacing.UP);
    }
    
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north())))
        		.withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east())))
        		.withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south())))
        		.withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west())));

    }

	// ========  property  ========
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STATUS, Stat.byMetadata(meta));
	}
    
    /**
     * Convert the BlockState into the correct metadata value
     */
	@Override
    public int getMetaFromState(IBlockState state)
    {
		return state.getValue(STATUS).getMeta();
    }
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {STATUS, NORTH, EAST, WEST, SOUTH});
    }
    
	
    public static enum Stat implements IStringSerializable
    {
        EMPTY(0, "empty"),
        WATER(1, "water"),
        SALT(2, "salt"),
        BITTERN(3, "bittern"),
        NA(4, "na");
    	
    	private static final Stat[] META_LOOKUP = new Stat[values().length];
    	private int meta;
    	private String name;
    	
    	Stat(int meta, String name) {
    		this.meta = meta;
    		this.name = name;
    	}
    	
        public static Stat byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }
    	
    	public int getMeta() {
    		return this.meta;
    	}
    	
		@Override
		public String getName() {
			return this.name;
		}
		
		
        static
        {
            for (Stat enumtype : values())
            {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
        
    }
}
