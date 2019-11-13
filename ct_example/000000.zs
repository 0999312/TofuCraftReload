import mods.tofucraft.tofuCrusher;
import mods.tofucraft.tofuCompressor;
import mods.tofucraft.tofuAdvancedAggregator;

tofuCrusher.ClearAllRecipe();
tofuCrusher.AddRecipe(<ore:cobblestone>, <item:minecraft:diamond>);

tofuCompressor.RemoveRecipe(<tofucraft:blocktofuishi>);
tofuCompressor.AddRecipe(<item:minecraft:diamond>*5, <tofucraft:blocktofudiamond>);

tofuAdvancedAggregator.AddRecipe([<item:minecraft:diamond>,<item:minecraft:diamond>,<item:minecraft:diamond>,<item:minecraft:diamond>],<tofucraft:blocktofuishi>);