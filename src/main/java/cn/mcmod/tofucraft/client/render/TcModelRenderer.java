package cn.mcmod.tofucraft.client.render;

import java.util.ArrayList;
import java.util.List;

import cn.mcmod.tofucraft.client.model.ModelPane;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TcModelRenderer extends ModelRenderer {
	public List<ModelPane> paneList = new ArrayList<ModelPane>();
	private int displayList;
	private boolean compiled;
	
    public TcModelRenderer(ModelBase model, String boxNameIn)
    {
    	super(model, boxNameIn);
    }

    public TcModelRenderer(ModelBase model)
    {
        super(model);
    }

    public TcModelRenderer(ModelBase model, int texOffX, int texOffY)
    {
    	super(model, texOffX, texOffY);
    }
    
//    public TcModelRenderer addPane(ModelPane paneIn) {
//    	
//    	this.paneList.add(paneIn);
//    	return this;
//    }
    
    public TcModelRenderer addPane(int posx, int posy, int posz, int width, int height, EnumFacing facing) {
		ModelPane pane;
		switch (facing) {
		case UP:
			pane = new ModelPane(this, 16-posx, posz, posy, width, height, facing);
			break;
		case DOWN:
			pane = new ModelPane(this, posx, posz, posy, width, height, facing);
			break;
		case NORTH:
			pane = new ModelPane(this, 16-posx, posy, posz, width, height, facing);
			break;
		case EAST:
			pane = new ModelPane(this, 16-posz, posy,16-posx, width, height, facing);
			break;
		case SOUTH:
			pane = new ModelPane(this, posx, posy, 16-posz, width, height, facing);
			break;
		default:
			pane = new ModelPane(this, posz, posy, posx, width, height, facing);
		}
		this.paneList.add(pane);
    	return this;
    }
    
    @SideOnly(Side.CLIENT)
    private void compileDisplayPane() {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, 4864);
        BufferBuilder vertexbuffer = Tessellator.getInstance().getBuffer();
        
        for (int i = 0; i < this.paneList.size(); ++i)
        {
        	ModelPane pane = (ModelPane)this.paneList.get(i);
            pane.render(vertexbuffer, 0.0625F);
        }
        
        GlStateManager.glEndList();
        this.compiled = true;
    }
    
    public void renderDirectly() {
    	
        if (!this.compiled)
        {
            this.compileDisplayPane();
        }

        GlStateManager.pushMatrix();

        GlStateManager.callList(this.displayList);

        GlStateManager.popMatrix();
        
    }
    
}
