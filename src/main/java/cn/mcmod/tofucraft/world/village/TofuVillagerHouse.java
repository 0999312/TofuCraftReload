package cn.mcmod.tofucraft.world.village;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuChest;
import cn.mcmod.tofucraft.entity.TofuVillages;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.List;
import java.util.Random;

public class TofuVillagerHouse extends StructureVillagePieces.Village {
    public TofuVillagerHouse() {
    }

    public TofuVillagerHouse(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing) {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = p_i45571_4_;
    }

    public static TofuVillagerHouse createPiece(StructureVillagePieces.Start start,
                                                List<StructureComponent> list, Random rand, int minX, int minY, int minZ, EnumFacing facing, int type) {
        StructureBoundingBox box = StructureBoundingBox
                .getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 6, 10, 6, facing);
        return StructureComponent.findIntersecting(list, box) != null ? null : new TofuVillagerHouse(start,
                type, rand, box, facing);
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
     * Mineshafts at the end, it adds Fences...
     */
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        if (this.averageGroundLvl < 0) {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if (this.averageGroundLvl < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(BlockLoader.tofuTerrain.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(BlockLoader.TOFUISHI_BRICK.getDefaultState());
        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(BlockLoader.TOFUISHI_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState air = Blocks.AIR.getDefaultState();
        IBlockState iblockstate3 = BlockLoader.ISHITOFU.getDefaultState();

        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 4, 6, iblockstate1, iblockstate1, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 5, 3, 5, air, air, false);

        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);

        this.setBlockState(worldIn, Blocks.BOOKSHELF.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.BOOKSHELF.getDefaultState(), 1, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.BOOKSHELF.getDefaultState(), 1, 3, 5, structureBoundingBoxIn);

        this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);

        this.setBlockState(worldIn, BlockLoader.SALTFURNACE.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState(), 1, 6, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlockLoader.ISHITOFU.getDefaultState(), 5, 5, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlockLoader.TOFUISHI_BRICK.getDefaultState(), 5, 6, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlockLoader.ISHITOFU.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlockLoader.TOFUISHI_BRICK.getDefaultState(), 5, 6, 1, structureBoundingBoxIn);

        IBlockState iblockstate5 = BlockLoader.TOFUISHI_BRICK_LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
        this.setBlockState(worldIn, iblockstate5, 5, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 4, 5, structureBoundingBoxIn);
        //2F
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 6, 9, 6, iblockstate1, iblockstate1, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 5, 8, 5, air, air, false);

        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 9, 0, iblockstate3, iblockstate3, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 9, 0, iblockstate3, iblockstate3, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 6, 0, 9, 6, iblockstate3, iblockstate3, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 6, 6, 9, 6, iblockstate3, iblockstate3, false);

        this.setBlockState(worldIn, BlockLoader.ISHITOFU.getDefaultState(), 1, 5, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlockLoader.TOFU_LEAVE.getDefaultState(), 1, 6, 5, structureBoundingBoxIn);

        BlockPos blockpos = new BlockPos(this.getXWithOffset(2, 5), this.getYWithOffset(5), this.getZWithOffset(2, 5));

        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, blockpos, TofuLootTables.tofuhouse, BlockLoader.TOFUCHEST.getDefaultState().withProperty(BlockTofuChest.FACING, EnumFacing.EAST));

        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 6, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 6, 0, structureBoundingBoxIn);


        if (this.getBlockStateFromPos(worldIn, 3, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 3, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
            this.setBlockState(worldIn, iblockstate2, 3, 0, -1, structureBoundingBoxIn);

        }


        this.placeTorch(worldIn, EnumFacing.NORTH, 3, 3, 1, structureBoundingBoxIn);
        this.placeTorch(worldIn, EnumFacing.SOUTH, 3, 3, 5, structureBoundingBoxIn);
        this.placeTorch(worldIn, EnumFacing.SOUTH, 3, 7, 5, structureBoundingBoxIn);

        this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 3, 1, 0, EnumFacing.NORTH);


        this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 4, 2);

        for (int l = 0; l < 6; ++l) {
            for (int k = 0; k < 6; ++k) {
                this.clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate1, k, -1, l, structureBoundingBoxIn);
            }
        }

        return true;
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
        ForgeRegistry<VillagerRegistry.VillagerProfession> registry = (ForgeRegistry<VillagerRegistry.VillagerProfession>) ForgeRegistries.VILLAGER_PROFESSIONS;
        int id = registry.getID(TofuVillages.ProfessionTofuCook);
        return id < 0 ? currentVillagerProfession : id;
    }
}