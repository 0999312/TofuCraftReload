package cn.mcmod.tofucraft.client.render.tileentity;

import cn.mcmod.tofucraft.client.render.TcModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.EnumFacing;

public class ModelSaltpan extends ModelBase {

    /** The chest lid in the chest's model. */
	public TcModelRenderer contentRenderer = new TcModelRenderer(this);
	public TcModelRenderer sideNorth = new TcModelRenderer(this);
	public TcModelRenderer sideEast = new TcModelRenderer(this);
	public TcModelRenderer sideSouth = new TcModelRenderer(this);
	public TcModelRenderer sideWest = new TcModelRenderer(this);
	
    public ModelSaltpan()
    {        
    	this.contentRenderer.setTextureSize(16, 16);
    	this.contentRenderer.addPane(16, 3, 0, 16, 16, EnumFacing.UP);
    	this.sideNorth.setTextureSize(16, 16);
    	this.sideNorth.addPane(16, 1,  0, 16, 2, EnumFacing.NORTH);
    	this.sideEast.setTextureSize(16, 16);
    	this.sideEast.addPane(16, 1, 16, 16, 2, EnumFacing.EAST);
    	this.sideSouth.setTextureSize(16, 16);
    	this.sideSouth.addPane( 0, 1, 16, 16, 2, EnumFacing.SOUTH);
    	this.sideWest.setTextureSize(16, 16);
    	this.sideWest.addPane( 0, 1,  0, 16, 2, EnumFacing.WEST);
    }
    
    public void renderContent() {
    	this.contentRenderer.renderDirectly();
    }
    
    public void renderContentEdge(boolean north, boolean east, boolean south, boolean west) {
    	if (north) this.sideNorth.renderDirectly();
    	if(east) this.sideEast.renderDirectly();
    	if(south) this.sideSouth.renderDirectly();
    	if(west) this.sideWest.renderDirectly();
    }
    
}
