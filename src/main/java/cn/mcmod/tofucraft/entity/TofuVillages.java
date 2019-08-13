package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;

public class TofuVillages {

    public static int PROFESSION_FARMER_ID = 0;
    public static int CAREER_FARMER_ID = 0;

    public static VillagerRegistry.VillagerProfession ProfessionTofuCook;
    public static VillagerRegistry.VillagerCareer CareerTofuCook;

    public static VillagerRegistry.VillagerProfession ProfessionTofunian;
    public static VillagerRegistry.VillagerCareer CareerTofunianTofuCrafter;
    public static VillagerRegistry.VillagerCareer CareerTofunian;
    public static VillagerRegistry.VillagerProfession ProfessionHunterTofunian;
    public static VillagerRegistry.VillagerCareer CareerHunter;

    public static void register() {
        // Register the profession of Tofu Cook
        TofuVillages.ProfessionTofuCook = new VillagerRegistry.VillagerProfession("tofucraft:tofuCook",
                new ResourceLocation("tofucraft", "textures/mob/tofucook.png").toString(),//new ResourceLocation("tofucraft", "textures/mob/tofucook.png").toString(),
                "minecraft:textures/entity/zombie_villager/zombie_villager.png");
        TofuVillages.CareerTofuCook = new VillagerRegistry.VillagerCareer(TofuVillages.ProfessionTofuCook, "tofuCook")
                .addTrade(1, new TradeListTCookLv1())
                .addTrade(2, new TradeListTCookLv2())
                .addTrade(3, new TradeListTCookLv4());
        ForgeRegistries.VILLAGER_PROFESSIONS.register((TofuVillages.ProfessionTofuCook));
        VillagerRegistry.getById(0).getCareer(0).addTrade(2, new TradeListFarmerLv2());
    }

    public static class TradeListFarmerLv2 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            //Buy
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.rice, 16 + random.nextInt(8)), new ItemStack(Items.EMERALD)));
        }
    }


    public static class TradeListTCookLv1 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            //Buy
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.soybeans, 18 + random.nextInt(4)), new ItemStack(Items.EMERALD)));
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.tofu_food, 14 + random.nextInt(4),1), new ItemStack(Items.EMERALD)));
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.material, 6 + random.nextInt(4),5), new ItemStack(Items.EMERALD)));
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.material, 14 + random.nextInt(4),3), new ItemStack(Items.EMERALD)));
            //Sell
        }
    }

    public static class TradeListTCookLv2 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            //Buy
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.material, 18 + random.nextInt(4),2), new ItemStack(Items.EMERALD)));
            //Sell
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(ItemLoader.tofu_food, 17 + random.nextInt(8),3)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(ItemLoader.tofu_food, 10 + random.nextInt(4),4)));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(ItemLoader.tofu_food, 12 + random.nextInt(4),5)));
        }
    }

    public static class TradeListTCookLv4 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            //Sell
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(ItemLoader.tofu_material, 2 + random.nextInt(2))));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD), new ItemStack(ItemLoader.tofu_material, 8 + random.nextInt(4),2)));
        }
    }

    private static class TradeListTofunianLv1 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.tofu_food,26 + random.nextInt(4),2), new ItemStack(ItemLoader.zundaruby, 1)));
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby,1 + random.nextInt(1)), new ItemStack(ItemLoader.tofustick, 1)));
        }
    }
    private static class TradeListTofunianLv2 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {

            if(random.nextBoolean()) {
                ItemStack enchantedItemStack = new ItemStack(ItemLoader.metalTofuSword, 1);
                ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(enchantedItemStack.getItem(), 1, enchantedItemStack.getMetadata()), 5 + random.nextInt(15), false);
                recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 5 + random.nextInt(4)), itemstack1));
            }
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.tofu_hell,10 + random.nextInt(6)), new ItemStack(ItemLoader.zundaruby)));
        }
    }

    private static class TradeListTofunianLv3 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {

            if(random.nextBoolean()) {
                recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 2 + random.nextInt(3)), new ItemStack(ItemLoader.material,1,22)));
            }
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.tofu_food,10 + random.nextInt(6),3), new ItemStack(ItemLoader.zundaruby)));
        }
    }

    private static class TradeListTofunianLv4 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {

            if(random.nextBoolean()) {
                ItemStack enchantedItemStack = new ItemStack(ItemLoader.metalTofuPickaxe, 1);
                ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(enchantedItemStack.getItem(), 1, enchantedItemStack.getMetadata()), 5 + random.nextInt(15), false);
                recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 6 + random.nextInt(4)), itemstack1));
            }

            if(random.nextBoolean()) {
                recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 2 + random.nextInt(3)), new ItemStack(ItemLoader.material,1,23)));
            }else {
                recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 2 + random.nextInt(3)), new ItemStack(ItemLoader.material,1,24)));
            }
        }
    }

    private static class TradeListTofunianFisherLv1 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.COAL, 14 + random.nextInt(4)), new ItemStack(ItemLoader.zundaruby,1)));
            recipeList.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, 1), new ItemStack(ItemLoader.tofu_food,8+ random.nextInt(4))));
        }
    }
    private static class TradeListTofunianFisherLv2 implements EntityVillager.ITradeList {
        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new MerchantRecipe(new ItemStack(Items.STRING, 14 + random.nextInt(4)), new ItemStack(ItemLoader.zundaruby,1)));
        }
    }
}