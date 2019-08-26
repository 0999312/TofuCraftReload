package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.api.recipes.FlintLockAmmoMap;
import cn.mcmod.tofucraft.entity.projectile.ammo.EntityAmmoBase;
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

public class ItemFlintlock extends Item {
    private ItemStack ammo;

    public ItemFlintlock() {
        super();
        setUnlocalizedName(TofuMain.MODID + "." + "fulintlock");
        setMaxStackSize(1);
        setMaxDamage(200);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        boolean loaded = false;
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            loaded = tag.getBoolean("loaded");
            ammo = new ItemStack(tag.getCompoundTag("ammo"));
        }

        if (loaded) {
            if (!worldIn.isRemote) {
                stack.damageItem(1, playerIn);
                EntityAmmoBase base = FlintLockAmmoMap.findAmmo(ammo).getValue().create(worldIn, playerIn);
                base.setPosition(playerIn.posX, playerIn.posY + (double) playerIn.getEyeHeight() - 0.10000000149011612D, playerIn.posZ);
                base.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, base.getVelocity(), base.getInaccuracy());
                worldIn.spawnEntity(base);
                tag.setBoolean("loaded", false);
                tag.setTag("ammo", new NBTTagCompound());
            }
        } else {
            for (ItemStack s : playerIn.inventory.mainInventory) {
                if (FlintLockAmmoMap.findAmmo(s) != null) {
                    playerIn.setActiveHand(handIn);
                    return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                }
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            for (ItemStack s : player.inventory.mainInventory) {
                if (FlintLockAmmoMap.findAmmo(s) != null && !worldIn.isRemote) {
                    ammo = FlintLockAmmoMap.findAmmo(s).getKey();
                    s.shrink(ammo.getCount());
                    if (stack.getTagCompound() == null)
                        stack.setTagCompound(new NBTTagCompound());
                    stack.getTagCompound().setTag("ammo", ammo.writeToNBT(new NBTTagCompound()));
                    stack.getTagCompound().setBoolean("loaded", true);
                }
            }
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 80;
    }
}
