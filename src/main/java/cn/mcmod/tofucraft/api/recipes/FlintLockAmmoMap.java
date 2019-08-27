package cn.mcmod.tofucraft.api.recipes;

import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import cn.mcmod.tofucraft.item.fulintlock.propellants.PropellantBlaze;
import cn.mcmod.tofucraft.item.fulintlock.propellants.PropellantGunpowder;
import cn.mcmod.tofucraft.item.fulintlock.warheads.WarheadBlazeRod;
import cn.mcmod.tofucraft.item.fulintlock.warheads.WarheadSoybean;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FlintLockAmmoMap {
    private static HashMap<String, Class<? extends Warhead>> warheads = new HashMap<>();
    private static HashMap<String, Class<? extends Propellant>> propellants = new HashMap<>();

    private static HashMap<String, Warhead> defaultInstWarhead = new HashMap<>();
    private static HashMap<String, Propellant> defaultInstPropellant = new HashMap<>();

    public static void init() {
        registerPropellant("gunpowder", PropellantGunpowder.class);
        registerWarhead("soy", WarheadSoybean.class);
        registerWarhead("blazerod", WarheadBlazeRod.class);
        registerPropellant("blaze", PropellantBlaze.class);
    }

    public static HashMap<String, Class<? extends Propellant>> getPropellants() {
        return propellants;
    }

    public static HashMap<String, Class<? extends Warhead>> getWarheads() {
        return warheads;
    }

    public static HashMap<String, Propellant> getDefaultInstPropellant() {
        return defaultInstPropellant;
    }

    public static HashMap<String, Warhead> getDefaultInstWarhead() {
        return defaultInstWarhead;
    }

    public static Map.Entry<String, Class<? extends Warhead>> getWarhead(ItemStack item) {
        for (Map.Entry<String, Class<? extends Warhead>> entry : warheads.entrySet()) {
            ItemStack warheadItem = null;
            try {
                warheadItem = entry.getValue().newInstance().getAmmoItem();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (warheadItem != null)
                if (warheadItem.isItemEqual(item) && warheadItem.getCount() <= item.getCount())
                    return entry;
        }
        return null;
    }

    public static Map.Entry<String, Class<? extends Propellant>> getPropellant(ItemStack item) {
        for (Map.Entry<String, Class<? extends Propellant>> entry : propellants.entrySet()) {
            ItemStack propellantItem = null;
            try {
                propellantItem = entry.getValue().newInstance().getPropellantItem();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (propellantItem != null)
                if (propellantItem.isItemEqual(item) && propellantItem.getCount() <= item.getCount())
                    return entry;
        }
        return null;
    }

    public static Map.Entry<String, Warhead> getWarheadInst(ItemStack item) {
        for (Map.Entry<String, Warhead> entry : defaultInstWarhead.entrySet()) {
            ItemStack entryitem = entry.getValue().getAmmoItem();
            if (entryitem.isItemEqual(item) && entryitem.getCount() <= item.getCount())
                return entry;
        }
        return null;
    }

    public static Map.Entry<String, Propellant> getPropellantInst(ItemStack item) {
        for (Map.Entry<String, Propellant> entry : defaultInstPropellant.entrySet()) {
            ItemStack entryitem = entry.getValue().getPropellantItem();
            if (entryitem.isItemEqual(item) && entryitem.getCount() <= item.getCount())
                return entry;
        }
        return null;
    }

    public static void registerPropellant(String key, Class<? extends Propellant> value) {
        propellants.put(key, value);
        try {
            defaultInstPropellant.put(key, value.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerWarhead(String key, Class<? extends Warhead> value) {
        warheads.put(key, value);
        try {
            defaultInstWarhead.put(key, value.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
