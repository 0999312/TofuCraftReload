package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.advancements.TofuAdvancements;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.block.fluid.ZundaSoyMilkFluid;
import cn.mcmod.tofucraft.util.TofuLootTables;
import cn.mcmod.tofucraft.world.biome.BiomeZundaTofuPlains;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public class EntityTofuCow extends EntityCow {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTofuCow.class, DataSerializers.VARINT);
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
            if(this.getVariant() == 1) {
                FluidStack fluidStack = FluidRegistry.getFluidStack(ZundaSoyMilkFluid.name, Fluid.BUCKET_VOLUME);

                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);

                TofuAdvancements.grantAdvancement(player, "flesh_soymilk");
                if (itemstack.isEmpty()) {

                    player.setHeldItem(hand, FluidUtil.getFilledBucket(fluidStack));
                } else if (!player.inventory.addItemStackToInventory(FluidUtil.getFilledBucket(fluidStack))) {
                    player.dropItem(FluidUtil.getFilledBucket(fluidStack), false);
                }

            }else {
                FluidStack fluidStack = FluidRegistry.getFluidStack(SoyMilkFluid.name, Fluid.BUCKET_VOLUME);

                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);

                TofuAdvancements.grantAdvancement(player, "flesh_soymilk");
                if (itemstack.isEmpty()) {

                    player.setHeldItem(hand, FluidUtil.getFilledBucket(fluidStack));
                } else if (!player.inventory.addItemStackToInventory(FluidUtil.getFilledBucket(fluidStack))) {
                    player.dropItem(FluidUtil.getFilledBucket(fluidStack), false);
                }
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome instanceof BiomeZundaTofuPlains)
        {
            this.setVariant(1);
        }else {
            this.setVariant(0);
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public int getVariant()
    {
        return MathHelper.clamp(((Integer)this.dataManager.get(VARIANT)).intValue(), 0, 1);
    }

    public void setVariant(int p_191997_1_)
    {
        this.dataManager.set(VARIANT, Integer.valueOf(p_191997_1_));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VARIANT, Integer.valueOf(0));
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }


    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofucow;
    }


    public EntityTofuCow createChild(EntityAgeable ageable) {
        Biome biome = this.world.getBiome(new BlockPos(this));

        EntityTofuCow tofuCow = new EntityTofuCow(this.world);

       if(this.getVariant() == 1){
           if(this.rand.nextInt(2)==0){
               tofuCow.setVariant(0);
           }else {
               tofuCow.setVariant(1);
           }
        }
        return tofuCow;
    }

}
