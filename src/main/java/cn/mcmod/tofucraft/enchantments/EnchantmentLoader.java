package cn.mcmod.tofucraft.enchantments;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=TofuMain.MODID)
public class EnchantmentLoader {
	public static final EnumEnchantmentType typeDiamondTofu = EnumHelper.addEnchantmentType("diamond_tofu", 
			(item)->((item instanceof ItemTool||item instanceof ItemSword)));
	public static Enchantment Drain = new EnchantmentDrain().setName("drain").setRegistryName(TofuMain.MODID, "ench_drain");
	public static Enchantment Batch = new EnchantmentBatch().setName("batch").setRegistryName(TofuMain.MODID, "ench_batch");
	@SubscribeEvent
	public static void registerEnchantment(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().registerAll(Drain,Batch);
	}
}
