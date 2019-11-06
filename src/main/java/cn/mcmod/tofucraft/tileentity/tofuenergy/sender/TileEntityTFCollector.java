package cn.mcmod.tofucraft.tileentity.tofuenergy.sender;

import cn.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import cn.mcmod.tofucraft.base.tileentity.TileEntitySenderBase;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class TileEntityTFCollector extends TileEntitySenderBase {

    private static final int POWER = 5;
    private static final int INTERFERE_RADIUS = 5;

    private static final double midpoint = 6;
    private static final double L = 1;
    private static final double k = 1;


    public TileEntityTFCollector() {
        super(5000);
    }

    @Override
    public void update() {
        //Update energy sender logic
        super.update();

        if (!world.isRemote) {
        	if(this.isRedstonePowered()){
	            double workload = POWER;
	
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
    }

}
