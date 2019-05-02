package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.entity.ai.EntityAIHarvestTofuFarmland;
import cn.mcmod.tofucraft.entity.ai.EntityAITofunianAvoidEntity;
import cn.mcmod.tofucraft.entity.ai.EntityAITofunianInteract;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public class EntityTofunian extends EntityVillager {
    private static final DataParameter<Integer> TOFUPROFESSION = EntityDataManager.createKey(EntityTofunian.class, DataSerializers.VARINT);

    private int tofunianCareerLevel;
    private int tofunianTimeUntilReset;
    private boolean isWillingToMate;
    public EntityTofunian(World worldIn) {
        super(worldIn, 5);
        this.setSize(0.45F, 1.4F);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityTofuChinger.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, AbstractIllager.class, 8.0F, 0.8D, 0.8D));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAITradePlayer(this));
        this.tasks.addTask(2, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(3, new EntityAIMoveIndoors(this));
        this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIVillagerMate(this));
        this.tasks.addTask(8, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAITofunianInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this,true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void updateAITasks() {
        if (!this.isTrading() && this.tofunianTimeUntilReset > 0) {
            --this.tofunianTimeUntilReset;

            if (this.tofunianTimeUntilReset <= 0) {
                //update TofunianTrade
                this.initTrades();
            }
        }

        super.updateAITasks();
    }

    @Override
    public void useRecipe(MerchantRecipe recipe)
    {
        super.useRecipe(recipe);

        if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            this.tofunianTimeUntilReset = 40;
        }

    }

    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();

        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (this.rand.nextInt(600) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
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

        compound.setBoolean("Willing", this.isWillingToMate);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(TOFUPROFESSION, compound.getInteger("tofu_profession"));
        //Load villager's CarrierLevel and make it usable as tofunian CareerLevel
        this.tofunianCareerLevel = compound.getInteger("CareerLevel");

        this.isWillingToMate = compound.getBoolean("Willing");
        this.updateEntityAI();
    }

    @Override
    protected SoundEvent getAmbientSound() {

        return null;
    }


    @Override
    protected SoundEvent getDeathSound() {

        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {

        return null;
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

        }else if (randValRole == TofuProfession.TOFUCOOK.ordinal()) {

            this.setTofuCook();

        }else if (randValRole == TofuProfession.TOFUSMISH.ordinal()) {

            this.setTofuSmith();

        }
    }

    public void setTofuProfession(int profession) {
        if (profession == TofuProfession.FISHERMAN.ordinal()) {

            this.setFisher();

        } else if (profession == TofuProfession.GUARD.ordinal()) {

            this.setGuard();

        }else if (profession == TofuProfession.TOFUCOOK.ordinal()) {

            this.setTofuCook();

        }else if (profession == TofuProfession.TOFUSMISH.ordinal()) {

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

        this.setProfession(5);

        return data;

    }

    public void updateEntityAI() {

        if(canGuard()){
            this.tasks.addTask(1, new EntityAIAttackMelee(this,0.65F,true));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityTofuChinger.class,false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityZombie.class,false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class,false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVex.class,false));
        }

        if(canFarm()&&!this.isChild()){
            this.tasks.addTask(8, new EntityAIHarvestTofuFarmland(this, 0.6D));
        }
    }

    protected void onGrowingAdult()
    {

        if (this.getTofuProfession() == TofuProfession.TOFUCOOK)
        {
            this.tasks.addTask(8, new EntityAIHarvestTofuFarmland(this, 0.6D));
        }

        super.onGrowingAdult();
    }

    @Override
    public float getEyeHeight()
    {
        return this.isChild() ? 0.5F :1.0f;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public EntityVillager createChild(EntityAgeable ageable) {

        EntityTofunian entityvillager = new EntityTofunian(this.world);

        entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null);

        return entityvillager;

    }

    public boolean canFarm() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.TOFUCOOK.ordinal();

    }

    public boolean canSmish() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.TOFUSMISH.ordinal();

    }

    public boolean canFish() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.FISHERMAN.ordinal();

    }

    public boolean canGuard() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.GUARD.ordinal() && !isChild();

    }

    public void setTofuSmith() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.TOFUSMISH.ordinal()));
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


        if(this.rand.nextBoolean()){
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemLoader.metalhelmet));
        }else {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemLoader.solidhelmet));
        }
    }

    private static final Field _buyingList = ReflectionHelper.findField(EntityVillager.class, "field_70963_i", "buyingList");

    public void initTrades() {

        MerchantRecipeList list = new MerchantRecipeList();


        if(this.tofunianCareerLevel != 0)
        {
            ++this.tofunianCareerLevel;
        } else
        {
            this.tofunianCareerLevel = 1;
        }

        if (getTofuProfession() == TofuProfession.FISHERMAN) {


            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food),22+ rand.nextInt(6));

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 8 + rand.nextInt(3)),1);
        }else if (getTofuProfession() == TofuProfession.TOFUCOOK) {
            addTradeRubyForItem(list, new ItemStack(ItemLoader.tofu_food), 24 + rand.nextInt(6));
            addTradeRubyForItem(list, new ItemStack(ItemLoader.soybeans), 24 + rand.nextInt(6));

            if(tofunianCareerLevel > 1) {
                addTradeRubyForItem(list, new ItemStack(ItemLoader.rice), 16 + rand.nextInt(6));

                addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofu_food, 6 + rand.nextInt(4), 6), 1);
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofu_food, 6 + rand.nextInt(4), 5), 1);
            }
            if (tofunianCareerLevel > 2){
                if(this.rand.nextInt(2)==0) {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 5 + rand.nextInt(3), 15), 1);
                }else {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 6 + rand.nextInt(4), 14), 1);
                }
            }

            if (tofunianCareerLevel > 3){
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaMochi, 4 + rand.nextInt(4)), 1);

                addTradeForSingleRuby(list, new ItemStack(ItemLoader.material, 2 + rand.nextInt(2), 17), 4);
            }

            if (tofunianCareerLevel > 4){
                if(this.rand.nextInt(2)==0) {
                    addTradeForSingleRuby(list, new ItemStack(ItemLoader.material, 1 + rand.nextInt(2), 9), 5);
                }
            }
        } else if (getTofuProfession() == TofuProfession.TOFUSMISH) {
            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food,1,2),22+ rand.nextInt(6));

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuSword, 1),4+ rand.nextInt(3));
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuShovel, 1),5+ rand.nextInt(3));
            if(tofunianCareerLevel > 1) {
                addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_material,1),5+ rand.nextInt(4));
                addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalhelmet, 1),4+ rand.nextInt(3));
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuPickaxe, 1),6+ rand.nextInt(6));
            }

            if(tofunianCareerLevel > 2) {
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalleggins, 1),6+ rand.nextInt(4));
                addEnchantTradeForSingleRuby(list, new ItemStack(ItemLoader.metalchestplate, 1),7+ rand.nextInt(4));
            }

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofustick, 2),1+ rand.nextInt(2));
        }

        try {

            _buyingList.set(this, list);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void addTradeRubyForItem(MerchantRecipeList list, ItemStack buy,int cost) {

        double tofuWorth = 1;


        List<Double> listTradeCosts = new ArrayList<>();

        ItemStack stack1 = buy.copy();


        stack1.setCount(cost);

        list.add(new MerchantRecipe(stack1,new ItemStack(ItemLoader.zundaruby, 1)));
    }

    public void addTradeForSingleRuby(MerchantRecipeList list, ItemStack sell,int cost) {

        double tofuWorth = 1;


        List<Double> listTradeCosts = new ArrayList<>();

        ItemStack stack1 = sell.copy();


        list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), stack1));
    }

    public void addEnchantTradeForSingleRuby(MerchantRecipeList list, ItemStack sell,int cost) {

        double tofuWorth = 1;


        List<Double> listTradeCosts = new ArrayList<>();

        ItemStack stack1 = sell.copy();

        ItemStack enchantStack = EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(stack1.getItem(), 1, stack1.getMetadata()), 5 + this.rand.nextInt(15), false);

        list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), enchantStack));
    }

    @Override
    public ITextComponent getDisplayName()
    {

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
            case TOFUSMISH:
                s1 = "tofusmish";
                break;
        }


        ITextComponent itextcomponent = new TextComponentTranslation("entity.Tofunian." + s1, new Object[0]);
        itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
        itextcomponent.getStyle().setInsertion(this.getCachedUniqueIdString());


        return itextcomponent;
    }

    public boolean getIsWillingToMate(boolean updateFirst)
    {
        if (!this.isWillingToMate && updateFirst && this.hasEnoughFoodToBreed())
        {
            boolean flag = false;

            for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.getItem() == Items.BREAD && itemstack.getCount() >= 3)
                    {
                        flag = true;
                        this.getVillagerInventory().decrStackSize(i, 3);
                    }
                    else if ((itemstack.getItem() == ItemLoader.soybeans ||itemstack.getItem() == ItemLoader.foodset ||itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT) && itemstack.getCount() >= 12)
                    {
                        flag = true;
                        this.getVillagerInventory().decrStackSize(i, 12);
                    }
                }

                if (flag)
                {
                    this.world.setEntityState(this, (byte)18);
                    this.isWillingToMate = true;
                    break;
                }
            }
        }

        return this.isWillingToMate;
    }

    public void setIsWillingToMate(boolean isWillingToMate)
    {
        this.isWillingToMate = isWillingToMate;
    }

    public boolean wantsMoreFood()
    {
        return !this.hasEnoughItems(1);
    }

    private boolean hasEnoughItems(int multiplier)
    {
        boolean flag = this.getProfession() == 0;

        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() == Items.BREAD && itemstack.getCount() >= 3 * multiplier || itemstack.getItem() == Items.POTATO && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.CARROT && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.BEETROOT && itemstack.getCount() >= 12 * multiplier|| itemstack.getItem() == ItemLoader.foodset && itemstack.getCount() >= 12 * multiplier|| itemstack.getItem() == ItemLoader.soybeans && itemstack.getCount() >= 12 * multiplier)
                {
                    return true;
                }

                if (flag && itemstack.getItem() == Items.WHEAT && itemstack.getCount() >= 9 * multiplier ||flag && itemstack.getItem() == ItemLoader.rice && itemstack.getCount() >= 9 * multiplier)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        super.updateEquipmentIfNeeded(itemEntity);
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();

        if (this.canTofunianPickupItem(item))
        {
            ItemStack itemstack1 = this.getVillagerInventory().addItem(itemstack);

            if (itemstack1.isEmpty())
            {
                itemEntity.setDead();
            }
            else
            {
                itemstack.setCount(itemstack1.getCount());
            }
        }
    }
    private boolean canTofunianPickupItem(Item itemIn)
    {
        return itemIn == ItemLoader.soybeans || itemIn == ItemLoader.rice;
    }

    public boolean isFarmItemInInventory()
    {
        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty() && (itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.BEETROOT_SEEDS ||itemstack.getItem() == ItemLoader.soybeans))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn)
    {
        if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == TofuEntityRegister.tofunian)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }

    @Override

    public void playSound(SoundEvent soundIn, float volume, float pitch) {

        //cancel villager trade sounds
        if (soundIn == SoundEvents.ENTITY_VILLAGER_YES || soundIn == SoundEvents.ENTITY_VILLAGER_NO) {

            return;

        }

        super.playSound(soundIn, volume, pitch);

    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return TofuEntityRegister.tofunian;
    }

    public enum TofuProfession {

        GUARD,
        FISHERMAN,
        TOFUCOOK,
        TOFUSMISH;




        private static final Map<Integer, TofuProfession> lookup = new HashMap<>();

        static { for(TofuProfession e : EnumSet.allOf(TofuProfession.class)) { lookup.put(e.ordinal(), e); } }

        public static TofuProfession get(int intValue) { return lookup.get(intValue); }

    }
}
