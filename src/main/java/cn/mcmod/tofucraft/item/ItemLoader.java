package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.CommonProxy;
import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.util.JSON_Creator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.ForgeRegistry;

public class ItemLoader {
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
	
	public ItemLoader(FMLPreInitializationEvent event) {
		register(material);
		register(tofu_food);
		register(tofu_hell);
		register(tofu_material);
		register(foodset);
		register(zundaMochi);
		register(tsuyuBowl);
		register(foodsetContain);
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
    }
	private static void register(Item item)
    {
		item.setCreativeTab(CommonProxy.tab);
        ForgeRegistries.ITEMS.register(item.setRegistryName(item.getUnlocalizedName().substring(5+TofuMain.MODID.length()+1)));
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
