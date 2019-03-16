package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.material.TofuArmorMaterial;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.JSON_Creator;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class ItemLoader {
	public static EnumMap<TofuType, ItemStack> tofuItems = Maps.newEnumMap(TofuType.class);

	public static ItemFoodBasic tofu_food = new ItemFoodBasic("tofu_food", 64,
			  new int[]{2   ,2   ,3   ,3   ,4   ,4   ,4   ,4   ,4   ,4   ,3   ,5   ,2},
			new float[]{0.1F,0.1F,0.4F,0.2F,0.2F,0.2f,0.2f,0.2f,0.2f,0.2f,0.2f,0.8F,0.2F},
			new String[]{
					TofuMain.MODID+"."+"tofukinu",
					TofuMain.MODID+"."+"tofumomen",
					TofuMain.MODID+"."+"tofuishi",
					TofuMain.MODID+"."+"tofugrilled",
					TofuMain.MODID+"."+"tofufriedpouch",
					TofuMain.MODID+"."+"tofufried",
					TofuMain.MODID+"."+"tofuegg",
					TofuMain.MODID+"."+"tofuannin",
					TofuMain.MODID+"."+"tofusesame",
					TofuMain.MODID+"."+"tofuzunda",
					TofuMain.MODID+"."+"tofustrawberry",
					TofuMain.MODID+"."+"tofumiso",
					TofuMain.MODID+"."+"tofuglow"
			}
	);
	public static ItemFood tofu_hell = (ItemFood)new ItemFood(2, 0.2f, false)
			.setPotionEffect(new PotionEffect(Potion.getPotionById(12), 30, 0), 1.0F)
			.setUnlocalizedName(TofuMain.MODID+"."+"tofuhell");
	public static ItemBase tofu_material = new ItemBase("tofu_material", 64, new String[]{
			TofuMain.MODID+"."+"tofumetal",
			TofuMain.MODID+"."+"tofudiamond",
			TofuMain.MODID+"."+"tofudried"
	});
	public static ItemFoodBasic foodset = new ItemFoodBasic("foodset", 64,
			  new int[]{6   ,5   ,4   ,5   ,6   ,6   ,2   ,6   ,10  ,12  ,2    ,5   ,4   ,6   ,5   ,4,4   ,6   ,16  ,20,2   ,8 ,7 ,1,3},
			new float[]{0.4f,0.3f,0.4f,0.6f,0.8f,0.8f,0.5f,0.5f,0.2f,0.8f,0.15f,0.6f,0.4f,0.6f,0.8f,0.6f,0.6f,0.6f,0.6f,1f,0.2f,0.6f,0.6f,0.1f,0.3f},
			new String[]{
					TofuMain.MODID+"."+"tofuchikuwa",
					TofuMain.MODID+"."+"oage",
					TofuMain.MODID+"."+"onigiri",
					TofuMain.MODID+"."+"onigirisalt",
					TofuMain.MODID+"."+"yakionigirimiso",
					TofuMain.MODID+"."+"yakionigirishoyu",
					TofuMain.MODID+"."+"sprouts",
					TofuMain.MODID+"."+"hiyayakko",
					TofuMain.MODID+"."+"ricetofu",
					TofuMain.MODID+"."+"tofuhamburg",
					TofuMain.MODID+"."+"tofucookie",
					TofuMain.MODID+"."+"inari",
					TofuMain.MODID+"."+"tofufishraw",
					TofuMain.MODID+"."+"tofufishcooked",
					TofuMain.MODID+"."+"kinakomochi",
					TofuMain.MODID+"."+"kinakomanju",
					TofuMain.MODID+"."+"chikuwa",
					TofuMain.MODID+"."+"tofusteak",
					TofuMain.MODID+"."+"tofuhamburgt",
					TofuMain.MODID+"."+"tofuhamburgta",
					TofuMain.MODID+"."+"tofuminced",
					TofuMain.MODID+"."+"ricesoborotofu",
					TofuMain.MODID+"."+"soborotofusaute",
					TofuMain.MODID+"."+"edamameboiled",
					TofuMain.MODID+"."+"saltymelon"
			}
	);
	public static ItemBase material = new ItemBase("material", 64, new String[]{
			TofuMain.MODID+"."+"salt",
			TofuMain.MODID+"."+"kouji",
			TofuMain.MODID+"."+"miso",
			TofuMain.MODID+"."+"edamame",
			TofuMain.MODID+"."+"zunda",
			TofuMain.MODID+"."+"barrelempty",
			TofuMain.MODID+"."+"soybeansparched",
			TofuMain.MODID+"."+"kinako",
			TofuMain.MODID+"."+"natto",
			TofuMain.MODID+"."+"apricotseed",
			TofuMain.MODID+"."+"filtercloth",
			TofuMain.MODID+"."+"okara",
			TofuMain.MODID+"."+"mincedpotato",
			TofuMain.MODID+"."+"starchraw",
			TofuMain.MODID+"."+"starch",
			TofuMain.MODID+"."+"kyoninso",
			TofuMain.MODID+"."+"leek",
			TofuMain.MODID+"."+"zundama",
			TofuMain.MODID+"."+"tofugem",
			TofuMain.MODID+"."+"tofudiamondnugget",
			TofuMain.MODID+"."+"tofuhamburgraw",
			TofuMain.MODID+"."+"tfcapacitor",
			TofuMain.MODID+"."+"tfcircuit",
			TofuMain.MODID+"."+"tfcoil",
			TofuMain.MODID+"."+"tfoscillator",
			TofuMain.MODID+"."+"advtofugem",
			TofuMain.MODID+"."+"activatedtofugem",
			TofuMain.MODID+"."+"activatedhelltofu",
			TofuMain.MODID+"."+"tofusomen",
			TofuMain.MODID+"."+"glassbowl",
			TofuMain.MODID+"."+"rollingpin"
	});
	public static ItemFood zundaMochi = (ItemFood)new ItemFood(3, 0.8f, false)
			.setPotionEffect(new PotionEffect(Potion.getPotionById(10), 20, 2), 1.0F)
			.setUnlocalizedName(TofuMain.MODID+"."+"zundamochi");
	public static ItemFoodBasic tsuyuBowl = new ItemFoodContain("tsuyuBowl_glass", 1,
			  new int[]{2   },
			new float[]{0.1f},
			new String[]{
					TofuMain.MODID+"."+"tsuyubowl_glass"},
			new ItemStack[]{
					new ItemStack(material,1,29),	
			}
	);
	public static ItemFoodBasic foodsetContain = new ItemFoodContain("foodsetContain", 1,
			  new int[]{16  ,8   ,5   ,6   ,8   ,3   ,20,20,3   ,5   ,10  ,6   ,8   ,3   },
			new float[]{0.5f,0.4f,0.3f,0.5f,0.8f,0.3f,1F,1f,0.3f,0.6f,0.7f,0.6f,0.8f,0.3f},
			new String[]{
					TofuMain.MODID+"."+"mabodofu",
					TofuMain.MODID+"."+"moyashiitame",
					TofuMain.MODID+"."+"moyashiohitashi",
					TofuMain.MODID+"."+"hiyayakko_glass",
					TofuMain.MODID+"."+"nattohiyayakko_glass",
					TofuMain.MODID+"."+"tofusomenbowl_glass",
					TofuMain.MODID+"."+"tastystew",
					TofuMain.MODID+"."+"tastybeefstew",
					TofuMain.MODID+"."+"yudofu",
					TofuMain.MODID+"."+"misosoup",
					TofuMain.MODID+"."+"misodengaku",
					TofuMain.MODID+"."+"nikujaga",
					TofuMain.MODID+"."+"agedashitofu",
					TofuMain.MODID+"."+"koyadofustew",
					TofuMain.MODID+"."+"apricot"
			},
			new ItemStack[]{
					
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(material,1,29),	
					new ItemStack(material,1,29),	
					new ItemStack(tsuyuBowl),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.STICK),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(Items.BOWL),	
					new ItemStack(material,1,9)
			}
	);
	public static ItemSwordBasic kinuTofuSword = new ItemSwordBasic(TofuToolMaterial.KINU,"swordkinu");
	public static ItemSwordBasic momenTofuSword = new ItemSwordBasic(TofuToolMaterial.MOMEN,"swordmomen");
	public static ItemSwordBasic ishiTofuSword = new ItemSwordBasic(TofuToolMaterial.SOLID,"swordsolid");
	public static ItemSwordBasic metalTofuSword = new ItemSwordBasic(TofuToolMaterial.METAL,"swordmetal");

	public static ItemPickaxeBasic kinuTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.KINU,"toolkinupickaxe");
	public static ItemPickaxeBasic momenTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.MOMEN,"toolmomenpickaxe");
	public static ItemPickaxeBasic ishiTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.SOLID,"toolsolidpickaxe");
	public static ItemPickaxeBasic metalTofuPickaxe = new ItemPickaxeBasic(TofuToolMaterial.METAL,"toolmetalpickaxe");

	public static ItemArmorBase kinuhelmet = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.HEAD,"armorkinuhelmet").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");
	public static ItemArmorBase kinuchestplate = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.CHEST,"armorkinuchestplate").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");
	public static ItemArmorBase kinuleggins = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.LEGS,"armorkinuleggins").setArmorTexture("tofucraft:textures/armor/armor_kinu_2.png");
	public static ItemArmorBase kinuboots = new ItemArmorBase(TofuArmorMaterial.KINU, EntityEquipmentSlot.FEET,"armorkinuboots").setArmorTexture("tofucraft:textures/armor/armor_kinu_1.png");

	public static ItemArmorBase momenhelmet = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.HEAD,"armormomenhelmet").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");
	public static ItemArmorBase momenchestplate = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.CHEST,"armormomenchestplate").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");
	public static ItemArmorBase momenleggins = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.LEGS,"armormomenleggins").setArmorTexture("tofucraft:textures/armor/armor_momen_2.png");
	public static ItemArmorBase momenboots = new ItemArmorBase(TofuArmorMaterial.MOMEN, EntityEquipmentSlot.FEET,"armormomenboots").setArmorTexture("tofucraft:textures/armor/armor_momen_1.png");

	public static ItemArmorBase solidhelmet = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.HEAD,"armorsolidhelmet").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");
	public static ItemArmorBase solidchestplate = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.CHEST,"armorsolidchestplate").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");
	public static ItemArmorBase solidleggins = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.LEGS,"armorsolidleggins").setArmorTexture("tofucraft:textures/armor/armor_solid_2.png");
	public static ItemArmorBase solidboots = new ItemArmorBase(TofuArmorMaterial.SOLID, EntityEquipmentSlot.FEET,"armorsolidboots").setArmorTexture("tofucraft:textures/armor/armor_solid_1.png");

	public static ItemArmorBase metalhelmet = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.HEAD,"armormetalhelmet").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");
	public static ItemArmorBase metalchestplate = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.CHEST,"armormetalchestplate").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");
	public static ItemArmorBase metalleggins = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.LEGS,"armormetalleggins").setArmorTexture("tofucraft:textures/armor/armor_metal_2.png");
	public static ItemArmorBase metalboots = new ItemArmorBase(TofuArmorMaterial.METAL, EntityEquipmentSlot.FEET,"armormetalboots").setArmorTexture("tofucraft:textures/armor/armor_metal_1.png");

	public static ItemSoybeans soybeans = new ItemSoybeans();

	public static Item nigari = new ItemNigari();
	public static Item tofustick = new ItemTofuStick();

	public static Item zundaruby = new Item();

	public static ItemDoor TOFUKINU_DOOR = new ItemDoor(BlockLoader.TOFUKINU_DOOR);
	public static ItemDoor TOFUMOMEN_DOOR = new ItemDoor(BlockLoader.TOFUMOMEN_DOOR);
	public static ItemDoor TOFUISHI_DOOR = new ItemDoor(BlockLoader.TOFUISHI_DOOR);
	public static ItemDoor TOFUMETAL_DOOR = new ItemDoor(BlockLoader.TOFUMETAL_DOOR);
	
	public ItemLoader(FMLPreInitializationEvent event) {
		register(material);
		register(tofu_food);
		register(tofu_hell);
		register(tofu_material);
		register(foodset);
		register(zundaMochi);
		register(tsuyuBowl);
		register(foodsetContain);
		register(nigari);
		register(soybeans);
		register(tofustick);

		MinecraftForge.addGrassSeed(new ItemStack(soybeans), 2);

		register(kinuTofuSword);
		register(momenTofuSword);
		register(ishiTofuSword);
		register(metalTofuSword);

		register(zundaruby.setUnlocalizedName(TofuMain.MODID+"."+"zundaruby"));

		register(kinuTofuPickaxe);
		register(momenTofuPickaxe);
		register(ishiTofuPickaxe);
		register(metalTofuPickaxe);

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

		register(TOFUKINU_DOOR.setUnlocalizedName(TofuMain.MODID+"."+"tofudoor_kinu"));
		register(TOFUMOMEN_DOOR.setUnlocalizedName(TofuMain.MODID+"."+"tofudoor_momen"));
		register(TOFUISHI_DOOR.setUnlocalizedName(TofuMain.MODID+"."+"tofudoor_ishi"));
		register(TOFUMETAL_DOOR.setUnlocalizedName(TofuMain.MODID+"."+"tofudoor_metal"));

		tofuItemRegister(TofuType.kinu,new ItemStack(tofu_food));
		tofuItemRegister(TofuType.momen,new ItemStack(tofu_food,1,1));
		tofuItemRegister(TofuType.ishi,new ItemStack(tofu_food,1,2));
		tofuItemRegister(TofuType.grilled,new ItemStack(tofu_food,1,3));
		tofuItemRegister(TofuType.friedPouch,new ItemStack(tofu_food,1,4));
		tofuItemRegister(TofuType.fried,new ItemStack(tofu_food,1,5));
		tofuItemRegister(TofuType.egg,new ItemStack(tofu_food,1,6));
		tofuItemRegister(TofuType.metal,new ItemStack(tofu_material));
		tofuItemRegister(TofuType.diamond,new ItemStack(tofu_material,1,1));
		tofuItemRegister(TofuType.zunda,new ItemStack(tofu_food,1,9));

		//boildEdamame
		GameRegistry.addSmelting( new ItemStack(material,1,3), new ItemStack(foodset,16,23), 0.25f);
		//SoyBeenParched
		GameRegistry.addSmelting( new ItemStack(soybeans,1), new ItemStack(material,1,6), 0.2f);

		GameRegistry.addSmelting( new ItemStack(material,1,13), new ItemStack(material,1,14), 0.2f);
		GameRegistry.addSmelting(new ItemStack(tofu_food,1,0), new ItemStack(tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(new ItemStack(tofu_food,1,1), new ItemStack(tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(new ItemStack(tofu_food,1,2), new ItemStack(foodset,1,17), 0.2f);
		GameRegistry.addSmelting(new ItemStack(material,1,20), new ItemStack(foodset,1,9), 0.2f);
		GameRegistry.addSmelting(new ItemStack(foodset,1,12), new ItemStack(foodset,1,13), 0.2f);
		
		GameRegistry.addSmelting(BlockLoader.KINUTOFU, new ItemStack(BlockLoader.GRILD), 0.6f);
		GameRegistry.addSmelting(BlockLoader.MOMENTOFU, new ItemStack(BlockLoader.GRILD), 0.6f);
	}
	@SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
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
		registerRender(tofustick);

		registerRender(zundaruby);

		registerRender(kinuTofuSword);
		registerRender(momenTofuSword);
		registerRender(ishiTofuSword);
		registerRender(metalTofuSword);

		registerRender(kinuTofuPickaxe);
		registerRender(momenTofuPickaxe);
		registerRender(ishiTofuPickaxe);
		registerRender(metalTofuPickaxe);

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
    }

	private static void register(Item item)
    {
		item.setCreativeTab(CommonProxy.tab);

        ForgeRegistries.ITEMS.register(item.setRegistryName(item.getUnlocalizedName().substring(5+TofuMain.MODID.length()+1)));
    }

	private static void tofuItemRegister(TofuType type,ItemStack item){
		ItemLoader.tofuItems.put(type, item);

	}

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemBase item)
    {
    	registerRender(item, false);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic item)
    {
    	registerRender(item, false);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemBase item,boolean json_create)
    {
    	for(int i = 0;i<item.getSubNames().length;i++){
    		String name = item.getSubNames()[i].substring(TofuMain.MODID.length()+1);
    		if(json_create)JSON_Creator.genItem(name, name, "json_create");
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(TofuMain.MODID,name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic item,boolean json_create)
    {
    	
    	for(int i = 0;i<item.getSubNames().length;i++){
    		String name = item.getSubNames()[i].substring(TofuMain.MODID.length()+1);
        	if(json_create)JSON_Creator.genItem(name, name, "json_create");
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(TofuMain.MODID,name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, int meta, String name)
    {
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }
    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, int meta, String name,String textureName)
    {
    	JSON_Creator.genItem(name, textureName, "json_create");
        ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }
	@SideOnly(Side.CLIENT)
    private static void registerRender(Item item,String textureName)
    {
		JSON_Creator.genItem(item.getRegistryName().toString().substring(TofuMain.MODID.length()+1), textureName, "json_create");
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }
	@SideOnly(Side.CLIENT)
    private static void registerRender(Item item)
    {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }

}
