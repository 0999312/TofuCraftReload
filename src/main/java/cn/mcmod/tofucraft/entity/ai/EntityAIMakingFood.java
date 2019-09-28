package cn.mcmod.tofucraft.entity.ai;

import cn.mcmod.tofucraft.entity.EntityTofunian;
import cn.mcmod.tofucraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMakingFood extends EntityAIMoveToBlock {
    private int tick;
    private final EntityTofunian tofunian;

    public EntityAIMakingFood(EntityTofunian entityTofunian, double speed) {
        super(entityTofunian, speed, 10);
        this.tofunian = entityTofunian;
    }

    @Override
    public boolean shouldExecute() {
        return !this.tofunian.hasEnoughCraftedItem(2, 0) && !this.findSoy().isEmpty() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return tick <= 62 && super.shouldContinueExecuting();
    }

    @Override
    public void updateTask() {
        super.updateTask();
        this.tofunian.getLookHelper().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double) (this.destinationBlock.getY()), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.tofunian.getVerticalFaceSpeed());

        if (this.getIsAboveDestination()) {
            ++tick;

            if (tick % 10 == 0) {
                this.tofunian.swingArm(EnumHand.MAIN_HAND);
            }

            if (tick == 60) {
                this.findSoy().shrink(1);
                this.tofunian.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.6F, 0.65F);
                this.makeFood();
                if (!this.tofunian.hasEnoughCraftedItem(2, 0) && !this.findSoy().isEmpty()) {
                    tick = 0;
                }
            }
        }
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block instanceof BlockWorkbench) {
                return true;
            }

            return false;
        }
    }

    public ItemStack findSoy() {
        for (int i = 0; i < this.tofunian.getVillagerInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = this.tofunian.getVillagerInventory().getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == ItemLoader.soybeans) {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean getIsAboveDestination() {
        return super.getIsAboveDestination();
    }

    public void makeFood() {
        this.tofunian.getVillagerInventory().addItem(new ItemStack(ItemLoader.tofu_food, 2, 3));
    }
}
