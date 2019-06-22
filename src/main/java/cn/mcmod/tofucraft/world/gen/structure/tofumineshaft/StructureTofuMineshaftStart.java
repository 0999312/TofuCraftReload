package cn.mcmod.tofucraft.world.gen.structure.tofumineshaft;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class StructureTofuMineshaftStart extends StructureStart
{
    private MapGenTofuMineshaft.Type mineShaftType;

    public StructureTofuMineshaftStart()
    {
    }

    public StructureTofuMineshaftStart(World p_i47149_1_, Random p_i47149_2_, int p_i47149_3_, int p_i47149_4_, MapGenTofuMineshaft.Type p_i47149_5_)
    {
        super(p_i47149_3_, p_i47149_4_);
        this.mineShaftType = p_i47149_5_;
        StructureTofuMineshaftPieces.Room structuremineshaftpieces$room = new StructureTofuMineshaftPieces.Room(0, p_i47149_2_, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2, this.mineShaftType);
        this.components.add(structuremineshaftpieces$room);
        structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, p_i47149_2_);
        this.updateBoundingBox();

        if (p_i47149_5_ == MapGenTofuMineshaft.Type.BUILDING)
        {
            int j = p_i47149_1_.getSeaLevel() - this.boundingBox.maxY + this.boundingBox.getYSize() / 2 - -5;
            this.boundingBox.offset(0, j, 0);

            for (StructureComponent structurecomponent : this.components)
            {
                structurecomponent.offset(0, j, 0);
            }
        }
        else
        {
            this.markAvailableHeight(p_i47149_1_, p_i47149_2_, 10);
        }
    }
}