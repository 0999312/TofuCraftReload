package cn.mcmod.tofucraft.tileentity.tofuenergy.sender;

import cn.mcmod.tofucraft.api.recipes.TofuCatalystMap;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBaseInvenotried;
import net.minecraft.item.ItemStack;

public class TileEntityTFCollector extends TileEntitySenderBaseInvenotried {

    private static final int POWER = 6;

    public TileEntityTFCollector() {
        super(5000, 1);
    }

    @Override
    public void update() {
        //Update energy sender logic
        super.update();

        if (!world.isRemote) {
            if (this.isRedstonePowered()) {
                receive(POWER, false);
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return TofuCatalystMap.getPossibleEfficiency(itemStack) > 0;
    }

    @Override
    public boolean isEnergyFull() {
        return false;
    }

    @Override
    public boolean isEnergyEmpty() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
