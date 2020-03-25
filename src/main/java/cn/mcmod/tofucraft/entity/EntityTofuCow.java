package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.advancements.TofuAdvancements;
import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.fluid.SoyMilkFluid;
import cn.mcmod.tofucraft.block.fluid.ZundaSoyMilkFluid;
import cn.mcmod.tofucraft.util.TofuLootTables;
import cn.mcmod.tofucraft.world.biome.BiomeZundaTofuPlains;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class EntityTofuCow extends EntityCow {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityTofuCow.class, DataSerializers.VARINT);

    public EntityTofuCow(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Item.getItemFromBlock(BlockLoader.yubaGrass), false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }


    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(itemstack, 1));

        if (handler instanceof FluidBucketWrapper && !player.capabilities.isCreativeMode && !this.isChild()) {
            FluidBucketWrapper fluidBucketWrapper = ((FluidBucketWrapper) handler);
            if (fluidBucketWrapper.getFluid() == null) {
                if (this.getVariant() == 1) {
                    FluidStack fluidStack = FluidRegistry.getFluidStack(ZundaSoyMilkFluid.name, Fluid.BUCKET_VOLUME);

                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    itemstack.shrink(1);

                    TofuAdvancements.grantAdvancement(player, "flesh_soymilk");
                    fluidBucketWrapper.fill(fluidStack, true);
                    if (itemstack.isEmpty()) {

                        player.setHeldItem(hand, fluidBucketWrapper.getContainer());
                    } else if (!player.inventory.addItemStackToInventory(fluidBucketWrapper.getContainer())) {
                        player.dropItem(fluidBucketWrapper.getContainer(), false);
                    }
                    return true;
                } else {
                    FluidStack fluidStack = FluidRegistry.getFluidStack(SoyMilkFluid.name, Fluid.BUCKET_VOLUME);

                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    itemstack.shrink(1);

                    TofuAdvancements.grantAdvancement(player, "flesh_soymilk");
                    fluidBucketWrapper.fill(fluidStack, true);
                    if (itemstack.isEmpty()) {
                        player.setHeldItem(hand, fluidBucketWrapper.getContainer());
                    } else if (!player.inventory.addItemStackToInventory(fluidBucketWrapper.getContainer())) {
                        player.dropItem(fluidBucketWrapper.getContainer(), false);
                    }
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome instanceof BiomeZundaTofuPlains) {
            this.setVariant(1);
        } else {
            this.setVariant(0);
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public int getVariant() {
        return MathHelper.clamp(((Integer) this.dataManager.get(VARIANT)).intValue(), 0, 1);
    }

    public void setVariant(int p_191997_1_) {
        this.dataManager.set(VARIANT, Integer.valueOf(p_191997_1_));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, Integer.valueOf(0));
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }


    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return TofuLootTables.tofucow;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.WHEAT || stack.getItem() == Item.getItemFromBlock(BlockLoader.yubaGrass);
    }

    public EntityTofuCow createChild(EntityAgeable ageable) {
        EntityTofuCow tofuCow = new EntityTofuCow(this.world);

        if (this.getVariant() == 1) {
            if (this.rand.nextInt(2) == 0) {
                tofuCow.setVariant(0);
            } else {
                tofuCow.setVariant(1);
            }
        }
        return tofuCow;
    }

    public float getBlockPathWeight(BlockPos pos) {
        return (this.world.getBlockState(pos.down()).getBlock() == BlockLoader.tofuTerrain || this.world.getBlockState(pos.down()).getBlock() == BlockLoader.zundatofuTerrain) ? 10.0F : this.world.getLightBrightness(pos) - 0.5F;
    }
}
