package cn.mcmod.tofucraft.inventory.slot;

import cn.mcmod.tofucraft.advancements.TofuAdvancements;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class SlotSaltFurnace extends Slot {
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    private int field_75228_b;

    public SlotSaltFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.thePlayer = par1EntityPlayer;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    public ItemStack decrStackSize(int par1)
    {
        if (this.getHasStack())
        {
            this.field_75228_b += Math.min(par1, this.getStack().getCount());
        }

        return super.decrStackSize(par1);
    }

    @Override
    public ItemStack onTake(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.onCrafting(par2ItemStack);
        super.onTake(par1EntityPlayer, par2ItemStack);
        return par2ItemStack;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.field_75228_b += par2;
        this.onCrafting(par1ItemStack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(this.thePlayer.world, this.thePlayer, this.field_75228_b);

        if (!this.thePlayer.world.isRemote)
        {
            int i = this.field_75228_b;
            float f = par1ItemStack.getItem() == ItemLoader.material ? 0.2f : par1ItemStack.getItem() == ItemLoader.nigari ? 0.3f : 0.0f;
            int j;

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.floor(i * f);

                if (j < MathHelper.ceil(i * f) && (float)Math.random() < i * f - j)
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.thePlayer.world.spawnEntity(new EntityXPOrb(this.thePlayer.world, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, j));
            }
        }

        this.field_75228_b = 0;


        if (par1ItemStack.getItem() == ItemLoader.material)
        {
            TofuAdvancements.grantAdvancement(this.thePlayer, "getsalt");
        }
        else if (par1ItemStack.getItem() == ItemLoader.nigari)
        {
            TofuAdvancements.grantAdvancement(this.thePlayer, "getnigari");
        }
    }

}
