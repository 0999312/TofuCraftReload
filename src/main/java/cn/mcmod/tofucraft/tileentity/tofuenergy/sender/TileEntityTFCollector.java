package cn.mcmod.tofucraft.tileentity.tofuenergy.sender;

import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;

public class TileEntityTFCollector extends TileEntitySenderBase {

    private static final int POWER = 3;
    public TileEntityTFCollector() {
        super(5000);
    }

    @Override
    public void update() {
        //Update energy sender logic
        super.update();

        if (!world.isRemote) {
        	if(this.isRedstonePowered()){
	            int workload = POWER;
	
	            //Transform workload to power
	            if (workload > 0 && getEnergyStored() < getMaxEnergyStored()) {
	                workload -= receive(Math.min(workload, POWER), false);
	            }

        	}
        }
    }

}
