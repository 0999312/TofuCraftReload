package cn.mcmod.tofucraft.world.village;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.List;
import java.util.Random;

public class VillagerCreationTofu implements VillagerRegistry.IVillageCreationHandler {
    public static void registerComponents() {
        MapGenStructureIO.registerStructureComponent(TofuVillagerHouse.class,
                TofuMain.MODID + ":tofu_house");

    }

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
        return new PieceWeight(TofuVillagerHouse.class, 45, MathHelper.getInt(random, i, 4 + i));
    }

    @Override
    public Class<?> getComponentClass() {
        return TofuVillagerHouse.class;
    }

    @Override
    public Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1,
                                  int p2, int p3, EnumFacing facing, int p5) {
        return TofuVillagerHouse.createPiece(startPiece, pieces, random, p1, p2, p3, facing, p5);
    }

}