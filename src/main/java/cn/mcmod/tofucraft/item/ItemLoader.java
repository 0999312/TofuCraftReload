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
    			new FoodInfo(TofuMain.MODID + "." + "tofukinu", 2, 0.1f, false, 2F, 0F, 0F, 4F, 0F, 1F, 3F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofumomen", 2, 0.1f, false, 1F, 0F, 0F, 4F, 0F, 1F, 2.5F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofuishi", 3, 0.4f, false, 1F, 0F, 0F, 4F, 0F, 1F, 1F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofugrilled", 3, 0.2f, false, 2F, 0F, 0F, 4F, 0F, 1F, 2F, 1F, 480F),
    			new FoodInfo(TofuMain.MODID + "." + "tofufriedpouch", 4, 0.2f, false, 1F, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofufried", 4, 0.2f, false, 1F, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofuegg", 4, 0.2f, false, 2F, 0F, 0F, 5F, 0F, 1F, 1F, 2F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofuannin", 4, 0.2f, false, 2F, 1F, 0F, 4F, 0F, 1F, 2F, 2F, 480F),
    			new FoodInfo(TofuMain.MODID + "." + "tofusesame", 4, 0.2f, false, 2F, 1F, 1F, 4F, 0F, 1F, 2F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofuzunda", 4, 0.2f, false, 2F, 1F, 1F, 4F, 1F, 1F, 2F, 1F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofustrawberry", 4, 0.2f, false, 2F, 2F, 0F, 5F, 2F, 1F, 1F, 2F, 200F),
    			new FoodInfo(TofuMain.MODID + "." + "tofumiso", 5, 0.8f, false, 2F, 1F, 1F, 4F, 0F, 2F, 2F, 2F, 480F),
    			
    		}
    ).setFastEat();
    public static ItemFood tofu_hell = (ItemFood) new ItemFood(2, 0.2f, false)
            .setPotionEffect(new PotionEffect(Potion.getPotionById(12), 30, 0), 1.0F)
            .setUnlocalizedName(TofuMain.MODID + "." + "tofuhell");
    public static ItemBase tofu_material = new ItemBase(TofuMain.MODID,"tofu_material", 64,
    		TofuMain.MODID + "." + "tofumetal",
            TofuMain.MODID + "." + "tofudiamond",
            TofuMain.MODID + "." + "tofudried");
    public static ItemFoodBase foodset = new ItemFoodBase(TofuMain.MODID,"foodset", 64,
    		new FoodInfo[]{
    				new FoodInfo(TofuMain.MODID + "." + "tofuchikuwa", 6, 0.4f, false, 1f, 2F, 0F, 4F, 0F, 2F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "oage", 5, 0.3f, false, 1f, 0F, 1F, 4F, 0F, 2F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "onigiri", 4, 0.4f, false, 1f, 4F, 0F, 0F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "onigirisalt", 5, 0.6f, false, 1f, 4F, 0F, 0F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "yakionigirimiso", 6, 0.8f, false, 1f, 4F, 0F, 1F, 0F, 0F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "yakionigirishoyu", 6, 0.8f, false, 1f, 4F, 0F, 1F, 0F, 0F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "sprouts", 2, 0.2f, false, 2f, 0F, 0F, 2F, 2F, 0F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "ricetofu", 8, 0.8f, false, 1F, 4F, 2F, 2.5F, 0F, 1.5F, 2.25f, 1f, 480f),	
    				new FoodInfo(TofuMain.MODID + "." + "tofuhamburg", 10, 0.8f, false, 1f, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofucookie", 2, 0.2f, false, 1f, 2F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "inari", 5, 0.4f, false, 1f, 2F, 2F, 3F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofufishraw", 3, 0.25f, false, 1f, 0F, 0F, 2F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofufishcooked", 6, 0.4f, false, 1f, 0F, 1F, 6F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "kinakomochi", 5, 0.4f, false, 1f, 4F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "kinakomanju", 4, 0.4f, false, 1f, 4F, 0F, 2F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "chikuwa", 4, 0.5f, false, 1f, 0F, 1F, 4F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofusteak", 6, 0.6f, false, 1f, 0F, 1F, 4F, 0F, 0F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofuhamburgt", 16, 0.5f, false, 1f, 2F, 2F, 5F, 0F, 1F, 2.5F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofuhamburgta", 20, 0.5f, false, 1f, 2F, 2F, 5F, 0F, 2F, 2.5F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tofuminced", 2, 0.2f, false, 2f, 0F, 0F, 2F, 2F, 0F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "ricesoborotofu", 8, 0.8f, false, 1F, 4F, 2F, 2.5F, 0F, 1.5F, 2.25f, 1f, 480f),	
    				new FoodInfo(TofuMain.MODID + "." + "okarastick", 4, 0.5f, false, 1f, 0F, 1F, 4F, 0F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "edamameboiled", 2, 0.2f, false, 2f, 2F, 0F, 2F, 0F, 1F, 4F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "saltymelon", 3, 0.2f, false, 4f, 5F, 0F, 0F, 2F, 1F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "chillstick", 6, 0.5f,false, 1f, 0F, 1F, 4F, 1F, 1F, 1F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "tttburger", 8, 0.4f, false, 1f, 3F, 1F, 6F, 0F, 1F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "fukumeni", 3, 0.4f, false, 3f, 0F, 0F, 4F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "meatwrapped_yuba", 4, 0.4f, false, 2f, 0F, 1F, 6F, 0F, 1F, 3F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "apricotjerry_bread", 6, 0.5f, false, 1f, 3F, 0F, 2F, 1F, 1F, 2F, 1F, 480F),
    				new FoodInfo(TofuMain.MODID + "." + "kinako_bread", 6, 0.5f, false, 1f, 3F, 1F, 2F, 0F, 1F, 2F, 1F, 480F),
    		}
    );
    public static ItemBase material = new ItemBase(TofuMain.MODID,"material", 64,
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
    public static ItemFoodBase tsuyuBowl = new ItemFoodContain(TofuMain.MODID,"tsuyuBowl_glass", 64,
    		new FoodInfo[]{
    				new FoodInfo(TofuMain.MODID + "." + "tsuyubowl_glass", 2, 0.1F, false, 4F, 0, 1f, 2f, 0, 1f, 0, 0, -1)
    		},
            new ItemStack[]{
                    new ItemStack(material, 1, 29),
            }
    );
    public static ItemFoodBase foodsetContain = new ItemFoodContain(TofuMain.MODID,"foodsetcontain", 64,
    		new FoodInfo[]{
    				new FoodInfo(TofuMain.MODID + "." + "mabodofu", 16, 0.6f, false, 2F, 1F, 2f, 3f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "moyashiitame", 8, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "moyashiohitashi", 5, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "hiyayakko_glass", 6, 0.5f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "nattohiyayakko_glass", 8, 0.8f, false, 2F, 1F, 1f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "tofusomenbowl_glass", 3, 0.4f, false, 2F, 3F, 0f, 1f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "tastystew", 20, 1f, false, 3F, 4F, 4f, 4f, 4f, 4f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "tastybeefstew", 20, 1f, false, 3F, 4F, 4f, 4f, 4f, 4f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "yudofu", 5, 0.4f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "misosoup", 3, 0.2f, false, 5F, 1F, 0f, 2f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "misodengaku", 5, 0.4f, false, 2F, 3F, 1f, 4f, 0f, 1f, 2f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "nikujaga", 10, 0.8f, false, 2F, 3F, 2f, 4f, 3f, 2f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "agedashitofu", 6, 0.6f, false, 5F, 0F, 1f, 4f, 0f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "koyadofustew", 8, 0.8f, false, 3F, 2F, 1f, 3f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "apricot", 2, 0.2f, false, 4F, 1F, 0f, 0f, 1f, 0f, 3f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "soborotofusaute", 7, 0.6f, false, 2F, 1F, 1f, 4f, 0f, 1f, 3f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "hiyayakko", 6, 0.5f, false, 2F, 0F, 0f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "goheimochi", 5, 0.5f, false, 1F, 4F, 0f, 2f, 0f, 1f, 3f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "nanbantofu", 8, 0.5f, false, 2F, 2F, 2f, 4f, 1f, 1f, 4f, 1f, 480f),
    				new FoodInfo(TofuMain.MODID + "." + "nanban", 10, 0.5f, false, 2F, 2F, 2f, 4f, 1f, 1f, 4f, 1f, 480f),
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
    				TofuMain.MODID + "." + "bottlesoysause",
    				TofuMain.MODID + "." + "dashi",
    				TofuMain.MODID + "." + "soyoil",
    				TofuMain.MODID + "." + "doubanjiang",
    				TofuMain.MODID + "." + "mayonnaise",
    				TofuMain.MODID + "." + "apricotjerry",
    				TofuMain.MODID + "." + "strawberryjam",
    		});

    public static Item defatting_potion = new ItemDefattingPotion();

    public static Item wrench = new ItemWrench();

    public static ItemDrinkBase soymilk_drink = new ItemDrinkBase(TofuMain.MODID,"soymilk_drink",
    		new FoodInfo[]{
    			new FoodInfo(TofuMain.MODID + "." + "soymilk", 2, 0.2F, false, 50F, 0F, 0F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_annin", 2, 0.2F, false, 50F, 1F, 0F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_apple", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_cocoa", 2, 0.2F, false, 50F, 1F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_kinako", 2, 0.2F, false, 50F, 1F, 0F, 5F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_pudding", 2, 0.2F, false, 50F, 1F, 1F, 4F, 0F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_pumpkin", 2, 0.2F, false, 50F, 2F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_sakura", 2, 0.2F, false, 50F, 0F, 0F, 4F, 1F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_strawberry", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_tea", 2, 0.2F, false, 50F, 0F, 0F, 4F, 2F, 1F, 5F, 0F, -1F),
    			new FoodInfo(TofuMain.MODID + "." + "soymilk_zunda", 2, 0.2F, false, 50F, 1F, 1F, 4F, 2F, 1F, 5F, 0F, -1F),
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
        ItemRegister.registerRender(mineral_soymilk);
        ItemRegister.registerRender(soybeansHell);
        ItemRegister.registerRender(tofu_slime_radar);
        ItemRegister.registerRender(soymilk_ramune);
        ItemRegister.registerRender(soymilk_drink);
        ItemRegister.registerRender(RollingPin);
        ItemRegister.registerRender(koujiBase);
        ItemRegister.registerRender(defatting_potion);
        ItemRegister.registerRender(sauce_bottle);
        ItemRegister.registerRender(material);
        ItemRegister.registerRender(tofu_food);
        ItemRegister.registerRender(tofu_hell);
        ItemRegister.registerRender(zundaMochi);
        ItemRegister.registerRender(tofu_material);
        ItemRegister.registerRender(foodset);
        ItemRegister.registerRender(tsuyuBowl);
        ItemRegister.registerRender(foodsetContain);
        ItemRegister.registerRender(nigari);
        ItemRegister.registerRender(soybeans);
        ItemRegister.registerRender(fukumame);
        ItemRegister.registerRender(rice);
        ItemRegister.registerRender(riceseed);
        ItemRegister.registerRender(tofustick);
        ItemRegister.registerRender(bugle);
        ItemRegister.registerRender(tofuhoe);

        ItemRegister.registerRender(zundaruby);

        ItemRegister.registerRender(kinuTofuSword);
        ItemRegister.registerRender(momenTofuSword);
        ItemRegister.registerRender(ishiTofuSword);
        ItemRegister.registerRender(metalTofuSword);
        ItemRegister.registerRender(diamondTofuSword);

        ItemRegister.registerRender(kinuTofuPickaxe);
        ItemRegister.registerRender(momenTofuPickaxe);
        ItemRegister.registerRender(ishiTofuPickaxe);
        ItemRegister.registerRender(metalTofuPickaxe);
        ItemRegister.registerRender(diamondTofuPickaxe);

        ItemRegister.registerRender(kinuTofuShovel);
        ItemRegister.registerRender(momenTofuShovel);
        ItemRegister.registerRender(ishiTofuShovel);
        ItemRegister.registerRender(metalTofuShovel);
        ItemRegister.registerRender(diamondTofuShovel);

        ItemRegister.registerRender(kinuTofuAxe);
        ItemRegister.registerRender(momenTofuAxe);
        ItemRegister.registerRender(ishiTofuAxe);
        ItemRegister.registerRender(metalTofuAxe);
        ItemRegister.registerRender(diamondTofuAxe);

        ItemRegister.registerRender(TOFUKINU_DOOR);
        ItemRegister.registerRender(TOFUMOMEN_DOOR);
        ItemRegister.registerRender(TOFUISHI_DOOR);
        ItemRegister.registerRender(TOFUMETAL_DOOR);

        ItemRegister.registerRender(kinuhelmet);
        ItemRegister.registerRender(kinuchestplate);
        ItemRegister.registerRender(kinuleggins);
        ItemRegister.registerRender(kinuboots);

        ItemRegister.registerRender(momenhelmet);
        ItemRegister.registerRender(momenchestplate);
        ItemRegister.registerRender(momenleggins);
        ItemRegister.registerRender(momenboots);

        ItemRegister.registerRender(solidhelmet);
        ItemRegister.registerRender(solidchestplate);
        ItemRegister.registerRender(solidleggins);
        ItemRegister.registerRender(solidboots);

        ItemRegister.registerRender(metalhelmet);
        ItemRegister.registerRender(metalchestplate);
        ItemRegister.registerRender(metalleggins);
        ItemRegister.registerRender(metalboots);

        ItemRegister.registerRender(diamondhelmet);
        ItemRegister.registerRender(diamondchestplate);
        ItemRegister.registerRender(diamondleggins);
        ItemRegister.registerRender(diamondboots);

        ItemRegister.registerRender(zundaBow);
        ItemRegister.registerRender(zundaArrow);
        ItemRegister.registerRender(anninApple);
        ItemRegister.registerRender(tofuforce_core);
        ItemRegister.registerRender(tofucore);
        ItemRegister.registerRender(tofuforce_sword);
        ItemRegister.registerRender(tofuishi_shield);
        ItemRegister.registerRender(tofumetal_shield);
//        registerRender(fulintlock);
        ItemRegister.registerRender(tofuchinger_tooth);
        ItemRegister.registerRender(tofuchinger_tootharrow);
    }

    private static void register(Item item) {
        item.setCreativeTab(CommonProxy.tab);
       	ItemRegister.register(TofuMain.MODID, item);
    }

    private static void tofuItemRegister(TofuType type, ItemStack item) {
        ItemLoader.tofuItems.put(type, item);
    }
}
