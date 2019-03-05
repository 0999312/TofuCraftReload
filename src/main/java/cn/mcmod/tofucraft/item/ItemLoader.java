package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.material.TofuToolMaterial;
import cn.mcmod.tofucraft.material.TofuType;
import cn.mcmod.tofucraft.util.JSON_Creator;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.EnumMap;
import java.util.Objects;

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
			  new int[]{6   ,5   ,4   ,5   ,6   ,6   ,2   ,6   ,10  ,12  ,2    ,5   ,4   ,6   ,5   ,4   ,6   ,16  ,20,2   ,8   ,7},
			new float[]{0.4f,0.3f,0.4f,0.6f,0.8f,0.8f,0.5f,0.5f,0.2f,0.8f,0.15f,0.6f,0.4f,0.6f,0.8f,0.6f,0.6f,0.6f,1f,0.2f,0.6f,0.6f},
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
					TofuMain.MODID+"."+"chikuwa",
					TofuMain.MODID+"."+"tofusteak",
					TofuMain.MODID+"."+"tofuhamburgt",
					TofuMain.MODID+"."+"tofuhamburgta",
					TofuMain.MODID+"."+"tofuminced",
					TofuMain.MODID+"."+"ricesoborotofu",
					TofuMain.MODID+"."+"soborotofusaute"
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

	public static ItemSoybeans soybeans = new ItemSoybeans();

	public static Item nigari = new ItemNigari();
	
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

		MinecraftForge.addGrassSeed(new ItemStack(soybeans), 2);

		register(kinuTofuSword);
		register(momenTofuSword);
		register(ishiTofuSword);
		register(metalTofuSword);

		register(kinuTofuPickaxe);
		register(momenTofuPickaxe);
		register(ishiTofuPickaxe);
		register(metalTofuPickaxe);

		tofuItemRegister(TofuType.kinu,new ItemStack(tofu_food));
		tofuItemRegister(TofuType.momen,new ItemStack(tofu_food,1,1));
		tofuItemRegister(TofuType.ishi,new ItemStack(tofu_food,1,2));
		tofuItemRegister(TofuType.grilled,new ItemStack(tofu_food,1,3));
		tofuItemRegister(TofuType.friedPouch,new ItemStack(tofu_food,1,4));
		tofuItemRegister(TofuType.fried,new ItemStack(tofu_food,1,5));
		tofuItemRegister(TofuType.egg,new ItemStack(tofu_food,1,6));
		tofuItemRegister(TofuType.metal,new ItemStack(tofu_material));
		tofuItemRegister(TofuType.diamond,new ItemStack(tofu_material,1,1));

		GameRegistry.addSmelting(tofu_food, new ItemStack(tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(new ItemStack(tofu_food,1,1), new ItemStack(tofu_food,1,3), 0.2f);
		GameRegistry.addSmelting(BlockLoader.KINUTOFU, new ItemStack(BlockLoader.GRILD), 0.8f);
		GameRegistry.addSmelting(BlockLoader.MOMENTOFU, new ItemStack(BlockLoader.GRILD), 0.8f);
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

		registerRender(kinuTofuSword);
		registerRender(momenTofuSword);
		registerRender(ishiTofuSword);
		registerRender(metalTofuSword);

		registerRender(kinuTofuPickaxe);
		registerRender(momenTofuPickaxe);
		registerRender(ishiTofuPickaxe);
		registerRender(metalTofuPickaxe);
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
