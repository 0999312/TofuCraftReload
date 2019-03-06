package cn.mcmod.tofucraft.inventory;

import cn.mcmod.tofucraft.gui.TofuGuiHandler;
import cn.mcmod.tofucraft.inventory.slot.SlotSaltFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSaltFurnace extends Container {
    private final IInventory tileSaltFurnace;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerSaltFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory)
    {
        this.tileSaltFurnace = furnaceInventory;
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 0, 23, 53));
        this.addSlotToContainer(new SlotSaltFurnace(playerInventory.player, furnaceInventory, 1, 82, 35));
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 134, 17));
        this.addSlotToContainer(new SlotSaltFurnace(playerInventory.player, furnaceInventory, 3, 134, 52));


        for (int var3 = 0; var3 < 3; var3++)
        {
            for (int var4 = 0; var4 < 9; var4++)
            {
                this.addSlotToContainer(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; var3++)
        {
            this.addSlotToContainer(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileSaltFurnace);
    }


    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.cookTime != this.tileSaltFurnace.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileSaltFurnace.getField(2));
            }

            if (this.furnaceBurnTime != this.tileSaltFurnace.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileSaltFurnace.getField(0));
            }

            if (this.currentItemBurnTime != this.tileSaltFurnace.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileSaltFurnace.getField(1));
            }

            if (this.totalCookTime != this.tileSaltFurnace.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileSaltFurnace.getField(3));
            }
        }

        this.cookTime = this.tileSaltFurnace.getField(2);
        this.furnaceBurnTime = this.tileSaltFurnace.getField(0);
        this.currentItemBurnTime = this.tileSaltFurnace.getField(1);
        this.totalCookTime = this.tileSaltFurnace.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileSaltFurnace.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileSaltFurnace.isUsableByPlayer(playerIn);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
    {
        // 0-3: Salt furnace inventory
        // 4-30: Player inventory
        // 31-39: Hot bar in the player inventory

        ItemStack itemStack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (index == 1 || index == 3)
            {
                if (!this.mergeItemStack(itemStack1, 4, 40, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemStack1, itemStack);
            }
            else if (index >= 4)
            {
                if (TileEntityFurnace.isItemFuel(itemStack1))
                {
                    if (!this.mergeItemStack(itemStack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemStack1.getItem() == Items.GLASS_BOTTLE)
                {
                    if (!this.mergeItemStack(itemStack1, 2, 3, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 4 && index < 31)
                {
                    if (!this.mergeItemStack(itemStack1, 31, 40, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 31 && index < 40 && !this.mergeItemStack(itemStack1, 4, 31, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemStack1, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemStack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(par1EntityPlayer, itemStack1);
        }

        return itemStack;
    }
}
