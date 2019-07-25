package cn.mcmod.tofucraft.world.gen.structure.tofucastle;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuChest;
import cn.mcmod.tofucraft.entity.EntityTofuGandlem;
import cn.mcmod.tofucraft.entity.EntityTofuMindCore;
import cn.mcmod.tofucraft.entity.EntityTofuTurret;
import cn.mcmod.tofucraft.tileentity.TileEntityTofuChest;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class TofuCastlePiece {

    public static void registerTofuCastlePiece() {
        MapGenStructureIO.registerStructureComponent(TofuCastlePiece.TofuCastleTemplate.class, "TCT");
    }

    public static void generateCore(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<TofuCastleTemplate> list, Random random) {
        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos, rotation, "tofucastle_main"));
        generatesub(templateManager, pos, rotation, list, random);
        generateUnderGround(templateManager, pos, rotation, list, random);
    }

    private static void generateUnderGround(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<TofuCastleTemplate> list, Random random) {
        BlockPos pos1 = new BlockPos(pos.down(19));

        BlockPos pos2 = new BlockPos(pos1.south(23).east(4));
        BlockPos pos3 = new BlockPos(pos1.south(-15).east(4));

        BlockPos pos4 = new BlockPos(pos1.south(23 + 15).down(11));

        generateUnderSub(templateManager, pos2, rotation, list, random);
        generateUnderSub(templateManager, pos3, rotation, list, random);

        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos1, rotation, "tofucastle_undermain"));
        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos4, rotation, "tofucastle_bossroom"));
    }

    private static void generateUnderSub(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<TofuCastleTemplate> list, Random random) {
        //east
        BlockPos pos1 = new BlockPos(pos.south(4).east(15));
        //west
        BlockPos pos2 = new BlockPos(pos.south(4).east(-7));

        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos, rotation, "tofucastle_normalroom"));

        //east
        if (random.nextInt(1) == 0) {
            if (random.nextInt(1) == 0) {
                list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos1, rotation, "tofucastle_foodcontainer"));
            } else {
                list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos1, rotation, "tofucastle_foodcontainer2"));
            }
        } else {
            list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos1, rotation, "tofucastle_eastroom"));
        }


        //west
        if (random.nextInt(2) == 0) {
            list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos2, rotation, "tofucastle_westroom"));
        } else {
            list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos2, rotation, "tofucastle_westbedroom"));
        }
    }

    private static void generatesub(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<TofuCastleTemplate> list, Random random) {
        //generate entrance
        BlockPos pos1 = new BlockPos(pos.south(23).east(4));
        BlockPos pos2 = new BlockPos(pos.south(-15).east(4));
        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos1, rotation, "tofucastle_entrance"));
        list.add(new TofuCastlePiece.TofuCastleTemplate(templateManager, pos2, rotation, "tofucastle_entrance"));
    }

    public static class TofuCastleTemplate extends StructureComponentTemplate {
        private Rotation rotation;
        private Mirror mirror;
        private String templateName;
        private boolean isAleadyBossRoomGen;

        public TofuCastleTemplate() { //Needs empty constructor
        }

        public TofuCastleTemplate(TemplateManager manager, BlockPos pos, Rotation rotation, String templateName) {
            this(manager, pos, rotation, Mirror.NONE, templateName);
        }

        private TofuCastleTemplate(TemplateManager manager, BlockPos pos, Rotation rotation, Mirror mirror, String templateName) {
            super(0);
            this.templatePosition = pos;
            this.rotation = rotation;
            this.mirror = mirror;
            this.templateName = templateName;
            this.loadTemplate(manager);
        }

        private void loadTemplate(TemplateManager manager) {
            Template template = manager.getTemplate(null, new ResourceLocation(TofuMain.MODID, "tofucastle/" + this.templateName));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).setRotation(this.rotation).setMirror(this.mirror);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox box) {
            super.addComponentParts(world, random, box);
            return true;
        }


        @Override
        protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox box) {
            if (function.equals("Chest")) {
                if (box.isVecInside(pos)) {
                    world.setBlockState(pos, BlockLoader.TOFUCHEST.getDefaultState().withProperty(BlockTofuChest.FACING, EnumFacing.WEST), 2);

                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityTofuChest) {
                        ((TileEntityTofuChest) tileEntity).setLootTable(TofuLootTables.tofucastle_normal, rand.nextLong());
                    }
                }
            } else if (function.equals("FoodContainer")) {
                if (box.isVecInside(pos)) {
                    BlockPos blockpos = pos.down();

                    TileEntity tileEntity = world.getTileEntity(blockpos);
                    if (tileEntity instanceof TileEntityTofuChest) {
                        ((TileEntityTofuChest) tileEntity).setLootTable(TofuLootTables.tofucastle_foodcontainer, rand.nextLong());
                    }
                }
            } else if (function.equals("Turret")) {
                EntityTofuTurret entityturret = new EntityTofuTurret(world);
                entityturret.enablePersistence();
                entityturret.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                world.spawnEntity(entityturret);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else if (function.equals("MindCore")) {
                EntityTofuMindCore entitymindcore = new EntityTofuMindCore(world);
                entitymindcore.enablePersistence();
                entitymindcore.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entitymindcore)), (IEntityLivingData) null);
                entitymindcore.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                world.spawnEntity(entitymindcore);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else if (function.equals("Boss")) {
                EntityTofuGandlem entitytofugandlem = new EntityTofuGandlem(world);
                entitytofugandlem.enablePersistence();

                entitytofugandlem.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entitytofugandlem)), (IEntityLivingData) null);
                entitytofugandlem.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                entitytofugandlem.setSleep(true);
                world.spawnEntity(entitytofugandlem);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound compound) {
            super.writeStructureToNBT(compound);
            compound.setBoolean("BossRoom", this.isAleadyBossRoomGen);
            compound.setString("Template", this.templateName);
            compound.setString("Rot", this.placeSettings.getRotation().name());
            compound.setString("Mi", this.placeSettings.getMirror().name());
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound compound, TemplateManager manager) {
            super.readStructureFromNBT(compound, manager);
            this.isAleadyBossRoomGen = compound.getBoolean("BossRoom");
            this.templateName = compound.getString("Template");
            this.rotation = Rotation.valueOf(compound.getString("Rot"));
            this.mirror = Mirror.valueOf(compound.getString("Mi"));
            this.loadTemplate(manager);
        }
    }
}