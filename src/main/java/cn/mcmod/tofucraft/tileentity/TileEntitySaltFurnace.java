package cn.mcmod.tofucraft.tileentity;

import cn.mcmod.tofucraft.block.BlockLoader;
import cn.mcmod.tofucraft.block.BlockSaltFurnace;
import cn.mcmod.tofucraft.inventory.ContainerSaltFurnace;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntitySaltFurnace extends TileEntityLockable implements ITickable, ISidedInventory, IFluidHandler {
    /*
     * Comment:
     * Well this code needs a really deep reconstruction.
     * Damn...
     *
     * Program logic:
     * 1. Check if the cauldron is at proper place.
     *    - If the furnace is burning then:
     *      If there's place to place fire then place, if there's already fire then refresh, if non of these are fit then
     *      do nothing.
     * 2. Check if the cauldron has water.
     * 3. Check if there's suitable fuel.
     * 4. Consume fuel, and start a work cycle.
     * 5. Dump items to where it should be.
     * */

    private static final int[] SLOTS_TOP = new int[]{0, 2};
    private static final int[] SLOTS_SIDE = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_BOTTOM = new int[]{1, 3};
    private static final FluidStack nigari = new FluidStack(BlockLoader.NIGARI_FLUID, 10);

    public FluidTank nigariTank = new FluidTank(BlockLoader.NIGARI_FLUID, 0, 120);
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     * 0=Fuel, 1=Salt output, 2=Glass bottle, 3=Nigari output
     */
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int currentItemBurnTime = 0;
    private int cookTime = 0;
    private int totalCookTime;
    /**
     * -1=noCauldron, 0-3=waterlevel
     */
    private int lastCauldronStatus = 0;
    private String customName;

    public static void registerFixesFurnace(DataFixer fixer) {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntitySaltFurnace.class, new String[]{"Items"}));
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @SuppressWarnings("deprecation")
    public static int getCauldronStatus(BlockPos pos, World worldIn) {
        IBlockState stateUp = worldIn.getBlockState(pos.up());
        if (stateUp.getBlock() == Blocks.CAULDRON)
            return stateUp.getValue(BlockCauldron.LEVEL);
        else {
            Block block = stateUp.getBlock();
            if (block.isAir(stateUp, worldIn, pos.up())) return -1;
            else if (!block.getMaterial(stateUp).getCanBurn()) return -3;
            else {
                IBlockState state2Up = worldIn.getBlockState(pos.up(2));
                Block block2Up = state2Up.getBlock();
                if (block2Up.isAir(state2Up, worldIn, pos.up(2))) return -2;
                else return -3;
            }
        }
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {

        boolean wasBurning = isBurning();
        if (isBurning()) furnaceBurnTime--;

        int cauldron = this.getCauldronStatus();
        boolean isDirty = false;
        if (!world.isRemote) {
            if (furnaceBurnTime == 0 && this.canBoil(cauldron)) {
                ItemStack fuel = furnaceItemStacks.get(0);
                furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                if (furnaceBurnTime > 0) {
                    isDirty = true;
                    fuel.shrink(1);
                    if (fuel.getCount() == 0) {
                        furnaceItemStacks.set(0, fuel.getItem().getContainerItem(fuel));
                    }
                }
            }

            if (isBurning() && canBoil(cauldron) && cauldron >= 1) {
                cookTime++;
                if (cookTime == 200) {
                    cookTime = 0;
                    boilDown();
                    isDirty = true;
                }
            } else cookTime = 0;

            if (wasBurning != isBurning()) {
                isDirty = true;
                BlockSaltFurnace.setState(isBurning(), world, pos);
            }

            if (this.isBurning()) {
                if (cauldron == -1) {
                    this.world.setBlockState(this.pos.up(), Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 0));
                } else if (cauldron == -2) {
                    if (this.world.rand.nextInt(200) == 0) {
                        this.world.setBlockState(this.pos.up(2), Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 0));
                    }
                }
            }

            // Output nigari
            if (this.canOutputNigari()) {
                this.outputNigari();
            }
        }

        updateCauldronStatus();
        if (isDirty) markDirty();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.furnaceItemStacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(this.furnaceItemStacks.get(index)) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks.get(index));
        this.furnaceItemStacks.set(index, stack);

        if (stack != null && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.markDirty();
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        switch (i) {
            case 0:
                return TileEntityFurnace.isItemFuel(itemstack);
            case 2:
                return itemstack.isItemEqual(new ItemStack(Items.GLASS_BOTTLE, 1));
        }
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        this.furnaceItemStacks.clear();
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.tofucraft.SaltFurnace";
    }

    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomInventoryName(String name) {
        this.customName = name;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerSaltFurnace(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "tofucraft:saltFurnace";
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        switch (side) {
            case DOWN:
                return SLOTS_BOTTOM;
            case UP:
                return SLOTS_TOP;
            default:
                return SLOTS_SIDE;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == 1 || direction == EnumFacing.DOWN;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(par1NBTTagCompound, this.furnaceItemStacks);

        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.cookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = par1NBTTagCompound.getShort("ItemBurnTime");
        ;
        this.nigariTank.readFromNBT(par1NBTTagCompound.getCompoundTag("NigariTank"));

        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short) this.cookTime);
        par1NBTTagCompound.setInteger("ItemBurnTime", this.currentItemBurnTime);

        NBTTagCompound nigariTag = this.nigariTank.writeToNBT(new NBTTagCompound());
        par1NBTTagCompound.setTag("NigariTank", nigariTag);

        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.furnaceItemStacks);

        if (this.hasCustomName()) {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }

        return par1NBTTagCompound;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new SPacketUpdateTileEntity(pos, this.getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Returns cook Progress in integer between 0 and par1
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1) {
        return this.cookTime * par1 / 200;
    }

    /**
     * Returns remaining burn time in integer between 0 and par1
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public FluidTank getNigariTank() {
        return this.nigariTank;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    /**
     * returns true when it is possible to boil.
     */
    private boolean canBoil(int cauldronStat) {
        if (cauldronStat == 0 || cauldronStat <= -3) return false;//no water or no air for fire

        ItemStack stack = new ItemStack(ItemLoader.material, 2);
        if (this.furnaceItemStacks.get(1).isEmpty()) return true;//slot empty
        if (!this.furnaceItemStacks.get(1).isItemEqual(stack)) return false;//wrong item(s) present at salt slot.
        int result = furnaceItemStacks.get(1).getCount() + stack.getCount();
        return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
    }

    public void boilDown() {
        if (this.canBoil(this.getCauldronStatus())) {
            ItemStack stack = new ItemStack(ItemLoader.material, 2);

            if (this.furnaceItemStacks.get(1).isEmpty()) {
                this.furnaceItemStacks.set(1, stack.copy());
            } else if (this.furnaceItemStacks.get(1).isItemEqual(stack)) {
                //test
                int count = furnaceItemStacks.get(1).getCount() + stack.getCount();
                furnaceItemStacks.get(1).setCount(count);
            }
            this.nigariTank.fill(new FluidStack(BlockLoader.NIGARI_FLUID, 20), true);
            this.sendTankChangeToClients();

            // Decrease the water in the cauldron
            if (!world.isRemote) {
                int waterLevel = this.getCauldronWaterLevel();
                this.setCauldronWaterLevel(waterLevel - 1);
            }
        }
    }

    private boolean canOutputNigari() {
        ItemStack containerStack = this.furnaceItemStacks.get(2);

        if (containerStack.getCount() > 0) {
            ItemStack filledStack = new ItemStack(Items.GLASS_BOTTLE);

            if (containerStack.isItemEqual(filledStack)) {
                if (this.nigariTank.getFluidAmount() <= 10) return false;
                if (furnaceItemStacks.get(3).isEmpty()) return true;
                int result = furnaceItemStacks.get(3).getCount() + filledStack.getCount();
                return result <= getInventoryStackLimit() && result <= filledStack.getMaxStackSize();
            }
        }
        return false;
    }

    public void outputNigari() {
        ItemStack filledStack = new ItemStack(ItemLoader.nigari, 1);
        if (this.furnaceItemStacks.get(3).isEmpty()) {
            this.furnaceItemStacks.set(3, filledStack.copy());
        } else if (this.furnaceItemStacks.get(3).isItemEqual(filledStack)) {
            int count = furnaceItemStacks.get(3).getCount() + filledStack.getCount();
            furnaceItemStacks.get(3).setCount(count);
        }

        this.nigariTank.drain(20, true);
        this.sendTankChangeToClients();

        int count = furnaceItemStacks.get(2).getCount() - 1;

        furnaceItemStacks.get(2).setCount(count);
        if (furnaceItemStacks.get(2).getCount() == 0) {
            furnaceItemStacks.set(2, ItemStack.EMPTY);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

    /*
     * === Cauldron Control ===
     */
    private void updateCauldronStatus() {
        int stat = this.getCauldronStatus();

        if (this.lastCauldronStatus != stat && this.world.getBlockState(this.pos.up()).getBlock() == Blocks.CAULDRON) {
            int metadata = this.world.getBlockState(this.pos.up()).getValue(BlockCauldron.LEVEL);
            IBlockState newMetadata = Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, metadata);
            this.world.setBlockState(this.pos.up(), newMetadata, 2);
            this.lastCauldronStatus = stat;
        }
    }

    /**
     * returns water level without checking wether there is a cauldron or not.
     */
    private int getCauldronWaterLevel() {
        return world.getBlockState(this.pos.up()).getValue(BlockCauldron.LEVEL);
    }

    private void setCauldronWaterLevel(int level) {
        world.setBlockState(this.pos.up(), this.world.getBlockState(this.pos.up()).withProperty(BlockCauldron.LEVEL, level), 2);
    }

    /**
     * gets the water Level of the cauldron above this block
     *
     * @return water level. 0-3, int.
     * If there is no cauldron, returns negative value. -1, -2: must put fire one/two blck above, -3: inflammable
     */
    @SuppressWarnings("deprecation")
    public int getCauldronStatus() {
        IBlockState stateUp = this.world.getBlockState(this.pos.up());
        if (stateUp.getBlock() == Blocks.CAULDRON)
            return stateUp.getValue(BlockCauldron.LEVEL);
        else {
            Block block = stateUp.getBlock();
            if (block.isAir(stateUp, world, this.getPos().up()) || block == Blocks.FIRE) return -1;
            else if (!block.getMaterial(stateUp).getCanBurn()) return -3;
            else {
                IBlockState state2Up = this.world.getBlockState(this.pos.up(2));
                if (state2Up.getBlock().isAir(state2Up, world, this.pos.up(2))) return -2;
                else return -3;
            }
        }
    }
    /*
     * === Fluid Handler ===
     */

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return this.nigariTank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if ((resource == null) || (!resource.isFluidEqual(nigariTank.getFluid()))) {
            return null;
        }

        if (!canDrain(resource.getFluid())) return null;

        return nigariTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return nigariTank.drain(maxDrain, doDrain);
    }

    public boolean canDrain(Fluid fluid) {
        return nigariTank.getFluid().getUnlocalizedName() == fluid.getUnlocalizedName();
    }

    protected void sendTankChangeToClients() {

        if (!this.hasWorld() || this.world.isRemote) return;

        List<EntityPlayer> list = this.getWorld().playerEntities;
        for (EntityPlayer player : list) {
            if (player instanceof EntityPlayerMP) {
                ((EntityPlayerMP) player).connection.sendPacket(this.getUpdatePacket());
            }
        }

    }

}
