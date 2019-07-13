package cn.mcmod.tofucraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockYuba extends Block {

	public BlockYuba() {
		super(Material.CLOTH);
		this.setSoundType(SoundType.CLOTH);
	}
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.00025D, 1.0D);
    }
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
    @Override
    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        if (!this.canBlockStay(par1World, pos))
        {
            par1World.setBlockToAir(pos);
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this), 1);
        player.addExhaustion(0.025F);

        if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            this.dropYuba(worldIn, pos, state);
        }
        else
        {
            if (stack != null && OreDictionary.containsMatch(false, OreDictionary.getOres("stickWood"), new ItemStack(Items.STICK)))
            {
                this.dropYuba(worldIn, pos, state);
            }
        }
    }

    protected void dropYuba(World par1World, BlockPos pos, IBlockState state)
    {
        BlockYuba.spawnAsEntity(par1World, pos, new ItemStack(ItemLoader.material,1,32));
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, BlockPos pos)
    {
        return par1World.getBlockState(pos.down()).getMaterial() == Material.WATER;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
