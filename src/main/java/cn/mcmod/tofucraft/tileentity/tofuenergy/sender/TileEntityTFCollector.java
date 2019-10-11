package cn.mcmod.tofucraft.tileentity.tofuenergy.sender;

import cn.mcmod.tofucraft.api.recipes.CatalystEfficiencyMap;
import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBaseInvenotried;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TileEntityTFCollector extends TileEntitySenderBaseInvenotried {

    /*
     * Comment:
     * A machine which generates from thick TofuForce aura in its surroundings.
     * Needs a tofu gem like catalyst to start to work, and the power varies as the environment changes.
     * The more TFCollector around it, the more then less efficient it will be then.
     * This follows a curve similar to the Logistic Equation.
     * */

    private static final int POWER = 10;
    private static final int INTERFERE_RADIUS = 5;

    private static final double midpoint = 6;
    private static final double L = 1;
    private static final double k = 1;


    public TileEntityTFCollector() {
        super(1000, 1);
    }

    @Override
    public void update() {
        //Update energy sender logic
        super.update();

        if (!world.isRemote) {
            double workload = POWER;
            workload *= CatalystEfficiencyMap.getEfficiency(inventory.get(0));
            if (workload == 0) return;

            //Get all tile within a certain radius, then calculate the efficiency
            List<TileEntity> tes = TofuNetwork.toTiles(
                    TofuNetwork.Instance.getTEWithinRadius(this, INTERFERE_RADIUS)
                            .filter(entry -> entry.getValue() instanceof TileEntityTFCollector));

            int interfere_count = tes.size() - 1;
            if (interfere_count > 0) {
                double eff = L / (1 + Math.exp(-k / (interfere_count - midpoint)));
                eff = 1 - eff;
                workload *= eff;
            }


            //Dump energy to the machine buffer
            receive((int) Math.floor(workload), false);
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return CatalystEfficiencyMap.getEfficiency(itemStack) > 0;
    }

    @Override
    public String getName() {
        return "container.tofucraft.collector";
    }
}
