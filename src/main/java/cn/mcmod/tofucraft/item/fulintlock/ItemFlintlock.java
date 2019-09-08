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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import javax.annotation.Nullable;

public class ItemFlintlock extends Item {


    public ItemFlintlock() {
        super();
        setUnlocalizedName(TofuMain.MODID + "." + "fulintlock");
        setMaxStackSize(1);
        setMaxDamage(200);
//        this.addPropertyOverride(new ResourceLocation("state"), new IItemPropertyGetter() {
//            @SideOnly(Side.CLIENT)
//            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
//             switch (NBTUtil.getInteger(stack.getTagCompound(), "state", 0)) {
//                 case 0:
//                	 return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
//                 case 1:
//                	 return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
//                 case 2:
//                	 return 0F;
//                 }
//			return 0F;
//            }
//        });
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

}
