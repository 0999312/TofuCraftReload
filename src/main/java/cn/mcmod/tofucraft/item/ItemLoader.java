package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TileEntityRenderHelper;
import cn.mcmod.tofucraft.item.tfitem.ItemGrowingTofu;
import cn.mcmod.tofucraft.item.tfitem.ItemTofuForceCore;
import cn.mcmod.tofucraft.material.TofuArmorMaterial;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.JSON_Creator;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class ItemLoader {
    public static EnumMap<TofuType, ItemStack> tofuItems = Maps.newEnumMap(TofuType.class);

    public static ItemFoodBasic tofu_food = new ItemFoodBasic("tofu_food", 64,
            new int[]{2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 3, 5},
            new float[]{0.1F, 0.1F, 0.4F, 0.2F, 0.2F, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.8F},
            TofuMain.MODID + "." + "tofukinu",//0
            TofuMain.MODID + "." + "tofumomen",//1
            TofuMain.MODID + "." + "tofuishi",//2
            TofuMain.MODID + "." + "tofugrilled",//3
            TofuMain.MODID + "." + "tofufriedpouch",//4
            TofuMain.MODID + "." + "tofufried",//5
            TofuMain.MODID + "." + "tofuegg",//6
            TofuMain.MODID + "." + "tofuannin",//7
            TofuMain.MODID + "." + "tofusesame",//8
            TofuMain.MODID + "." + "tofuzunda",//9
            TofuMain.MODID + "." + "tofustrawberry",//10
            TofuMain.MODID + "." + "tofumiso"//11
    ).setFastEat();
    public static ItemFood tofu_hell = (ItemFood) new ItemFood(2, 0.2f, false)
            .setPotionEffect(new PotionEffect(Potion.getPotionById(12), 30, 0), 1.0F)
            .setUnlocalizedName(TofuMain.MODID + "." + "tofuhell");
    public static ItemBase tofu_material = new ItemBase("tofu_material", 64, TofuMain.MODID + "." + "tofumetal",
            TofuMain.MODID + "." + "tofudiamond",
            TofuMain.MODID + "." + "tofudried");
    public static ItemFoodBasic foodset = new ItemFoodBasic("foodset", 64,
            new int[]{6, 5, 4, 5, 6, 6, 2, 10, 12, 2, 5, 4, 6, 5, 4, 4, 6, 16, 20, 2, 8, 4, 1, 3, 6, 8, 3, 4},
            new float[]{0.4f, 0.3f, 0.4f, 0.6f, 0.8f, 0.8f, 0.5f, 0.2f, 0.8f, 0.15f, 0.6f, 0.4f, 0.6f, 0.8f, 0.6f, 0.6f, 0.6f, 0.6f, 1f, 0.2f, 0.6f, 0.4f, 0.1f, 0.3f, 0.5f, 0.4f, 1f, 0.3f},
            TofuMain.MODID + "." + "tofuchikuwa",//0
            TofuMain.MODID + "." + "oage",//1
            TofuMain.MODID + "." + "onigiri",//2
            TofuMain.MODID + "." + "onigirisalt",//3
            TofuMain.MODID + "." + "yakionigirimiso",//4
            TofuMain.MODID + "." + "yakionigirishoyu",//5
            TofuMain.MODID + "." + "sprouts",//6
            TofuMain.MODID + "." + "ricetofu",
            TofuMain.MODID + "." + "tofuhamburg",
            TofuMain.MODID + "." + "tofucookie",
            TofuMain.MODID + "." + "inari",//10
            TofuMain.MODID + "." + "tofufishraw",
            TofuMain.MODID + "." + "tofufishcooked",
            TofuMain.MODID + "." + "kinakomochi",
            TofuMain.MODID + "." + "kinakomanju",
            TofuMain.MODID + "." + "chikuwa",
            TofuMain.MODID + "." + "tofusteak",
            TofuMain.MODID + "." + "tofuhamburgt",
            TofuMain.MODID + "." + "tofuhamburgta",
            TofuMain.MODID + "." + "tofuminced",
            TofuMain.MODID + "." + "ricesoborotofu",//20
            TofuMain.MODID + "." + "okarastick",
            TofuMain.MODID + "." + "edamameboiled",
            TofuMain.MODID + "." + "saltymelon",
            TofuMain.MODID + "." + "chillstick",
            TofuMain.MODID + "." + "tttburger",
            TofuMain.MODID + "." + "fukumeni",
            TofuMain.MODID + "." + "meatwrapped_yuba");
    public static ItemBase material = new ItemBase("material", 64,
            TofuMain.MODID + "." + "salt",
            TofuMain.MODID + "." + "kouji",//1
            TofuMain.MODID + "." + "miso",//2
            TofuMain.MODID + "." + "edamame",//3
            TofuMain.MODID + "." + "zunda",//4
            TofuMain.MODID + "." + "barrelempty",//5
            TofuMain.MODID + "." + "soybeansparched",//6
            TofuMain.MODID + "." + "kinako",//7
            TofuMain.MODID + "." + "natto",//8
            TofuMain.MODID + "." + "apricotseed",//9
            TofuMain.MODID + "." + "filtercloth",//10
            TofuMain.MODID + "." + "okara",//11
            TofuMain.MODID + "." + "mincedpotato",//12
            TofuMain.MODID + "." + "starchraw",//13
            TofuMain.MODID + "." + "starch",//14
            TofuMain.MODID + "." + "kyoninso",//15
            TofuMain.MODID + "." + "leek",//16
            TofuMain.MODID + "." + "zundama",//17
            TofuMain.MODID + "." + "tofugem",//18
            TofuMain.MODID + "." + "tofudiamondnugget",//19
            TofuMain.MODID + "." + "tofuhamburgraw",//20
            TofuMain.MODID + "." + "tfcapacitor",//21
            TofuMain.MODID + "." + "tfcircuit",//22
            TofuMain.MODID + "." + "tfcoil",//23
            TofuMain.MODID + "." + "tfoscillator",//24
            TofuMain.MODID + "." + "advtofugem",//25
            TofuMain.MODID + "." + "activatedtofugem",//26
            TofuMain.MODID + "." + "activatedhelltofu",//27
            TofuMain.MODID + "." + "tofusomen",//28
            TofuMain.MODID + "." + "glassbowl",//29
            TofuMain.MODID + "." + "lemon",//30
            TofuMain.MODID + "." + "chill",//31
            TofuMain.MODID + "." + "yuba"//32
    );
    public static Item koujiBase = new ItemKoujiBase();
    public static Item RollingPin = new ItemRolling();
    public static ItemFood zundaMochi = (ItemFood) new ItemFood(3, 0.8f, false)
            .setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 2), 1.0F)
            .setUnlocalizedName(TofuMain.MODID + "." + "zundamochi");
    public static ItemFoodBasic tsuyuBowl = new ItemFoodContain("tsuyuBowl_glass", 1,
            new int[]{2},
            new float[]{0.1f},
            new String[]{
                    TofuMain.MODID + "." + "tsuyubowl_glass"},
            new ItemStack[]{
                    new ItemStack(material, 1, 29),
            }
    );
    public static ItemFoodBasic foodsetContain = new ItemFoodContain("foodsetcontain", 1,
            new int[]{16, 8, 5, 6, 8, 3, 20, 20, 3, 5, 10, 6, 8, 3, 7, 6, 5, 8, 9},
            new float[]{0.5f, 0.4f, 0.3f, 0.5f, 0.8f, 0.3f, 1F, 1f, 0.3f, 0.6f, 0.7f, 0.6f, 0.8f, 0.3f, 0.6f, 0.5f, 0.4f, 0.5f, 0.5f},
            new String[]{
                    TofuMain.MODID + "." + "mabodofu",
                    TofuMain.MODID + "." + "moyashiitame",
                    TofuMain.MODID + "." + "moyashiohitashi",
                    TofuMain.MODID + "." + "hiyayakko_glass",
                    TofuMain.MODID + "." + "nattohiyayakko_glass",
                    TofuMain.MODID + "." + "tofusomenbowl_glass",
                    TofuMain.MODID + "." + "tastystew",
                    TofuMain.MODID + "." + "tastybeefstew",
                    TofuMain.MODID + "." + "yudofu",
                    TofuMain.MODID + "." + "misosoup",
                    TofuMain.MODID + "." + "misodengaku",
                    TofuMain.MODID + "." + "nikujaga",
                    TofuMain.MODID + "." + "agedashitofu",
                    TofuMain.MODID + "." + "koyadofustew",
                    TofuMain.MODID + "." + "apricot",
                    TofuMain.MODID + "." + "soborotofusaute",
                    TofuMain.MODID + "." + "hiyayakko",
                    TofuMain.MODID + "." + "goheimochi",
                    TofuMain.MODID + "." + "nanbantofu",
                    TofuMain.MODID + "." + "nanban"
            },
            new ItemStack[]{

                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(material, 1, 29),
                    new ItemStack(material, 1, 29),
                    new ItemStack(tsuyuBowl),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.STICK),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(material, 1, 9),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.STICK),
                    new ItemStack(Items.BOWL),
                    new ItemStack(Items.BOWL)
            }
    );
    public static ItemSwordBasic kinuTofuSword = new ItemSwordBasic(TofuToolMaterial.KINU, "swordkinu");
    public static ItemSwordBasic momenTofuSword = new ItemSwordBasic(TofuToolMaterial.MOMEN, "swordmomen");
    public static ItemSwordBasic ishiTofuSword = new ItemSwordBasic(TofuToolMaterial.SOLID, "swordsolid");
    public static ItemSwordBasic metalTofuSword = new ItemSwordBasic(TofuToolMaterial.METAL, "swordmetal");
    public static ItemSwordBasic diamondTofuSword = new ItemDiamondTofuSword();

    public static ItemPickaxeBasic kinuTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.KINU, "toolkinupickaxe");
    public static ItemPickaxeBasic momenTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.MOMEN, "toolmomenpickaxe");
    public static ItemPickaxeBasic ishiTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.SOLID, "toolsolidpickaxe");
    public static ItemPickaxeBasic metalTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.METAL, "toolmetalpickaxe");
    public static ItemPickaxeBasic diamondTofuPickaxe = new ItemDiamondTofuPickaxe();

    public static ItemShovelBasic kinuTofuShovel = new ItemShovelBasic(TofuToolMaterial.KINU, "toolkinushovel");
    public static ItemShovelBasic momenTofuShovel = new ItemShovelBasic(TofuToolMaterial.MOMEN, "toolmomenshovel");
    public static ItemShovelBasic ishiTofuShovel = new ItemShovelBasic(TofuToolMaterial.SOLID, "toolsolidshovel");
    public static ItemShovelBasic metalTofuShovel = new ItemShovelBasic(TofuToolMaterial.METAL, "toolmetalshovel");
    public static ItemShovelBasic diamondTofuShovel = new ItemDiamondTofuSpade();

    public static ItemAxeBasic kinuTofuAxe = new ItemAxeBasic(TofuToolMaterial.KINU, 0.0F, -2.6F, "toolkinuaxe");
    public static ItemAxeBasic momenTofuAxe = new ItemAxeBasic(TofuToolMaterial.MOMEN, 0.5F, -2.8F, "toolmomenaxe");
    public static ItemAxeBasic ishiTofuAxe = new ItemAxeBasic(TofuToolMaterial.SOLID, 5.0F, -2.9F, "toolsolidaxe");
    public static ItemAxeBasic metalTofuAxe = new ItemAxeBasic(TofuToolMaterial.METAL, 7.0F, -2.95F, "toolmetalaxe");
    public static ItemAxeBasic diamondTofuAxe = new ItemDiamondTofuAxe();

    public static ItemArmorBase kinuhelmet = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.HEAD, "armorkinuhelmet").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");
    public static ItemArmorBase kinuchestplate = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.CHEST, "armorkinuchestplate").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");
    public static ItemArmorBase kinuleggins = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.LEGS, "armorkinuleggins").setArmorTexture("tofucraft:textures/armor/armor_kinu_2.png");
    public static ItemArmorBase kinuboots = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.FEET, "armorkinuboots").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");

    public static ItemArmorBase momenhelmet = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.HEAD, "armormomenhelmet").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");
    public static ItemArmorBase momenchestplate = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.CHEST, "armormomenchestplate").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");
    public static ItemArmorBase momenleggins = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.LEGS, "armormomenleggins").setArmorTexture("tofucraft:textures/armor/armor_momen_2.png");
    public static ItemArmorBase momenboots = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.FEET, "armormomenboots").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");

    public static ItemArmorBase solidhelmet = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.HEAD, "armorsolidhelmet").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");
    public static ItemArmorBase solidchestplate = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.CHEST, "armorsolidchestplate").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");
    public static ItemArmorBase solidleggins = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.LEGS, "armorsolidleggins").setArmorTexture("tofucraft:textures/armor/armor_solid_2.png");
    public static ItemArmorBase solidboots = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.FEET, "armorsolidboots").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");

    public static ItemArmorBase metalhelmet = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.HEAD, "armormetalhelmet").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");
    public static ItemArmorBase metalchestplate = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.CHEST, "armormetalchestplate").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");
    public static ItemArmorBase metalleggins = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.LEGS, "armormetalleggins").setArmorTexture("tofucraft:textures/armor/armor_metal_2.png");
    public static ItemArmorBase metalboots = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.FEET, "armormetalboots").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");

    public static ItemArmorBase diamondhelmet = new ItemArmorBase(TofuArmorMaterial.DIAMOND, EntityEquipmentSlot.HEAD, "armordiamondhelmet").setArmorTexture("tofucraft:textures/armor/armor_diamond_1.png");
    public static ItemArmorBase diamondchestplate = new ItemArmorBase(TofuArmorMaterial.DIAMOND, EntityEquipmentSlot.CHEST, "armordiamondchestplate").setArmorTexture("tofucraft:textures/armor/armor_diamond_1.png");
    public static ItemArmorBase diamondleggins = new ItemArmorBase(TofuArmorMaterial.DIAMOND, EntityEquipmentSlot.LEGS, "armordiamondleggins").setArmorTexture("tofucraft:textures/armor/armor_diamond_2.png");
    public static ItemArmorBase diamondboots = new ItemArmorBase(TofuArmorMaterial.DIAMOND, EntityEquipmentSlot.FEET, "armordiamondboots").setArmorTexture("tofucraft:textures/armor/armor_diamond_1.png");

    public static ItemSoybeans soybeans = new ItemSoybeans();
    public static ItemSoybeansNether soybeansHell = new ItemSoybeansNether();

    public static Item fukumame = new ItemFukumame();

    public static Item zundaBow = new ItemZundaBow();
    public static Item zundaArrow = new ItemZundaArrow();

    public static Item nigari = new ItemNigari();
    public static Item tofustick = new ItemTofuStick();
    public static Item bugle = new ItemBugle();
    public static Item tofuhoe = new ItemTofuHoe();

    public static Item zundaruby = new Item();
    public static Item rice = new Item();
    public static Item riceseed = new ItemRiceSeed();

    public static ItemDoor TOFUKINU_DOOR = new ItemDoor(BlockLoader.TOFUKINU_DOOR);
    public static ItemDoor TOFUMOMEN_DOOR = new ItemDoor(BlockLoader.TOFUMOMEN_DOOR);
    public static ItemDoor TOFUISHI_DOOR = new ItemDoor(BlockLoader.TOFUISHI_DOOR);
    public static ItemDoor TOFUMETAL_DOOR = new ItemDoor(BlockLoader.TOFUMETAL_DOOR);

    public static Item soysauce_bottle = new ItemSeasoning(TofuMain.MODID + "." + "bottlesoysause", 20);
    public static Item dashi_bottle = new ItemSeasoning(TofuMain.MODID + "." + "dashi", 10);
    public static Item soyoil_bottle = new ItemSeasoning(TofuMain.MODID + "." + "soyoil", 20);
    public static Item doubanjiang_bottle = new ItemSeasoning(TofuMain.MODID + "." + "doubanjiang", 58);
    public static Item mayonnaise_bottle = new ItemSeasoning(TofuMain.MODID + "." + "mayonnaise", 20);
    public static Item apricotjerry_bottle = new ItemSeasoning(TofuMain.MODID + "." + "apricotjerry", 20);

    public static Item defatting_potion = new ItemDefattingPotion();

    public static Item apricotjerry_bread = new ItemFood(5, 0.7f, false);

    public static Item wrench = new ItemWrench();

    public static DrinkSoymilk soymilk_drink = new DrinkSoymilk("soymilk_drink",
            new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f},
            new String[]{
                    TofuMain.MODID + "." + "soymilk",
                    TofuMain.MODID + "." + "soymilk_annin",
                    TofuMain.MODID + "." + "soymilk_apple",
                    TofuMain.MODID + "." + "soymilk_cocoa",
                    TofuMain.MODID + "." + "soymilk_kinako",
                    TofuMain.MODID + "." + "soymilk_pudding",
                    TofuMain.MODID + "." + "soymilk_pumpkin",
                    TofuMain.MODID + "." + "soymilk_sakura",
                    TofuMain.MODID + "." + "soymilk_strawberry",
                    TofuMain.MODID + "." + "soymilk_tea",
                    TofuMain.MODID + "." + "soymilk_zunda",
            },
            new PotionEffect[]{
                    null,
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "absorption")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "fire_resistance")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "jump_boost")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "haste")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "regeneration")), 1200, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "resistance")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "speed")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "water_breathing")), 600, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "night_vision")), 600, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "regeneration")), 1200, 1),
            });
    public static Item soymilk_ramune = new DrinkSoymilkRamune("soymilk_ramune", 2, 0.2f,
            new PotionEffect[]{
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "absorption")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "fire_resistance")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "jump_boost")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "haste")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "regeneration")), 1200, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "resistance")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "speed")), 900, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "water_breathing")), 600, 0),
                    new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "night_vision")), 600, 0),
            }
    );
    public static Item tofu_slime_radar = new ItemTofuSlimeRadar().setUnlocalizedName(TofuMain.MODID + "." + "tofuradar");
    public static Item anninApple = new ItemAnninApple();

    public static Item tofuforce_core = new ItemTofuForceCore();
    public static Item tofuishi_shield = new ItemTofuShield(360);
    public static Item tofumetal_shield = new ItemTofuShield(560);
