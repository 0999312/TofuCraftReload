package cn.mcmod.tofucraft.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static boolean getBoolean(NBTTagCompound compound, String key, boolean ifnone) {
        if (compound == null) return ifnone;
        return compound.hasKey(key) ? compound.getBoolean(key) : ifnone;
    }

    public static int getInteger(NBTTagCompound compound, String key, int ifnone) {
        if (compound == null) return ifnone;
        return compound.hasKey(key) ? compound.getInteger(key) : ifnone;
    }

    public static String getString(NBTTagCompound compound, String key, String ifnone) {
        if (compound == null) return ifnone;
        return compound.hasKey(key) ? compound.getString(key) : ifnone;
    }

    public static NBTTagCompound setString(NBTTagCompound compound, String key, String value) {
        if (compound == null) compound = new NBTTagCompound();
        compound.setString(key, value);
        return compound;
    }

    public static NBTTagCompound setInteger(NBTTagCompound compound, String key, int value) {
        if (compound == null) compound = new NBTTagCompound();
        compound.setInteger(key, value);
        return compound;
    }

    public static NBTTagCompound setBoolean(NBTTagCompound compound, String key, boolean value) {
        if (compound == null) compound = new NBTTagCompound();
        compound.setBoolean(key, value);
        return compound;
    }
}
