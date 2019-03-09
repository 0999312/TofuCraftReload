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
        this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIVillagerInteract(this));
        this.tasks.addTask(10, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
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

    public void setTofuProfession() {

        int randValRole = rand.nextInt(TofuProfession.values().length);

        if (randValRole == TofuProfession.FISHERMAN.ordinal()) {

            this.setFisher();

        } else if (randValRole == TofuProfession.GUARD.ordinal()) {

            this.setGuard();

        }else if (randValRole == TofuProfession.TOFUCRAFTER.ordinal()) {

            this.setTofuCrafter();

        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {

        this.setHomePosAndDistance(this.getPosition(), MAX_HOME_DISTANCE);


        IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);

        setTofuProfession();

        updateEntityAI();


        initTrades();

        this.setProfession(5);

        return data;

    }

    public void updateEntityAI() {

        if(canGuard()){
            this.tasks.addTask(1, new EntityAIAttackMelee(this,0.82F,true));
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

    public boolean canFish() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.FISHERMAN.ordinal();

    }

    public boolean canGuard() {

        return this.getDataManager().get(TOFUPROFESSION) == TofuProfession.GUARD.ordinal() && !isChild();

    }

    public void setTofuCrafter() {

        this.getDataManager().set(TOFUPROFESSION, Integer.valueOf(TofuProfession.TOFUCRAFTER.ordinal()));


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

    }

    private static final Field _buyingList = ReflectionHelper.findField(EntityVillager.class, "field_70963_i", "buyingList");

    public void initTrades() {

        MerchantRecipeList list = new MerchantRecipeList();


        if (getTofuProfession() == TofuProfession.FISHERMAN) {
            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food),26);
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.foodset, 8 + rand.nextInt(3)),1,false);
        }else if (getTofuProfession() == TofuProfession.TOFUCRAFTER) {
            addTradeRubyForItem(list,new ItemStack(ItemLoader.tofu_food),24+ rand.nextInt(6));
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.metalTofuSword, 1),4,false);
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.zundaMochi, 8 + rand.nextInt(3)),1,false);
            addTradeForSingleRuby(list, new ItemStack(ItemLoader.tofustick, 2),1,true);
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

        int i = rand.nextInt(4);


        stack1.setCount(cost + i);

        list.add(new MerchantRecipe(stack1,new ItemStack(ItemLoader.zundaruby, 1)));


    }

    public void addTradeForSingleRuby(MerchantRecipeList list, ItemStack sell,int cost,boolean randomCost) {

        double tofuWorth = 1;


        List<Double> listTradeCosts = new ArrayList<>();

        ItemStack stack1 = sell.copy();

        int i = rand.nextInt(3);

        if(randomCost) {

            list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost + i), stack1));
        }else {
            list.add(new MerchantRecipe(new ItemStack(ItemLoader.zundaruby, cost), stack1));
        }

    }

    @Override
    public ITextComponent getDisplayName()
    {

        String s1 = null;

        switch (this.getTofuProfession()) {
            case GUARD:
                s1 = "guard";
                break;
            case TOFUCRAFTER:
                s1 = "tofucrafter";
                break;
            case FISHERMAN:
                s1 = "fisherman";
                break;
        }


        ITextComponent itextcomponent = new TextComponentTranslation("entity.Tofunian." + s1, new Object[0]);
        itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
        itextcomponent.getStyle().setInsertion(this.getCachedUniqueIdString());


        return itextcomponent;
    }

    public enum TofuProfession {

        GUARD,
        FISHERMAN,
        TOFUCRAFTER;



        private static final Map<Integer, TofuProfession> lookup = new HashMap<>();

        static { for(TofuProfession e : EnumSet.allOf(TofuProfession.class)) { lookup.put(e.ordinal(), e); } }

        public static TofuProfession get(int intValue) { return lookup.get(intValue); }

    }
}
