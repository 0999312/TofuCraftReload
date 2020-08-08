package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.client.TileEntityRenderHelper;
import cn.mcmod.tofucraft.item.tfitem.ItemTofuCore;
import cn.mcmod.tofucraft.item.tfitem.ItemTofuForceCore;
import cn.mcmod.tofucraft.item.tfitem.ItemTofuForceSword;
import cn.mcmod.tofucraft.material.TofuArmorMaterial;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod_mmf.mmlib.item.ItemBase;
import cn.mcmod_mmf.mmlib.item.ItemMetaDurability;
import cn.mcmod_mmf.mmlib.item.food.FoodInfo;
import cn.mcmod_mmf.mmlib.item.food.ItemDrinkBase;
import cn.mcmod_mmf.mmlib.item.food.ItemFoodBase;
import cn.mcmod_mmf.mmlib.item.food.ItemFoodContain;
import cn.mcmod_mmf.mmlib.register.ItemRegister;

import com.google.common.collect.Maps;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class ItemLoader {
    public static EnumMap<TofuType, ItemStack> tofuItems = Maps.newEnumMap(TofuType.class);

    public static ItemFoodBase tofu_food = new ItemFoodBase(TofuMain.MODID,"tofu_food", 64,
    		new FoodInfo[]{
    			new FoodInfo("tofukinu", 2, 0.1f, false, 2F, 0F, 0F, 4F, 0F, 1F, 3F, 1F, 200F),
    			new FoodInfo("tofumomen", 2, 0.1f, false, 1F, 0F, 0F, 4F, 0F, 1F, 2.5F, 1F, 200F),
    			new FoodInfo("tofuishi", 3, 0.4f, false, 1F, 0F, 0F, 4F, 0F, 1F, 1F, 1F, 200F),
    			new FoodInfo("tofugrilled", 3, 0.2f, false, 2F, 0F, 0F, 4F, 0F, 1F, 2F, 1F, 480F),
    			new FoodInfo("tofufriedpouch", 4, 0.2f, false, 1F, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo("tofufried", 4, 0.2f, false, 1F, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo("tofuegg", 4, 0.2f, false, 2F, 0F, 0F, 5F, 0F, 1F, 1F, 2F, 200F),
    			new FoodInfo("tofuannin", 4, 0.2f, false, 2F, 1F, 0F, 4F, 0F, 1F, 2F, 2F, 480F),
    			new FoodInfo("tofusesame", 4, 0.2f, false, 2F, 1F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo("tofuzunda", 4, 0.2f, false, 2F, 1F, 1F, 4F, 1F, 1F, 2F, 1F, 200F),
    			new FoodInfo("tofustrawberry", 4, 0.2f, false, 2F, 2F, 0F, 5F, 2F, 1F, 1F, 2F, 200F),
    			new FoodInfo("tofumiso", 5, 0.8f, false, 2F, 1F, 1F, 4F, 0F, 2F, 2F, 2F, 480F),
    			
    		}
    ).setFastEat();
    public static ItemFood tofu_hell = (ItemFood) new ItemFood(2, 0.2f, false)
            .setPotionEffect(new PotionEffect(Potion.getPotionById(12), 30, 0), 1.0F)
            .setUnlocalizedName(TofuMain.MODID + "." + "tofuhell");
    public static ItemBase tofu_material = new ItemBase(TofuMain.MODID,"tofu_material", 64,
    		"tofumetal",
            "tofudiamond",
            "tofudried");
    public static ItemFoodBase foodset = new ItemFoodBase(TofuMain.MODID,"foodset", 64,
    		new FoodInfo[]{
    				new FoodInfo("tofuchikuwa", 6, 0.4f, false, 1f, 2F, 0F, 4F, 0F, 2F, 2F, 1F, 480F),
    				new FoodInfo("oage", 5, 0.3f, false, 1f, 0F, 1F, 4F, 0F, 2F, 1F, 1F, 480F),
    				new FoodInfo("onigiri", 4, 0.4f, false, 1f, 4F, 0F, 0F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo("onigirisalt", 5, 0.6f, false, 1f, 4F, 0F, 0F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo("yakionigirimiso", 6, 0.8f, false, 1f, 4F, 0F, 1F, 0F, 0F, 1F, 1F, 480F),
    				new FoodInfo("yakionigirishoyu", 6, 0.8f, false, 1f, 4F, 0F, 1F, 0F, 0F, 1F, 1F, 480F),
    				new FoodInfo("sprouts", 2, 0.2f, false, 2f, 0F, 0F, 2F, 2F, 0F, 3F, 1F, 480F),
    				new FoodInfo("ricetofu", 8, 0.8f, false, 1F, 4F, 2F, 2.5F, 0F, 1.5F, 2.25f, 1f, 480f),	
    				new FoodInfo("tofuhamburg", 10, 0.8f, false, 1f, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo("tofucookie", 2, 0.2f, false, 1f, 2F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo("inari", 5, 0.4f, false, 1f, 2F, 2F, 3F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo("tofufishraw", 3, 0.25f, false, 1f, 0F, 0F, 2F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo("tofufishcooked", 6, 0.4f, false, 1f, 0F, 1F, 6F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo("kinakomochi", 5, 0.4f, false, 1f, 4F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo("kinakomanju", 4, 0.4f, false, 1f, 4F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo("chikuwa", 4, 0.5f, false, 1f, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo("tofusteak", 6, 0.6f, false, 1f, 0F, 1F, 4F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo("tofuhamburgt", 16, 0.5f, false, 1f, 2F, 2F, 5F, 0F, 1F, 2.5F, 1F, 480F),
    				new FoodInfo("tofuhamburgta", 20, 0.5f, false, 1f, 2F, 2F, 5F, 0F, 2F, 2.5F, 1F, 480F),
    				new FoodInfo("tofuminced", 2, 0.2f, false, 2f, 0F, 0F, 2F, 2F, 0F, 3F, 1F, 480F),
    				new FoodInfo("ricesoborotofu", 8, 0.8f, false, 1F, 4F, 2F, 2.5F, 0F, 1.5F, 2.25f, 1f, 480f),	
    				new FoodInfo("okarastick", 4, 0.5f, false, 1f, 0F, 1F, 4F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo("edamameboiled", 2, 0.2f, false, 2f, 2F, 0F, 2F, 0F, 1F, 4F, 1F, 480F),
    				new FoodInfo("saltymelon", 3, 0.2f, false, 4f, 5F, 0F, 0F, 2F, 1F, 3F, 1F, 480F),
    				new FoodInfo("chillstick", 6, 0.5f,false, 1f, 0F, 1F, 4F, 1F, 1F, 1F, 1F, 480F),
    				new FoodInfo("tttburger", 8, 0.4f, false, 1f, 3F, 1F, 6F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo("fukumeni", 3, 0.4f, false, 3f, 0F, 0F, 4F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo("meatwrapped_yuba", 4, 0.4f, false, 2f, 0F, 1F, 6F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo("apricotjerry_bread", 6, 0.5f, false, 1f, 3F, 0F, 2F, 1F, 1F, 2F, 1F, 480F),
    				new FoodInfo("kinako_bread", 6, 0.5f, false, 1f, 3F, 1F, 2F, 0F, 1F, 2F, 1F, 480F),
    		}
    );
    public static ItemBase material = new ItemBase(TofuMain.MODID,"material", 64,
            "salt",
            "kouji",//1
            "miso",//2
            "edamame",//3
            "zunda",//4
            "barrelempty",//5
            "soybeansparched",//6
            "kinako",//7
            "natto",//8
            "apricotseed",//9
            "filtercloth",//10
            "okara",//11
            "mincedpotato",//12
            "starchraw",//13
            "starch",//14
            "kyoninso",//15
            "leek",//16
            "zundama",//17
            "tofugem",//18
            "tofudiamondnugget",//19
            "tofuhamburgraw",//20
            "tfcapacitor",//21
            "tfcircuit",//22
            "tfcoil",//23
            "tfoscillator",//24
            "advtofugem",//25
            "activatedtofugem",//26
            "activatedhelltofu",//27
            "tofusomen",//28
            "glassbowl",//29
            "lemon",//30
            "chill",//31
            "yuba"//32
    );
    public static Item koujiBase = new ItemKoujiBase();
    public static Item RollingPin = new ItemRolling();
    public static ItemFood zundaMochi = (ItemFood) new ItemFood(3, 0.8f, false)
            .setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 2), 1.0F)
            .setUnlocalizedName(TofuMain.MODID + "." + "zundamochi");
    public static ItemFoodBase tsuyuBowl = new ItemFoodContain(TofuMain.MODID,"tsuyuBowl_glass", 64,
    		new FoodInfo[]{
    				new FoodInfo("tsuyubowl_glass", 2, 0.1F, false, 4F, 0, 1f, 2f, 0, 1f, 0, 0, -1)
    		},
            new ItemStack[]{
                    new ItemStack(material, 1, 29),
            }
    );
    public static ItemFoodBase foodsetContain = new ItemFoodContain(TofuMain.MODID,"foodsetcontain", 64,
    		new FoodInfo[]{
    				new FoodInfo("mabodofu", 16, 0.6f, false, 2F, 1F, 2f, 3f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("moyashiitame", 8, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("moyashiohitashi", 5, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("hiyayakko_glass", 6, 0.5f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("nattohiyayakko_glass", 8, 0.8f, false, 2F, 1F, 1f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("tofusomenbowl_glass", 3, 0.4f, false, 2F, 3F, 0f, 1f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo("tastystew", 20, 1f, false, 3F, 4F, 4f, 4f, 4f, 4f, 4f, 1f, 480f),
    				new FoodInfo("tastybeefstew", 20, 1f, false, 3F, 4F, 4f, 4f, 4f, 4f, 4f, 1f, 480f),
    				new FoodInfo("yudofu", 5, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("misosoup", 3, 0.2f, false, 5F, 1F, 0f, 2f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo("misodengaku", 5, 0.4f, false, 2F, 3F, 1f, 4f, 0f, 1f, 2f, 1f, 480f),
    				new FoodInfo("nikujaga", 10, 0.8f, false, 2F, 3F, 2f, 4f, 3f, 2f, 4f, 1f, 480f),
    				new FoodInfo("agedashitofu", 6, 0.6f, false, 5F, 0F, 1f, 4f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo("koyadofustew", 8, 0.8f, false, 3F, 2F, 1f, 3f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("apricot", 2, 0.2f, false, 4F, 1F, 0f, 0f, 1f, 0f, 3f, 1f, 480f),
    				new FoodInfo("soborotofusaute", 7, 0.6f, false, 2F, 1F, 1f, 4f, 0f, 1f, 3f, 1f, 480f),
    				new FoodInfo("hiyayakko", 6, 0.5f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("goheimochi", 5, 0.5f, false, 1F, 4F, 0f, 2f, 0f, 1f, 3f, 1f, 480f),
    				new FoodInfo("nanbantofu", 8, 0.5f, false, 2F, 2F, 2f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo("nanban", 10, 0.5f, false, 2F, 2F, 2f, 4f, 1f, 1f, 4f, 1f, 480f),
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
    public static Item mineral_soymilk = new Item().setContainerItem(Items.GLASS_BOTTLE);
    public static Item riceseed = new ItemRiceSeed();

    public static ItemDoor TOFUKINU_DOOR = new ItemDoor(BlockLoader.TOFUKINU_DOOR);
    public static ItemDoor TOFUMOMEN_DOOR = new ItemDoor(BlockLoader.TOFUMOMEN_DOOR);
    public static ItemDoor TOFUISHI_DOOR = new ItemDoor(BlockLoader.TOFUISHI_DOOR);
    public static ItemDoor TOFUMETAL_DOOR = new ItemDoor(BlockLoader.TOFUMETAL_DOOR);

//    public static Item soysauce_bottle = new ItemSeasoning(TofuMain.MODID + "." + "bottlesoysause", 20);
//    public static Item dashi_bottle = new ItemSeasoning(TofuMain.MODID + "." + "dashi", 10);
//    public static Item soyoil_bottle = new ItemSeasoning(TofuMain.MODID + "." + "soyoil", 20);
//    public static Item doubanjiang_bottle = new ItemSeasoning(TofuMain.MODID + "." + "doubanjiang", 58);
//    public static Item mayonnaise_bottle = new ItemSeasoning(TofuMain.MODID + "." + "mayonnaise", 20);
//    public static Item apricotjerry_bottle = new ItemSeasoning(TofuMain.MODID + "." + "apricotjerry", 20);
//    public static Item strawberryjam_bottle = new ItemSeasoning(TofuMain.MODID + "." + "strawberryjam", 20);
    public static ItemBase sauce_bottle = new ItemMetaDurability(TofuMain.MODID,"sauce_bottle", 25, new ItemStack(Items.GLASS_BOTTLE),
    		new String[]{
    				"bottlesoysause",
    				"dashi",
    				"soyoil",
    				"doubanjiang",
    				"mayonnaise",
    				"apricotjerry",
    				"strawberryjam",
    		});

    public static Item defatting_potion = new ItemDefattingPotion();

    public static Item wrench = new ItemWrench();

    public static ItemDrinkBase soymilk_drink = new ItemDrinkBase(TofuMain.MODID,"soymilk_drink",
    		new FoodInfo[]{
    			new FoodInfo("soymilk", 2, 0.2F, false, 50F, 0F, 0F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_annin", 2, 0.2F, false, 50F, 1F, 0F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_apple", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_cocoa", 2, 0.2F, false, 50F, 1F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_kinako", 2, 0.2F, false, 50F, 1F, 0F, 5F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_pudding", 2, 0.2F, false, 50F, 1F, 1F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_pumpkin", 2, 0.2F, false, 50F, 2F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_sakura", 2, 0.2F, false, 50F, 0F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_strawberry", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_tea", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo("soymilk_zunda", 2, 0.2F, false, 50F, 1F, 1F, 4F, 2F, 1F, 5F, 0F, -1F),
    		},
            new PotionEffect[][]{
                    null,
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "absorption")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "fire_resistance")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "jump_boost")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "haste")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "regeneration")), 1200, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "resistance")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "speed")), 900, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "water_breathing")), 600, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "night_vision")), 600, 0)},
                    new PotionEffect[]{new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("minecraft", "regeneration")), 1200, 1)},
            },new ItemStack(Items.GLASS_BOTTLE));
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

    public static ItemTofuForceCore tofuforce_core = new ItemTofuForceCore();
    public static ItemTofuCore tofucore = new ItemTofuCore();
    public static ItemTofuForceSword tofuforce_sword = new ItemTofuForceSword();
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
        register(sauce_bottle);
        register(anninApple);

        register(soymilk_drink);
        register(soymilk_ramune);
        register(mineral_soymilk.setUnlocalizedName(TofuMain.MODID + "." + "mineral_soymilk"));
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
        register(tofucore);
        register(tofuforce_sword);

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
        ItemRegister.getInstance().registerRender(mineral_soymilk);
        ItemRegister.getInstance().registerRender(soybeansHell);
        ItemRegister.getInstance().registerRender(tofu_slime_radar);
        ItemRegister.getInstance().registerRender(soymilk_ramune);
        ItemRegister.getInstance().registerRender(soymilk_drink);
        ItemRegister.getInstance().registerRender(RollingPin);
        ItemRegister.getInstance().registerRender(koujiBase);
        ItemRegister.getInstance().registerRender(defatting_potion);
        ItemRegister.getInstance().registerRender(sauce_bottle);
        ItemRegister.getInstance().registerRender(material);
        ItemRegister.getInstance().registerRender(tofu_food);
        ItemRegister.getInstance().registerRender(tofu_hell);
        ItemRegister.getInstance().registerRender(zundaMochi);
        ItemRegister.getInstance().registerRender(tofu_material);
        ItemRegister.getInstance().registerRender(foodset);
        ItemRegister.getInstance().registerRender(tsuyuBowl);
        ItemRegister.getInstance().registerRender(foodsetContain);
        ItemRegister.getInstance().registerRender(nigari);
        ItemRegister.getInstance().registerRender(soybeans);
        ItemRegister.getInstance().registerRender(fukumame);
        ItemRegister.getInstance().registerRender(rice);
        ItemRegister.getInstance().registerRender(riceseed);
        ItemRegister.getInstance().registerRender(tofustick);
        ItemRegister.getInstance().registerRender(bugle);
        ItemRegister.getInstance().registerRender(tofuhoe);

        ItemRegister.getInstance().registerRender(zundaruby);

        ItemRegister.getInstance().registerRender(kinuTofuSword);
        ItemRegister.getInstance().registerRender(momenTofuSword);
        ItemRegister.getInstance().registerRender(ishiTofuSword);
        ItemRegister.getInstance().registerRender(metalTofuSword);
        ItemRegister.getInstance().registerRender(diamondTofuSword);

        ItemRegister.getInstance().registerRender(kinuTofuPickaxe);
        ItemRegister.getInstance().registerRender(momenTofuPickaxe);
        ItemRegister.getInstance().registerRender(ishiTofuPickaxe);
        ItemRegister.getInstance().registerRender(metalTofuPickaxe);
        ItemRegister.getInstance().registerRender(diamondTofuPickaxe);

        ItemRegister.getInstance().registerRender(kinuTofuShovel);
        ItemRegister.getInstance().registerRender(momenTofuShovel);
        ItemRegister.getInstance().registerRender(ishiTofuShovel);
        ItemRegister.getInstance().registerRender(metalTofuShovel);
        ItemRegister.getInstance().registerRender(diamondTofuShovel);

        ItemRegister.getInstance().registerRender(kinuTofuAxe);
        ItemRegister.getInstance().registerRender(momenTofuAxe);
        ItemRegister.getInstance().registerRender(ishiTofuAxe);
        ItemRegister.getInstance().registerRender(metalTofuAxe);
        ItemRegister.getInstance().registerRender(diamondTofuAxe);

        ItemRegister.getInstance().registerRender(TOFUKINU_DOOR);
        ItemRegister.getInstance().registerRender(TOFUMOMEN_DOOR);
        ItemRegister.getInstance().registerRender(TOFUISHI_DOOR);
        ItemRegister.getInstance().registerRender(TOFUMETAL_DOOR);

        ItemRegister.getInstance().registerRender(kinuhelmet);
        ItemRegister.getInstance().registerRender(kinuchestplate);
        ItemRegister.getInstance().registerRender(kinuleggins);
        ItemRegister.getInstance().registerRender(kinuboots);

        ItemRegister.getInstance().registerRender(momenhelmet);
        ItemRegister.getInstance().registerRender(momenchestplate);
        ItemRegister.getInstance().registerRender(momenleggins);
        ItemRegister.getInstance().registerRender(momenboots);

        ItemRegister.getInstance().registerRender(solidhelmet);
        ItemRegister.getInstance().registerRender(solidchestplate);
        ItemRegister.getInstance().registerRender(solidleggins);
        ItemRegister.getInstance().registerRender(solidboots);

        ItemRegister.getInstance().registerRender(metalhelmet);
        ItemRegister.getInstance().registerRender(metalchestplate);
        ItemRegister.getInstance().registerRender(metalleggins);
        ItemRegister.getInstance().registerRender(metalboots);

        ItemRegister.getInstance().registerRender(diamondhelmet);
        ItemRegister.getInstance().registerRender(diamondchestplate);
        ItemRegister.getInstance().registerRender(diamondleggins);
        ItemRegister.getInstance().registerRender(diamondboots);

        ItemRegister.getInstance().registerRender(zundaBow);
        ItemRegister.getInstance().registerRender(zundaArrow);
        ItemRegister.getInstance().registerRender(anninApple);
        ItemRegister.getInstance().registerRender(tofuforce_core);
        ItemRegister.getInstance().registerRender(tofucore);
        ItemRegister.getInstance().registerRender(tofuforce_sword);
        ItemRegister.getInstance().registerRender(tofuishi_shield);
        ItemRegister.getInstance().registerRender(tofumetal_shield);
//        registerRender(fulintlock);
        ItemRegister.getInstance().registerRender(tofuchinger_tooth);
        ItemRegister.getInstance().registerRender(tofuchinger_tootharrow);
    }

    private static void register(Item item) {
        item.setCreativeTab(CommonProxy.tab);
       	ItemRegister.getInstance().register(TofuMain.MODID, item);
    }

    private static void tofuItemRegister(TofuType type, ItemStack item) {
        ItemLoader.tofuItems.put(type, item);
    }
}
