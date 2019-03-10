package cn.mcmod.tofucraft.entity;

import cn.mcmod.tofucraft.entity.ai.EntityAITofunianAvoidEntity;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
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
    public static int MAX_HOME_DISTANCE = 128;
    private static final DataParameter<Integer> TOFUPROFESSION = EntityDataManager.createKey(EntityTofunian.class, DataSerializers.VARINT);
    public EntityTofunian(World worldIn) {
        super(worldIn, 5);
        this.setSize(0.45F, 1.4F);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
        this.tasks.addTask(2, new EntityAITofunianAvoidEntity<>(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
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
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
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
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(TOFUPROFESSION, compound.getInteger("tofu_profession"));
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

        this.setHomePosAndDistance(this.getPosition(), MAX_HOME_DISTANCE);


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
            this.targetTasks.addTask(0, new EntityAIHurtByTarget(this,true));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this,EntityZombie.class,false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class,false));
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVex.class,false));
        }
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


        if (getTofuProfession() == TofuProfession.FISHERMAN) {
            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food),24+ rand.nextInt(6));
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 8 + rand.nextInt(3)),1);
        }else if (getTofuProfession() == TofuProfession.TOFUCOOK) {
            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food),24+ rand.nextInt(6));
            addTradeRubyForItem(list,new ItemStack(ItemLoader.soybeans),28+ rand.nextInt(6));

            addTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaMochi, 6 + rand.nextInt(3)),1);
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 8 + rand.nextInt(5),14),1);

        } else if (getTofuProfession() == TofuProfession.TOFUSMISH) {
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuSword, 1),4+ rand.nextInt(3));
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuPickaxe, 1),5+ rand.nextInt(3));
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

        int i = rand.nextInt(3);

        list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), stack1));


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
