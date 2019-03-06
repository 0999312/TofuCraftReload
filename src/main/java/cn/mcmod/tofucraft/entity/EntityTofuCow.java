package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.advancements.TofuAdvancements;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.util.TofuLootTables;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public class EntityTofuCow extends EntityCow {
    public EntityTofuCow(World worldIn) {
        super(worldIn);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.BUCKET && !player.capabilities.isCreativeMode && !this.isChild()) {
            FluidStack fluidStack = FluidRegistry.getFluidStack(SoyMilkFluid.name, Fluid.BUCKET_VOLUME);

            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemstack.shrink(1);

            TofuAdvancements.grantAdvancement(player,"flesh_soymilk");
            if (itemstack.isEmpty()) {

                player.setHeldItem(hand, FluidUtil.getFilledBucket(fluidStack));
            } else if (!player.inventory.addItemStackToInventory(FluidUtil.getFilledBucket(fluidStack))) {
                player.dropItem(FluidUtil.getFilledBucket(fluidStack), false);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofucow;
    }


    public EntityTofuCow createChild(EntityAgeable ageable) {
        return new EntityTofuCow(this.world);
    }

}
