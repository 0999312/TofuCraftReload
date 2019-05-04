package cn.mcmod.tofucraft.world.gen.structure.tofufortress;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockTofuChest;
import cn.mcmod.tofucraft.util.TofuLootTables;
import cn.mcmod.tofucraft.world.gen.structure.MapGenTofuFortress;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TofuFortressPiece {
    public static void registerTofuFortressPiece()
    {
        MapGenStructureIO.registerStructure(MapGenTofuFortress.Start.class, "TofuFortress");
        MapGenStructureIO.registerStructureComponent(TofuFortressPiece.TofuTowerTemplate.class, "TTPT");
    }

    public static void generateTower(TemplateManager p_191152_0_, BlockPos p_191152_1_, Rotation p_191152_2_, List<TofuTowerTemplate> p_191152_3_, Random p_191152_4_)
    {
        TofuFortressPiece.Placer woodlandmansionpieces$placer = new TofuFortressPiece.Placer(p_191152_0_, p_191152_4_);
        woodlandmansionpieces$placer.createMansion(p_191152_1_, p_191152_2_, p_191152_3_);
    }

    static class Placer {
        private final TemplateManager templateManager;
        private final Random random;
        private int startX;
        private int startY;

        public Placer(TemplateManager p_i47361_1_, Random p_i47361_2_) {
            this.templateManager = p_i47361_1_;
            this.random = p_i47361_2_;
        }

        public void createMansion(BlockPos blockPos, Rotation rotation, List<TofuFortressPiece.TofuTowerTemplate> list) {
            TofuFortressPiece.PlacementData tofutowerpieces$placementdata = new TofuFortressPiece.PlacementData();
            tofutowerpieces$placementdata.position = blockPos;
            tofutowerpieces$placementdata.rotation = rotation;


            list.add(new TofuFortressPiece.TofuTowerTemplate(this.templateManager,"tofufortress", tofutowerpieces$placementdata.position.offset(EnumFacing.SOUTH, 0), tofutowerpieces$placementdata.rotation));



            list.add(new TofuFortressPiece.TofuTowerTemplate(this.templateManager,"tofufortress_stairs", blockPos.offset(EnumFacing.SOUTH, -7).offset(EnumFacing.WEST,-1).offset(EnumFacing.DOWN,5), tofutowerpieces$placementdata.rotation));

            BlockPos stairpos = tofutowerpieces$placementdata.position.offset(EnumFacing.SOUTH, 2).offset(EnumFacing.WEST,-1).offset(EnumFacing.DOWN,5);
            this.createFirstRoom(stairpos.offset(EnumFacing.UP,-3).offset(EnumFacing.SOUTH, 2),tofutowerpieces$placementdata.rotation,list);
        }

        private void createFirstRoom(BlockPos blockPos, Rotation rotation, List<TofuTowerTemplate> list) {


            list.add(new TofuFortressPiece.TofuTowerTemplate(this.templateManager,"tofufortress_way1", blockPos, rotation));

            if(random.nextFloat() < 0.15){
                BlockPos pos1 = blockPos.offset(EnumFacing.UP,-6).offset(EnumFacing.SOUTH,11);
                createMansion(pos1,rotation,list);
            }




                list.add(new TofuFortressPiece.TofuTowerTemplate(this.templateManager,"tofufortress_room1", blockPos.offset(EnumFacing.EAST, 5), rotation));



                list.add(new TofuFortressPiece.TofuTowerTemplate(this.templateManager,"tofufortress_room2", blockPos.offset(EnumFacing.WEST, 3), rotation));



        }
    }

    static class PlacementData
    {
        public Rotation rotation;
        public BlockPos position;

        private PlacementData()
        {
        }
    }

    public static class TofuTowerTemplate extends StructureComponentTemplate
    {
        private String templateName;
        private Rotation rotation;
        private Mirror mirror;

        public TofuTowerTemplate()
        {
        }

        public TofuTowerTemplate(TemplateManager p_i47355_1_, String p_i47355_2_, BlockPos p_i47355_3_, Rotation p_i47355_4_)
        {
            this(p_i47355_1_, p_i47355_2_, p_i47355_3_, p_i47355_4_, Mirror.NONE);
        }

        public TofuTowerTemplate(TemplateManager p_i47356_1_, String p_i47356_2_, BlockPos p_i47356_3_, Rotation p_i47356_4_, Mirror p_i47356_5_)
        {
            super(0);
            this.templateName = p_i47356_2_;
            this.templatePosition = p_i47356_3_;
            this.rotation = p_i47356_4_;
            this.mirror = p_i47356_5_;
            this.loadTemplate(p_i47356_1_);
        }

        private void loadTemplate(TemplateManager p_191081_1_)
        {
            Template template = p_191081_1_.getTemplate((MinecraftServer)null, new ResourceLocation(TofuMain.MODID,"tofufortress/" + this.templateName));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(false).setRotation(this.rotation).setMirror(this.mirror);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to write subclass data to NBT
         */
        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Template", this.templateName);
            tagCompound.setString("Rot", this.placeSettings.getRotation().name());
            tagCompound.setString("Mi", this.placeSettings.getMirror().name());
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.templateName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.mirror = Mirror.valueOf(tagCompound.getString("Mi"));
            this.loadTemplate(p_143011_2_);
        }


        @Override
        public boolean addComponentParts(@Nonnull World world, @Nonnull Random random, @Nonnull StructureBoundingBox box) {
            super.addComponentParts(world, random, box);

            return true;

        }

        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
        {
            if (function.startsWith("Chest"))
            {
                Rotation rotation = this.placeSettings.getRotation();
                IBlockState iblockstate = BlockLoader.TOFUCHEST.getDefaultState();

                if(function.endsWith("TofuSpace")){
                    if ("ChestWest".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.WEST));
                    } else if ("ChestEast".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.EAST));
                    } else if ("ChestSouth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.SOUTH));
                    } else if ("ChestNorth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.NORTH));
                    }
                    this.generateChest(worldIn, sbb, rand, pos, LootTableList.CHESTS_WOODLAND_MANSION, iblockstate);
                }else  if(function.endsWith("FoodSpace")){
                    if ("ChestWest".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.WEST));
                    } else if ("ChestEast".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.EAST));
                    } else if ("ChestSouth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.SOUTH));
                    } else if ("ChestNorth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.NORTH));
                    }
                    this.generateChest(worldIn, sbb, rand, pos, LootTableList.CHESTS_WOODLAND_MANSION, iblockstate);
                }else {
                    if ("ChestWest".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.WEST));
                    } else if ("ChestEast".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.EAST));
                    } else if ("ChestSouth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.SOUTH));
                    } else if ("ChestNorth".equals(function)) {
                        iblockstate = iblockstate.withProperty(BlockTofuChest.FACING, rotation.rotate(EnumFacing.NORTH));
                    }
                    this.generateChest(worldIn, sbb, rand, pos, TofuLootTables.tofufortress, iblockstate);
                }


            }

        }
    }
}
