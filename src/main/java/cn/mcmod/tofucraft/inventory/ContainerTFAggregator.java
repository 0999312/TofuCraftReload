package cn.mcmod.tofucraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerTFAggregator extends Container {
    private final IInventory tileMachine;

    public ContainerTFAggregator(InventoryPlayer playerInventory, IInventory machineInventory) {
        this.tileMachine = machineInventory;

        this.addSlotToContainer(new Slot(machineInventory, 0, 23, 35));
        this.addSlotToContainer(new Slot(machineInventory, 1, 68, 35));


        for (int var3 = 0; var3 < 3; var3++) {
            for (int var4 = 0; var4 < 9; var4++) {
                this.addSlotToContainer(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; var3++) {
            this.addSlotToContainer(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileMachine);
    }


    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);
                icontainerlistener.sendWindowProperty(this, 0, this.tileMachine.getField(0));
                icontainerlistener.sendWindowProperty(this, 2, this.tileMachine.getField(2));
                icontainerlistener.sendWindowProperty(this, 1, this.tileMachine.getField(1));
        }

    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileMachine.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileMachine.isUsableByPlayer(playerIn);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            switch (slotIndex) {
                case 0:
                    if (!this.mergeItemStack(itemstack1, 1, 37, true))
                        return ItemStack.EMPTY;
                default:
                    if (!this.mergeItemStack(itemstack1, 0, 0, false)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onSlotChange(itemstack1, itemstack);
            }


            if (itemstack1.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }
}