//    WIP Remaking.
//    public static Item fulintlock = new ItemFlintlock();

    public static Item tofuchinger_tooth = new Item();
    public static Item tofuchinger_tootharrow = new ItemChingerToothArrow();

    public ItemLoader(FMLPreInitializationEvent event) {
        register(material);
        register(tofu_slime_radar);
        register(RollingPin);
        register(koujiBase);
        register(tofu_food);
        register(tofu_hell);
        register(tofu_material);
        register(foodset);
        register(zundaMochi);
        register(tsuyuBowl);
        register(foodsetContain);
        register(nigari);
        register(soybeans);
        register(soybeansHell);
        register(fukumame);
        register(rice.setUnlocalizedName(TofuMain.MODID + "." + "rice"));
        register(riceseed);
        register(defatting_potion);
        register(soysauce_bottle);
        register(dashi_bottle);
        register(soyoil_bottle);
        register(doubanjiang_bottle);
        register(mayonnaise_bottle);
        register(apricotjerry_bottle);
        register(apricotjerry_bread.setUnlocalizedName(TofuMain.MODID + "." + "apricotjerry_bread"));
        register(anninApple);
        register(soymilk_drink);
        register(soymilk_ramune);

        register(tofustick);
        register(bugle);
        register(tofuhoe);

        MinecraftForge.addGrassSeed(new ItemStack(soybeans), 2);
        MinecraftForge.addGrassSeed(new ItemStack(riceseed), 3);

        register(kinuTofuSword);
        register(momenTofuSword);
        register(ishiTofuSword);
        register(metalTofuSword);
        register(diamondTofuSword);

        register(zundaruby.setUnlocalizedName(TofuMain.MODID + "." + "zundaruby"));

        register(kinuTofuPickaxe);
        register(momenTofuPickaxe);
        register(ishiTofuPickaxe);
        register(metalTofuPickaxe);
        register(diamondTofuPickaxe);

        register(kinuTofuShovel);
        register(momenTofuShovel);
        register(ishiTofuShovel);
        register(metalTofuShovel);
        register(diamondTofuShovel);

        register(kinuTofuAxe);
        register(momenTofuAxe);
        register(ishiTofuAxe);
        register(metalTofuAxe);
        register(diamondTofuAxe);

        register(kinuhelmet);
        register(kinuchestplate);
        register(kinuleggins);
        register(kinuboots);

        register(momenhelmet);
        register(momenchestplate);
        register(momenleggins);
        register(momenboots);

        register(solidhelmet);
        register(solidchestplate);
        register(solidleggins);
        register(solidboots);

        register(metalhelmet);
        register(metalchestplate);
        register(metalleggins);
        register(metalboots);

        register(diamondhelmet);
        register(diamondchestplate);
        register(diamondleggins);
        register(diamondboots);

        register(tofuforce_core);

        register(tofuishi_shield.setUnlocalizedName(TofuMain.MODID + "." + "tofuishi_shield"));
        register(tofumetal_shield.setUnlocalizedName(TofuMain.MODID + "." + "tofumetal_shield"));
//        register(fulintlock);

        register(zundaBow);
        register(zundaArrow.setUnlocalizedName(TofuMain.MODID + "." + "zundaarrow"));

        register(TOFUKINU_DOOR.setUnlocalizedName(TofuMain.MODID + "." + "tofudoor_kinu"));
        register(TOFUMOMEN_DOOR.setUnlocalizedName(TofuMain.MODID + "." + "tofudoor_momen"));
        register(TOFUISHI_DOOR.setUnlocalizedName(TofuMain.MODID + "." + "tofudoor_ishi"));
        register(TOFUMETAL_DOOR.setUnlocalizedName(TofuMain.MODID + "." + "tofudoor_metal"));

        register(tofuchinger_tooth.setUnlocalizedName(TofuMain.MODID + "." + "tofuchinger_tooth"));
        register(tofuchinger_tootharrow.setUnlocalizedName(TofuMain.MODID + "." + "tofuchinger_tootharrow"));

        tofuItemRegister(TofuType.kinu, new ItemStack(tofu_food));
        tofuItemRegister(TofuType.momen, new ItemStack(tofu_food, 1, 1));
        tofuItemRegister(TofuType.ishi, new ItemStack(tofu_food, 1, 2));
        tofuItemRegister(TofuType.grilled, new ItemStack(tofu_food, 1, 3));
        tofuItemRegister(TofuType.friedPouch, new ItemStack(tofu_food, 1, 4));
        tofuItemRegister(TofuType.fried, new ItemStack(tofu_food, 1, 5));
        tofuItemRegister(TofuType.egg, new ItemStack(tofu_food, 1, 6));
        tofuItemRegister(TofuType.annin, new ItemStack(tofu_food, 1, 7));
        tofuItemRegister(TofuType.metal, new ItemStack(tofu_material));
        tofuItemRegister(TofuType.diamond, new ItemStack(tofu_material, 1, 1));
        tofuItemRegister(TofuType.zunda, new ItemStack(tofu_food, 1, 9));
        tofuItemRegister(TofuType.dried, new ItemStack(tofu_material, 1, 2));
        tofuItemRegister(TofuType.sesame, new ItemStack(tofu_food, 1, 8));
        tofuItemRegister(TofuType.strawberry, new ItemStack(tofu_food, 1, 10));
        tofuItemRegister(TofuType.miso, new ItemStack(tofu_food, 1, 11));
        tofuItemRegister(TofuType.hell, new ItemStack(tofu_hell, 1));
// STOP USE CLIENT VOID IN COMMON VOID PLEASE!!!
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        TileEntityRenderHelper TESR = new TileEntityRenderHelper();
        tofuishi_shield.setTileEntityItemStackRenderer(TESR);
        tofumetal_shield.setTileEntityItemStackRenderer(TESR);
        registerRender(soybeansHell);
        registerRender(tofu_slime_radar);
        registerRender(soymilk_ramune);
        registerRender(soymilk_drink);
        registerRender(mayonnaise_bottle);
        registerRender(apricotjerry_bottle);
        registerRender(apricotjerry_bread);
        registerRender(RollingPin);
        registerRender(koujiBase);
        registerRender(doubanjiang_bottle);
        registerRender(defatting_potion);
        registerRender(soyoil_bottle);
        registerRender(dashi_bottle);
        registerRender(soysauce_bottle);
        registerRender(material);
        registerRender(tofu_food);
        registerRender(tofu_hell);
        registerRender(zundaMochi);
        registerRender(tofu_material);
        registerRender(foodset);
        registerRender(tsuyuBowl);
        registerRender(foodsetContain);
        registerRender(nigari);
        registerRender(soybeans);
        registerRender(fukumame);
        registerRender(rice);
        registerRender(riceseed);
        registerRender(tofustick);
        registerRender(bugle);
        registerRender(tofuhoe);

        registerRender(zundaruby);

        registerRender(kinuTofuSword);
        registerRender(momenTofuSword);
        registerRender(ishiTofuSword);
        registerRender(metalTofuSword);
        registerRender(diamondTofuSword);

        registerRender(kinuTofuPickaxe);
        registerRender(momenTofuPickaxe);
        registerRender(ishiTofuPickaxe);
        registerRender(metalTofuPickaxe);
        registerRender(diamondTofuPickaxe);

        registerRender(kinuTofuShovel);
        registerRender(momenTofuShovel);
        registerRender(ishiTofuShovel);
        registerRender(metalTofuShovel);
        registerRender(diamondTofuShovel);

        registerRender(kinuTofuAxe);
        registerRender(momenTofuAxe);
        registerRender(ishiTofuAxe);
        registerRender(metalTofuAxe);
        registerRender(diamondTofuAxe);

        registerRender(TOFUKINU_DOOR);
        registerRender(TOFUMOMEN_DOOR);
        registerRender(TOFUISHI_DOOR);
        registerRender(TOFUMETAL_DOOR);

        registerRender(kinuhelmet);
        registerRender(kinuchestplate);
        registerRender(kinuleggins);
        registerRender(kinuboots);

        registerRender(momenhelmet);
        registerRender(momenchestplate);
        registerRender(momenleggins);
        registerRender(momenboots);

        registerRender(solidhelmet);
        registerRender(solidchestplate);
        registerRender(solidleggins);
        registerRender(solidboots);

        registerRender(metalhelmet);
        registerRender(metalchestplate);
        registerRender(metalleggins);
        registerRender(metalboots);

        registerRender(diamondhelmet);
        registerRender(diamondchestplate);
        registerRender(diamondleggins);
        registerRender(diamondboots);

        registerRender(zundaBow);
        registerRender(zundaArrow);
        registerRender(anninApple);
        registerRender(tofuforce_core);
        registerRender(tofuishi_shield);
        registerRender(tofumetal_shield);
//        registerRender(fulintlock);
        registerRender(tofuchinger_tooth);
        registerRender(tofuchinger_tootharrow);
    }

    private static void register(Item item) {
        item.setCreativeTab(CommonProxy.tab);

        ForgeRegistries.ITEMS.register(item.setRegistryName(item.getUnlocalizedName().substring(5 + TofuMain.MODID.length() + 1)));
    }

    private static void tofuItemRegister(TofuType type, ItemStack item) {
        ItemLoader.tofuItems.put(type, item);

    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemBase item) {
        registerRender(item, false);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic item) {
        registerRender(item, false);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemBase item, boolean json_create) {
        for (int i = 0; i < item.getSubNames().length; i++) {
            String name = item.getSubNames()[i].substring(TofuMain.MODID.length() + 1);
            if (json_create) JSON_Creator.genItem(name, name, "json_create");
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(TofuMain.MODID, name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(DrinkSoymilk item) {
        registerRender(item, false);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(DrinkSoymilk item, boolean json_create) {
        for (int i = 0; i < item.getSubNames().length; i++) {
            String name = item.getSubNames()[i].substring(TofuMain.MODID.length() + 1);
            if (json_create) JSON_Creator.genItem(name, name, "json_create");
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(TofuMain.MODID, name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic item, boolean json_create) {

        for (int i = 0; i < item.getSubNames().length; i++) {
            String name = item.getSubNames()[i].substring(TofuMain.MODID.length() + 1);
            if (json_create) JSON_Creator.genItem(name, name, "json_create");
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(TofuMain.MODID, name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, int meta, String name) {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, int meta, String name, String textureName) {
        JSON_Creator.genItem(name, textureName, "json_create");
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, String textureName) {
        JSON_Creator.genItem(item.getRegistryName().toString().substring(TofuMain.MODID.length() + 1), textureName, "json_create");
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item) {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }

}
