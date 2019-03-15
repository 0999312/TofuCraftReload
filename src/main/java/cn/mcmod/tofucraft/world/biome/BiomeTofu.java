package cn.mcmod.tofucraft.world.biome;

import java.util.Random;

import cn.mcmod.tofucraft.entity.EntityTofuSlime;
import cn.mcmod.tofucraft.world.gen.future.WorldGenTofuBuilding;
import cn.mcmod.tofucraft.world.gen.future.WorldGenTofuTrees;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenerator;

import cn.mcmod.tofucraft.block.BlockLoader;

public class BiomeTofu extends Biome {

    public static final float height_Tofu_Base = 0.05F;
    public static final float height_Tofu_Variation = 0.1F;
    public static final float height_ShallowWaters_Base = -0.5F;
    public static final float height_ShallowWaters_Variation = 0.0F;

    protected int treesPerChunk;
    protected int tofuPerChunk;
    protected int maxGrassPerChunk;
    protected int chanceOfLeeks;
    protected boolean generateLakes;
    
    protected WorldGenTofuTrees worldGeneratorTrees;
    protected WorldGenTofuBuilding worldGeneratorTofuBuilding;
    
    public BiomeTofu(String name) {
    	this(new BiomeProperties(name)
        		.setBaseHeight(BiomeTofu.height_Tofu_Base)
        		.setHeightVariation(BiomeTofu.height_Tofu_Variation));
    }
    
    public BiomeTofu(BiomeProperties property)
    {
        super(property);
        
        this.worldGeneratorTrees = new WorldGenTofuTrees(false);
        this.worldGeneratorTofuBuilding = new WorldGenTofuBuilding(false);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTofuSlime.class, 2, 2, 3));

        this.topBlock = BlockLoader.tofuTerrain.getDefaultState();
        this.fillerBlock = BlockLoader.tofuTerrain.getDefaultState();


        this.treesPerChunk = 0;
        this.tofuPerChunk = 0;
        this.maxGrassPerChunk = 1;
        this.chanceOfLeeks = 50;
        this.generateLakes = true;

//        TcBiomes.TOFU_BIOME_LIST[localBiomeId] = this;
    }
    
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenAbstractTree genBigTreeChance(Random rand) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
    
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        int i, j, k, l, i1;

        i = this.treesPerChunk;
        if (rand.nextInt(10) == 0)
        {
            ++i;
        }
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	for (j = 0; j < i; ++j)
        	{
            	k = pos.getX() + rand.nextInt(16) + 8;
            	l = pos.getZ() + rand.nextInt(16) + 8;
            	mutable.setPos( k, worldIn.getHeight(k, l), l);
            	WorldGenerator worldgenerator = this.genBigTreeChance(rand);
            	worldgenerator.generate(worldIn, rand, mutable);
        	}
        }
        if (rand.nextInt(this.chanceOfLeeks) == 0)
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	
            for (j = 0; j < maxGrassPerChunk; j++)
            {
                k = pos.getX() + rand.nextInt(16) + 8;
                l = rand.nextInt(128);
                i1 = pos.getZ() + rand.nextInt(16) + 8;
                mutable.setPos(k, l, i1);
                
                WorldGenerator var6 = new WorldGenBush(BlockLoader.LEEK);
                var6.generate(worldIn, rand, mutable);
            }
        }

        if (this.generateLakes)
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	
            for (j = 0; j < 50; ++j)
            {
                k = pos.getX() + rand.nextInt(16) + 8;
                l = rand.nextInt(rand.nextInt(120) + 8);
                i1 = pos.getZ() + rand.nextInt(16) + 8;
                mutable.setPos(k, l, i1);
                (new WorldGenLiquids(BlockLoader.SOYMILK)).generate(worldIn, rand, mutable);
            }
        }

        i = this.tofuPerChunk;
        if (rand.nextInt(70) == 0)
        {
            ++i;
        }
        {
        	BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        	for (j = 0; j < i; ++j)
        	{
            	k = pos.getX() + rand.nextInt(16) + 8;
            	l = pos.getZ() + rand.nextInt(16) + 8;
            	mutable.setPos(k, worldIn.getHeight(k, l), l);
            	WorldGenerator worldgenerator = this.worldGeneratorTofuBuilding;
            	worldgenerator.generate(worldIn, rand, mutable);
        	}
        }
        
    }
    
    
    
    
    
    
}
