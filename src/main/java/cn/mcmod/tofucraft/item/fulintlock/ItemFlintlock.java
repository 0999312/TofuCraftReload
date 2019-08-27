package cn.mcmod.tofucraft.item.fulintlock;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.api.recipes.recipe.Propellant;
import cn.mcmod.tofucraft.api.recipes.recipe.Warhead;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
import cn.mcmod.tofucraft.util.NBTUtil;
import joptsimple.internal.Strings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Map;

public class ItemFlintlock extends Item {

    //State: 0 -> nothing, 1 -> propellant, 2->loaded
    private int state;

    public ItemFlintlock() {
        super();
        setUnlocalizedName(TofuMain.MODID + "." + "fulintlock");
        setMaxStackSize(1);
        setMaxDamage(200);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        state = NBTUtil.getInteger(playerIn.getHeldItem(handIn).getTagCompound(), "state", 0);
        switch (state) {
            case 0:
                for (ItemStack s : playerIn.inventory.mainInventory) {
                    Map.Entry<String, Class<? extends Propellant>> propellantEntry = FlintLockAmmoMap.getPropellant(s);
                    if (propellantEntry != null) {
                        playerIn.setActiveHand(handIn);
                        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                    }
                }
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            case 1:
                for (ItemStack s : playerIn.inventory.mainInventory) {
                    Map.Entry<String, Class<? extends Warhead>> warheadEntry = FlintLockAmmoMap.getWarhead(s);
                    if (warheadEntry != null) {
                        playerIn.setActiveHand(handIn);
                        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                    }
                }
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            case 2:
                //FIRE
                if (!worldIn.isRemote) {
                    ItemStack stack = playerIn.getHeldItem(handIn);
                    String wh = NBTUtil.getString(stack.getTagCompound(), "warhead", Strings.EMPTY);
                    String pro = NBTUtil.getString(stack.getTagCompound(), "propellant", Strings.EMPTY);
                    EntityAmmoBase bullet = new EntityAmmoBase(worldIn, playerIn, wh, pro);
                    bullet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw);
                    bullet.setPosition(playerIn.posX, playerIn.posY + (double) playerIn.eyeHeight - 0.10000000149011612D, playerIn.posZ);
                    worldIn.spawnEntity(bullet);

                    NBTTagCompound tag = stack.getTagCompound();
                    NBTUtil.setInteger(tag, "state", 0);
                    NBTUtil.setString(tag, "warhead", "");
                    NBTUtil.setString(tag, "propellant", "");
                    stack.setTagCompound(tag);

                    stack.damageItem(1, playerIn);
                }
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            default:
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        state = NBTUtil.getInteger(stack.getTagCompound(), "state", 0);
        EntityPlayer player = (EntityPlayer) entityLiving;
        switch (state) {
            case 0:
                break;
            case 1:
                if (timeLeft < 220) {
                    Map.Entry<String, Warhead> warheadEntry = null;
                    for (ItemStack s : player.inventory.mainInventory) {
                        warheadEntry = FlintLockAmmoMap.getWarheadInst(s);
                        if (warheadEntry != null) {
                            s.shrink(warheadEntry.getValue().getAmmoItem().getCount());
                            break;
                        }
                    }
                    if (warheadEntry != null) {
                        NBTTagCompound tag = stack.getTagCompound();
                        tag = NBTUtil.setString(tag, "warhead", warheadEntry.getKey());
                        state++;
                        tag = NBTUtil.setInteger(tag, "state", state);
                        stack.setTagCompound(tag);
                    }
                }
                break;
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            state = NBTUtil.getInteger(stack.getTagCompound(), "state", 0);
            switch (state) {
                case 0:
                    Map.Entry<String, Propellant> propellantEntry = null;
                    for (ItemStack s : player.inventory.mainInventory) {
                        propellantEntry = FlintLockAmmoMap.getPropellantInst(s);
                        if (propellantEntry != null) {
                            s.shrink(propellantEntry.getValue().getPropellantItem().getCount());
                            break;
                        }
                    }
                    if (propellantEntry != null) {
                        NBTTagCompound tag = stack.getTagCompound();
                        tag = NBTUtil.setString(tag, "propellant", propellantEntry.getKey());
                        state++;
                        tag = NBTUtil.setInteger(tag, "state", state);
                        stack.setTagCompound(tag);
                    }
                    break;
                case 1:
                    Map.Entry<String, Warhead> warheadEntry = null;
                    for (ItemStack s : player.inventory.mainInventory) {
                        warheadEntry = FlintLockAmmoMap.getWarheadInst(s);
                        if (warheadEntry != null) {
                            s.shrink(warheadEntry.getValue().getAmmoItem().getCount());
                            break;
                        }
                    }
                    if (warheadEntry != null) {
                        NBTTagCompound tag = stack.getTagCompound();
                        tag = NBTUtil.setString(tag, "warhead", warheadEntry.getKey());
                        state++;
                        tag = NBTUtil.setInteger(tag, "state", state);
                        stack.setTagCompound(tag);
                    }
                    break;
            }
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        switch (NBTUtil.getInteger(stack.getTagCompound(), "state", 0)) {
            case 0:
                return 80;
            case 1:
                return 320;
            default:
                return 0;
        }
    }
}
