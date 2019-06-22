package cn.mcmod.tofucraft.world.biome;

import cn.mcmod.tofucraft.entity.EntityTofuCow;

public class BiomeTofuBuildings extends BiomeTofu {
	
    public BiomeTofuBuildings(BiomeProperties property)
    {
        super(property);
        this.treesPerChunk = -999;
        this.tofuPerChunk = 5;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityTofuCow.class, 3, 4, 4));
    }
}
