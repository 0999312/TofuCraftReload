package cn.mcmod.tofucraft.compat;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerTraits;

public class TConstructCompat {

    public static final Material tofumetal = new Material("tofumetal", 0xc6d7e1);
    public static final Material tofudiamond = new Material("tofudiamond", 0xB7DDF1);
    public static final AbstractTrait absorption = new TraitAbsorption();

    public static void preInit() {
        TinkerRegistry.addMaterialStats(TConstructCompat.tofumetal,
                new HeadMaterialStats(320, 6.0f, 4.0f, HarvestLevels.DIAMOND),
                new BowMaterialStats(0.9f, 2f, 2f)
        );
        TinkerRegistry.addMaterialStats(TConstructCompat.tofudiamond,
                new HeadMaterialStats(1200, 7.5f, 6.0f, HarvestLevels.OBSIDIAN),
                new BowMaterialStats(0.9f, 2.5f, 3.5f)
        );

        TinkerRegistry.integrate(TConstructCompat.tofumetal).preInit();
        TinkerRegistry.integrate(TConstructCompat.tofudiamond).preInit();
    }

    public static void init() {
        TConstructCompat.tofumetal.addItem(new ItemStack(ItemLoader.tofu_material, 1, 0), 1, Material.VALUE_Ingot);
        TConstructCompat.tofumetal.addItem(new ItemStack(BlockLoader.METALTOFU, 1, 0), 1, Material.VALUE_Ingot * 4);

        TConstructCompat.tofudiamond.addItem(new ItemStack(ItemLoader.tofu_material, 1, 1), 1, Material.VALUE_Ingot);
        TConstructCompat.tofudiamond.addItem(new ItemStack(BlockLoader.TOFUDIAMOND, 1, 0), 1, Material.VALUE_Ingot * 4);

        TConstructCompat.tofudiamond
	        .addTrait(TinkerTraits.holy)
	        .addTrait(TConstructCompat.absorption)
	        .setCraftable(true)
	        .setRepresentativeItem(new ItemStack(ItemLoader.tofu_material, 1, 1));
        TConstructCompat.tofumetal
            .addTrait(TinkerTraits.lightweight)
            .addTrait(TConstructCompat.absorption)
            .setCraftable(true)
            .setRepresentativeItem(new ItemStack(ItemLoader.tofu_material, 1, 0));
    }
}
