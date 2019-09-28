package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.entity.ai.*;
import cn.mcmod.tofucraft.item.ItemLoader;
import cn.mcmod.tofucraft.sound.TofuSounds;
import cn.mcmod.tofucraft.util.WorldUtils;
import cn.mcmod.tofucraft.world.village.TofuVillage;
import cn.mcmod.tofucraft.world.village.TofuVillageCollection;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityTofunian extends EntityAgeable implements INpc, IMerchant {
    private static final DataParameter<Integer> TOFUPROFESSION = EntityDataManager.createKey(EntityTofunian.class, DataSerializers.VARINT);
    private int randomTickDivider;

    private boolean isMating;
    TofuVillage village;
    private int tofunianCareerLevel;
    private int tofunianTimeUntilReset;
    private boolean isWillingToMate;
    private boolean needsInitilization;
    private int wealth;
    private java.util.UUID lastBuyingPlayer;
    @Nullable
    private EntityPlayer buyingPlayer;
    @Nullable
    private MerchantRecipeList buyingList;
    private boolean isLookingForHome;
    private final InventoryBasic villagerInventory;

    public EntityTofunian(World worldIn) {
        super(worldIn);
        this.villagerInventory = new InventoryBasic("Items", false, 8);
        this.setSize(0.45F, 1.4F);
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITofunianTradePlayer(this));
        this.tasks.addTask(2, new EntityAITofunianLookAtTradePlayer(this));
        this.tasks.addTask(3, new EntityAIMoveIndoors(this));
        this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAITofunianMate(this));
        this.tasks.addTask(8, new EntityAIUseItemOnLeftHand(this, new ItemStack(ItemLoader.tofu_food, 1, 3), SoundEvents.ENTITY_GENERIC_EAT, (p_213736_1_) -> {
            return this.getHealth() < this.getMaxHealth() && this.world.rand.nextInt(100) == 0;
        }) {

            @Override
            public boolean shouldExecute() {
                if (super.shouldExecute() && hasEnoughCraftedItem(0, 1)) {
                    for (int i = 0; i < getVillagerInventory().getSizeInventory(); ++i) {
                        ItemStack itemstack = getVillagerInventory().getStackInSlot(i);
                        if (itemstack.getItem() == ItemLoader.tofu_food) {
                            itemstack.shrink(1);
                            return true;
                        }
                    }
                    return false;

                } else {
                    return false;
                }
            }

            @Override
            public void resetTask() {
                super.resetTask();
                heal(2.0F);
            }
        });
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAITofunianInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 12) {
            this.spawnParticles(EnumParticleTypes.HEART);
        } else if (id == 13) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        } else if (id == 14) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(particleType, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 1.0D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
        }
    }

    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    protected void updateAITasks() {
        if (--this.randomTickDivider <= 0) {
            BlockPos blockpos = new BlockPos(this);

            String s = TofuVillageCollection.fileNameForProvider(this.world.provider);

            ((TofuVillageCollection) Objects.requireNonNull(this.world.getPerWorldStorage().getOrLoadData(TofuVillageCollection.class, s))).addToVillagerPositionList(blockpos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);

            this.village = ((TofuVillageCollection) Objects.requireNonNull(this.world.getPerWorldStorage().getOrLoadData(TofuVillageCollection.class, s))).getNearestVillage(blockpos, 32);

            if (this.village == null) {
                this.detachHome();
            } else {
                BlockPos blockpos1 = this.village.getCenter();
                this.setHomePosAndDistance(blockpos1, this.village.getTofuVillageRadius());

                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.village.setDefaultPlayerReputation(5);
                }
            }
        }

        if (!this.isTrading() && this.tofunianTimeUntilReset > 0) {
            --this.tofunianTimeUntilReset;

            if (this.tofunianTimeUntilReset <= 0) {
                if (this.needsInitilization) {
                    for (MerchantRecipe merchantrecipe : this.buyingList) {
                        if (merchantrecipe.isRecipeDisabled()) {
                            merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }

                    this.initTrades();
                    this.needsInitilization = false;

                    if (this.lastBuyingPlayer != null) {
                        this.world.setEntityState(this, (byte) 14);

                    }
                }

                this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        super.updateAITasks();
    }

    public void setLookingForHome() {
        this.isLookingForHome = true;
    }

    public boolean isMating() {
        return this.isMating;
    }

    public void setMating(boolean mating) {
        this.isMating = mating;
    }

    public void setCustomer(@Nullable EntityPlayer player) {
        this.buyingPlayer = player;
    }

    @Nullable
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    public boolean isTrading() {
        return this.buyingPlayer != null;
    }

    @Override
    public void useRecipe(MerchantRecipe recipe) {

        recipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound(TofuSounds.TOFUNIAN_YES, this.getSoundVolume(), this.getSoundPitch());
        int i = 3 + this.rand.nextInt(4);

        if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            this.tofunianTimeUntilReset = 40;
            this.needsInitilization = true;
            this.isWillingToMate = true;

            //update TofunianTrade
            if (this.tofunianCareerLevel != 0) {

                ++this.tofunianCareerLevel;

            } else {

                this.tofunianCareerLevel = 2;

            }


            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getUniqueID();
            } else {
                this.lastBuyingPlayer = null;
            }

            i += 5;
        }

        if (recipe.getItemToBuy().getItem() == ItemLoader.zundaruby) {
            this.wealth += recipe.getItemToBuy().getCount();
        }

        if (recipe.getRewardsExp()) {
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
        }

    }

    public void onLivingUpdate() {
        this.updateArmSwingProgress();

        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (this.rand.nextInt(600) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;

        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (!this.holdingSpawnEggOfClass(itemstack, this.getClass()) && this.isEntityAlive() && !this.isTrading() && !this.isChild() && !player.isSneaking()) {
            if (this.buyingList == null) {
                this.initTrades();
            }

            if (hand == EnumHand.MAIN_HAND) {
                player.addStat(StatList.TALKED_TO_VILLAGER);
            }

            if (!this.world.isRemote && !this.buyingList.isEmpty()) {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            } else if (this.buyingList.isEmpty()) {
                return super.processInteract(player, hand);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    protected void entityInit() {

        super.entityInit();

        this.getDataManager().register(TOFUPROFESSION, Integer.valueOf(0));
    }

    @Override

    public void writeEntityToNBT(NBTTagCompound compound) {

        super.writeEntityToNBT(compound);
        compound.setInteger("tofu_profession", this.getDataManager().get(TOFUPROFESSION));
        compound.setInteger("CareerLevel", this.tofunianCareerLevel);
        compound.setInteger("Riches", this.wealth);

        if (this.buyingList != null) {
            compound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }

        compound.setBoolean("Willing", this.isWillingToMate);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.villagerInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        compound.setTag("Inventory", nbttaglist);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(TOFUPROFESSION, compound.getInteger("tofu_profession"));
        //Load villager's CarrierLevel and make it usable as tofunian CareerLevel
        this.tofunianCareerLevel = compound.getInteger("CareerLevel");

        this.wealth = compound.getInteger("Riches");

        if (compound.hasKey("Offers", 10)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(nbttagcompound);
        }

        this.isWillingToMate = compound.getBoolean("Willing");

        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            ItemStack itemstack = new ItemStack(nbttaglist.getCompoundTagAt(i));

            if (!itemstack.isEmpty()) {
                this.villagerInventory.addItem(itemstack);
            }
        }

        this.setCanPickUpLoot(true);
        this.updateEntityAI();
    }

    @Override
    protected SoundEvent getAmbientSound() {

        return TofuSounds.TOFUNIAN_AMBIENT;
    }


    @Override
    protected SoundEvent getDeathSound() {

        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {

        return null;
    }

    protected boolean canDespawn() {
        return false;
    }


    public TofuProfession getTofuProfession() {

        return TofuProfession.get(this.getDataManager().get(TOFUPROFESSION));
    }

    public void initTofuProfession() {
        int randValRole = rand.nextInt(TofuProfession.values().length);

        if (randValRole == TofuProfession.FISHERMAN.ordinal()) {

            this.setFisher();

        } else if (randValRole == TofuProfession.GUARD.ordinal()) {

            this.setGuard();

        } else if (randValRole == TofuProfession.TOFUCOOK.ordinal()) {

            this.setTofuCook();

        } else if (randValRole == TofuProfession.TOFUSMITH.ordinal()) {

            this.setTofuSmith();

        }
    }

    public void setTofuProfession(int profession) {
        if (profession == TofuProfession.FISHERMAN.ordinal()) {

            this.setFisher();

        } else if (profession == TofuProfession.GUARD.ordinal()) {

            this.setGuard();

        } else if (profession == TofuProfession.TOFUCOOK.ordinal()) {

            this.setTofuCook();

        } else if (profession == TofuProfession.TOFUSMITH.ordinal()) {

            this.setTofuSmith();

        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {

        IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);

        initTofuProfession();

        updateEntityAI();

        initTrades();

        return data;

    }

    public void updateEntityAI() {

        if (canGuard()) {
            this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.65F, true));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityTofuChinger.class, false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVex.class, false));

            if (!this.isChild()) {
                this.tasks.addTask(6, new EntityAIUseItemOnLeftHand<>(this, new ItemStack(ItemLoader.bugle), TofuSounds.TOFUBUGLE, (p_213736_1_) -> {
                    return WorldUtils.isMorning(this.world) && this.world.rand.nextInt(350) == 0;
                }));
            }
        } else {
            this.tasks.addTask(1, new EntityAITofunianAvoidEntity<>(this, EntityTofuChinger.class, 8.0F, 0.6D, 0.6D));
            this.tasks.addTask(1, new EntityAITofunianAvoidEntity<>(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
            this.tasks.addTask(1, new EntityAITofunianAvoidEntity<>(this, AbstractIllager.class, 8.0F, 0.8D, 0.8D));
            this.tasks.addTask(1, new EntityAITofunianAvoidEntity<>(this, EntityVex.class, 8.0F, 0.6D, 0.6D));

            this.tasks.addTask(2, new EntityAIPanic(this, 0.75F));
        }


        if (canFarm() && !this.isChild()) {
            this.tasks.addTask(8, new EntityAIHarvestTofuFarmland(this, 0.6D));
        }

        if ((canFarm() || canFish()) && !this.isChild()) {
            this.tasks.addTask(8, new EntityAIMakingFood(this, 0.6D));
        }
    }

    protected void onGrowingAdult() {

        if (this.getTofuProfession() == TofuProfession.TOFUCOOK) {
            this.tasks.addTask(8, new EntityAIHarvestTofuFarmland(this, 0.6D));
        }

        if (canFarm() || canFish()) {
            this.tasks.addTask(8, new EntityAIMakingFood(this, 0.6D));
        }

        if (canGuard()) {
            if (!this.isChild()) {
                this.tasks.addTask(6, new EntityAIUseItemOnLeftHand<>(this, new ItemStack(ItemLoader.bugle), TofuSounds.TOFUBUGLE, (p_213736_1_) -> {
                    return WorldUtils.isMorning(this.world) && this.world.rand.nextInt(260) == 0;
                }));
            }
        }

        super.onGrowingAdult();
    }

    @Override
    public float getEyeHeight() {
        return this.isChild() ? 0.5F : 1.0f;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public EntityTofunian createChild(EntityAgeable ageable) {

        EntityTofunian entityvillager = new EntityTofunian(this.world);

        entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData) null);

        return entityvillager;

    }

    public boolean canFarm() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.TOFUCOOK.ordinal();

    }

    public boolean canSmish() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.TOFUSMITH.ordinal();

    }

    public boolean canFish() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.FISHERMAN.ordinal();

    }

    public boolean canGuard() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.GUARD.ordinal() && !isChild();

    }

    public void setTofuSmith() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.TOFUSMITH.ordinal()));
    }

    public void setTofuCook() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.TOFUCOOK.ordinal()));
    }

    public void setGuard() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.GUARD.ordinal()));

        this.setFightingItem();

    }

    public void setFisher() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.FISHERMAN.ordinal()));

        /*this.setFishingItem();*/

    }

    public void setFightingItem() {

        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemLoader.ishiTofuSword));


        if (this.rand.nextBoolean()) {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemLoader.metalhelmet));
        } else {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemLoader.solidhelmet));
        }
    }

    @SideOnly(Side.CLIENT)
    public void setRecipes(@Nullable MerchantRecipeList recipeList) {
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return new BlockPos(this);
    }


    @Nullable
    public MerchantRecipeList getRecipes(EntityPlayer player) {
        if (this.buyingList == null) {
            this.initTrades();
        }

        return net.minecraftforge.event.ForgeEventFactory.listTradeOffers(this, player, buyingList);
    }

    public void initTrades() {
        int j = this.tofunianCareerLevel;
        MerchantRecipeList trades = this.settingTrade(j);

        if (trades != null) {
            if (this.buyingList == null) {
                this.buyingList = trades;
            } else {
                this.buyingList = trades;
            }
        }
    }

    private MerchantRecipeList settingTrade(int level) {
        MerchantRecipeList list = new MerchantRecipeList();

        if (getTofuProfession() == TofuProfession.FISHERMAN) {


            addTradeRubyForItem(list, new ItemStack(ItemLoader.tofu_food), 22 + rand.nextInt(6));

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 8 + rand.nextInt(3)), 1);
            if (tofunianCareerLevel > 1) {
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.soymilk_drink, 1, 1 + rand.nextInt(2)), 3 + rand.nextInt(2));
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.soymilk_drink, 1, 5 + rand.nextInt(2)), 3 + rand.nextInt(2));
            }
        } else if (getTofuProfession() == TofuProfession.TOFUCOOK) {
            addTradeRubyForItem(list, new ItemStack(ItemLoader.tofu_food), 24 + rand.nextInt(6));
            addTradeRubyForItem(list, new ItemStack(ItemLoader.soybeans), 24 + rand.nextInt(6));

            if (tofunianCareerLevel > 1) {
                addTradeRubyForItem(list, new ItemStack(ItemLoader.rice), 16 + rand.nextInt(6));

                addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofu_food, 6 + rand.nextInt(4), 6), 1);
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofu_food, 6 + rand.nextInt(4), 5), 1);
            }
            if (tofunianCareerLevel > 2) {
                if (this.rand.nextInt(2) == 0) {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 5 + rand.nextInt(3), 15), 1);
                } else {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 6 + rand.nextInt(4), 14), 1);
                }
            }

            if (tofunianCareerLevel > 3) {
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaMochi, 4 + rand.nextInt(4)), 1);

                addTradeForSingleRuby(list, new ItemStack(ItemLoader.material, 2 + rand.nextInt(2), 17), 4);
            }

            if (tofunianCareerLevel > 4) {
                if (this.rand.nextInt(2) == 0) {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.material, 1 + rand.nextInt(2), 9), 5);
                }
            }
        } else if (getTofuProfession() == TofuProfession.TOFUSMITH) {
            addTradeRubyForItem(list, new ItemStack(ItemLoader.tofu_food, 1, 2), 22 + rand.nextInt(6));

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuSword, 1), 4 + rand.nextInt(3));
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuShovel, 1), 5 + rand.nextInt(3));
            if (tofunianCareerLevel > 1) {
                addTradeRubyForItem(list, new ItemStack(ItemLoader.tofu_material, 1), 5 + rand.nextInt(4));
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalhelmet, 1), 4 + rand.nextInt(3));
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuPickaxe, 1), 6 + rand.nextInt(6));
            }

            if (tofunianCareerLevel > 2) {
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalleggins, 1), 6 + rand.nextInt(4));
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalchestplate, 1), 7 + rand.nextInt(4));
            }

            if (tofunianCareerLevel > 3) {
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaBow, 1), 2 + rand.nextInt(2));
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaArrow, 8 + rand.nextInt(4)), 1 + rand.nextInt(1));
            }

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofustick, 2), 1 + rand.nextInt(2));
        }

        return list;
    }

    public void addTradeRubyForItem(MerchantRecipeList list, ItemStack buy, int cost) {

        ItemStack stack1 = buy.copy();

        stack1.setCount(cost);

        list.add(new MerchantRecipe(stack1, new ItemStack(ItemLoader.zundaruby, 1)));
    }

    public void addTradeForSingleRuby(MerchantRecipeList list, ItemStack sell, int cost) {

        ItemStack stack1 = sell.copy();

        list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), stack1));
    }

    public void addEnchantTradeForSingleRuby(MerchantRecipeList list, ItemStack sell, int cost) {

        ItemStack stack1 = sell.copy();

        ItemStack enchantStack = EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(stack1.getItem(), 1, stack1.getMetadata()), 5 + this.rand.nextInt(15), false);

        list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), enchantStack));
    }

    @Override
    public ITextComponent getDisplayName() {

        String s1 = null;

        switch (this.getTofuProfession()) {
            case GUARD:
                s1 = "guard";
                break;
            case TOFUCOOK:
                s1 = "tofucrafter";
                break;
            case FISHERMAN:
                s1 = "fisherman";
                break;
            case TOFUSMITH:
                s1 = "tofusmith";
                break;
        }


        ITextComponent itextcomponent = new TextComponentTranslation("entity.Tofunian." + s1, new Object[0]);
        itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
        itextcomponent.getStyle().setInsertion(this.getCachedUniqueIdString());


        return itextcomponent;
    }

    public boolean getIsWillingToMate(boolean updateFirst) {
        if (!this.isWillingToMate && updateFirst && this.hasEnoughFoodToBreed()) {
            boolean flag = false;

            for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
                ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

                if (!itemstack.isEmpty()) {
                    if (itemstack.getItem() == Items.BREAD && itemstack.getCount() >= 3) {
                        flag = true;
                        this.getVillagerInventory().decrStackSize(i, 3);
                    } else if ((itemstack.getItem() == ItemLoader.soybeans || itemstack.getItem() == ItemLoader.tofu_food || itemstack.getItem() == ItemLoader.foodset || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT) && itemstack.getCount() >= 12) {
                        flag = true;
                        this.getVillagerInventory().decrStackSize(i, 12);
                    }
                }

                if (flag) {
                    this.world.setEntityState(this, (byte) 18);
                    this.isWillingToMate = true;
                    break;
                }
            }
        }

        return this.isWillingToMate;
    }

    public boolean hasEnoughFoodToBreed() {
        return this.hasEnoughItems(1);
    }

    /**
     * Used by {@link net.minecraft.entity.ai.EntityAIVillagerInteract EntityAIVillagerInteract} to check if the
     * villager can give some items from an inventory to another villager.
     */
    public boolean canAbondonItems() {
        return this.hasEnoughItems(2);
    }

    public boolean wantsMoreFood() {
        boolean flag = this.getTofuProfession() == TofuProfession.TOFUCOOK;

        if (flag) {
            return !this.hasEnoughItems(5);
        } else {
            return !this.hasEnoughItems(1);
        }
    }

    public void setIsWillingToMate(boolean isWillingToMate) {
        this.isWillingToMate = isWillingToMate;
    }

    private boolean hasEnoughItems(int multiplier) {
        @SuppressWarnings("deprecation")
        boolean flag = this.getTofuProfession() == TofuProfession.TOFUCOOK;

        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.BREAD && itemstack.getCount() >= 3 * multiplier || itemstack.getItem() == Items.POTATO && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.CARROT && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.BEETROOT && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == ItemLoader.foodset && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == ItemLoader.soybeans && itemstack.getCount() >= 12 * multiplier) {
                    return true;
                }

                if (itemstack.getItem() == ItemLoader.tofu_food && itemstack.getCount() >= 14 * multiplier) {
                    return true;
                }

                if (flag && itemstack.getItem() == Items.WHEAT && itemstack.getCount() >= 9 * multiplier || flag && itemstack.getItem() == ItemLoader.rice && itemstack.getCount() >= 9 * multiplier) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasEnoughCraftedItem(int multiplier, int needCost) {
        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == ItemLoader.tofu_food && itemstack.getCount() >= 12 * multiplier + needCost) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();

        if (this.canTofunianPickupItem(itemstack)) {
            ItemStack itemstack1 = this.getVillagerInventory().addItem(itemstack);

            if (itemstack1.isEmpty()) {
                itemEntity.setDead();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }
    }

    public InventoryBasic getVillagerInventory() {
        return villagerInventory;
    }

    private boolean canTofunianPickupItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item == ItemLoader.soybeans || item == ItemLoader.rice || item == ItemLoader.tofu_food && itemStack.getMetadata() == 3;
    }

    public boolean isFarmItemInInventory() {
        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty() && (itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.BEETROOT_SEEDS || itemstack.getItem() == ItemLoader.soybeans)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getCreatureAttribute() == TofuEntityRegister.tofunian) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    public void verifySellingItem(ItemStack stack) {
        if (!this.world.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            if (!stack.isEmpty()) {
                this.playSound(TofuSounds.TOFUNIAN_YES, this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    public void onDeath(DamageSource cause) {
        if (this.village != null) {
            Entity entity = cause.getTrueSource();

            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.village.modifyPlayerReputation(entity.getUniqueID(), -2);
                } else if (entity instanceof IMob) {
                    this.village.endMatingSeason();
                }
            } else {
                EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0D);

                if (entityplayer != null) {
                    this.village.endMatingSeason();
                }
            }
        }

        super.onDeath(cause);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return TofuEntityRegister.tofunian;
    }

    public enum TofuProfession {

        GUARD,
        FISHERMAN,
        TOFUCOOK,
        TOFUSMITH;


        private static final Map<Integer, TofuProfession> lookup = new HashMap<>();

        static {
            for (TofuProfession e : EnumSet.allOf(TofuProfession.class)) {
                lookup.put(e.ordinal(), e);
            }
        }

        public static TofuProfession get(int intValue) {
            return lookup.get(intValue);
        }

    }
}
