package cn.mcmod.tofucraft.item;

import cn.mcmod.tofucraft.TofuMain;
import cn.mcmod.tofucraft.entity.projectile.EntityFukumame;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemFukumame extends Item {
    public ItemFukumame() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setUnlocalizedName(TofuMain.MODID + "." + "fukumame");
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new DispenserBehaviorFukumame());
        this.addPropertyOverride(new ResourceLocation("empty"), new IItemPropertyGetter() {

            @Override
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return stack.getItemDamage() > stack.getMaxDamage() ? 1.0f : 0.0f;
            }

        });
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            stack.damageItem(1, playerIn);
        }

        worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 4);

        if (!worldIn.isRemote) {
            for (int i = 0; i < 6; i++) {
                EntityFukumame entityfukumame = new EntityFukumame(worldIn, playerIn);

                float d0 = (worldIn.rand.nextFloat() * 12.0F) - 6.0F;
                applyEffect(entityfukumame, stack);

                entityfukumame.shoot(playerIn, playerIn.rotationPitch,playerIn.rotationYaw + d0,0.0F, 1.5f, 1.0F);
                worldIn.spawnEntity(entityfukumame);
            }
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public static void applyEffect(EntityFukumame fukumame, ItemStack stack) {
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

        if (k > 0) {
            fukumame.setDamage(fukumame.getDamage() + (double) k * 0.25D + 0.25D);
        }

//        int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
//
//        if (l > 0)
//        {
//            fukumame.setKnockbackStrength(l);
//        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
            fukumame.setFire(100);
        }

    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.type == EnumEnchantmentType.BREAKABLE) {
            return true;
        }

        if (enchantment == Enchantments.POWER) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private class DispenserBehaviorFukumame extends BehaviorDefaultDispenseItem {
        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            if (stack.getItemDamage() >= stack.getMaxDamage()) return stack;

            EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING); // getFacing
            World world = source.getWorld();
            double d0 = source.getX() + (double) ((float) enumfacing.getFrontOffsetX() * 1.125F);
            double d1 = source.getY() + (double) ((float) enumfacing.getFrontOffsetY() * 1.125F);
            double d2 = source.getZ() + (double) ((float) enumfacing.getFrontOffsetZ() * 1.125F);

            for (int i = 0; i < 8; i++) {
                EntityFukumame fukumame = new EntityFukumame(world, d0, d1, d2);
                fukumame.shoot(enumfacing.getFrontOffsetX(), (enumfacing.getFrontOffsetY()), enumfacing.getFrontOffsetZ(), this.getProjectileVelocity(), this.getProjectileInaccuracy());

                applyEffect(fukumame, stack);

                if (!world.isRemote) {
                    world.spawnEntity(fukumame);
                }
            }

            if (stack.isItemStackDamageable()) {
                stack.getItem();
                stack.attemptDamageItem(1, Item.itemRand, null);
            }
            source.getWorld().playEvent(1000, source.getBlockPos(), 0);
            return stack;
        }

        protected float getProjectileInaccuracy() {
            return 6.0F;
        }

        protected float getProjectileVelocity() {
            return 1.1F;
        }
    }
}
