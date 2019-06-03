package cn.mcmod.tofucraft.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelPane {
	
    /** The (x,y,z) vertex positions and (u,v) texture coordinates for each of the 8 points on a cube */
    private final PositionTextureVertex[] vertexPositions;
    /** An array of 6 TexturedQuads, one for each face of a cube */
    private final TexturedQuad quad;
	
	public ModelPane(ModelRenderer renderer, int texX, int texY, int depth,int width, int height, EnumFacing facing) {
		
        this.vertexPositions = new PositionTextureVertex[4];
        
		this.getVertexPositions(renderer, texX, texY, depth, width, height, facing);
		
        this.quad = new TexturedQuad(this.vertexPositions,
        		texX, (int)renderer.textureHeight-(texY + height), texX + width, (int)renderer.textureHeight-(texY),
        		renderer.textureWidth, renderer.textureHeight);
	}
	
	public ModelPane(ModelRenderer renderer, int texX, int texY, int depth,int width, int height, EnumFacing facing, int frame) {
		
        this.vertexPositions = new PositionTextureVertex[4];
        
		this.getVertexPositions(renderer, texX, texY, depth, width, height, facing);
		
        this.quad = new TexturedQuad(this.vertexPositions,
        		texX, (int)renderer.textureHeight-(texY + height) + frame*(int)renderer.textureWidth, texX + width, (int)renderer.textureHeight-(texY)+ frame*(int)renderer.textureWidth,
        		renderer.textureWidth, renderer.textureHeight);
	}
	
	private void getVertexPositions(ModelRenderer renderer, int texX, int texY, int depth,int width, int height, EnumFacing facing) {
		
		PositionTextureVertex positiontexturevertexA;
		PositionTextureVertex positiontexturevertexB;
		PositionTextureVertex positiontexturevertexC;
		PositionTextureVertex positiontexturevertexD;
        int x1 = 0;
        int y1 = 0;
        int z1 = 0;
        
		switch (facing) {
		case UP:
			x1 = -width;
			z1 = height;
			positiontexturevertexA = new PositionTextureVertex(16+ 0-texX, depth,  0+texY, 0, 0);// 8.0F, 8.0F);//1
			positiontexturevertexB = new PositionTextureVertex(16+x1-texX, depth,  0+texY, 0, 0);// 8.0F, 0.0F);//2
			positiontexturevertexC = new PositionTextureVertex(16+x1-texX, depth, z1+texY, 0, 0);// 8.0F, 0.0F);//6
			positiontexturevertexD = new PositionTextureVertex(16+ 0-texX, depth, z1+texY, 0, 0);// 8.0F, 8.0F);//5
			break;
		case DOWN:
			x1 = width;
			z1 = height;
			positiontexturevertexA = new PositionTextureVertex( 0+texX, depth,  0+texY, 0, 0);// 0.0F, 8.0F);//4
			positiontexturevertexB = new PositionTextureVertex(x1+texX, depth,  0+texY, 0, 0);// 0.0F, 0.0F);//3
			positiontexturevertexC = new PositionTextureVertex(x1+texX, depth, z1+texY, 0, 0);// 0.0F, 0.0F);//7
			positiontexturevertexD = new PositionTextureVertex( 0+texX, depth, z1+texY, 0, 0);// 0.0F, 8.0F);//0
			break;
		case NORTH:
			x1 = -width;
			y1 = height;
			positiontexturevertexA = new PositionTextureVertex(16-texX+ 0,texY+ 0, depth, 0, 0);// 0.0F, 8.0F);//0
			positiontexturevertexB = new PositionTextureVertex(16-texX+x1,texY+ 0, depth, 0, 0);// 0.0F, 0.0F);//7
			positiontexturevertexC = new PositionTextureVertex(16-texX+x1,texY+y1, depth, 0, 0);// 8.0F, 0.0F);//2
			positiontexturevertexD = new PositionTextureVertex(16-texX+ 0,texY+y1, depth, 0, 0);// 8.0F, 8.0F);//1
			break;
		case EAST:
			z1 = -width;
			y1 = height;
			positiontexturevertexA = new PositionTextureVertex(16-depth,texY+ 0,16-texX+ 0, 0, 0);// 0.0F, 8.0F);//4
			positiontexturevertexB = new PositionTextureVertex(16-depth,texY+ 0,16-texX+z1, 0, 0);// 0.0F, 8.0F);//0
			positiontexturevertexC = new PositionTextureVertex(16-depth,texY+y1,16-texX+z1, 0, 0);// 8.0F, 8.0F);//1
			positiontexturevertexD = new PositionTextureVertex(16-depth,texY+y1,16-texX+ 0, 0, 0);// 8.0F, 8.0F);//5
			break;
		case SOUTH:
			x1 = width;
			y1 = height;
			positiontexturevertexA = new PositionTextureVertex(texX+ 0,texY+ 0,16-depth, 0, 0);// 0.0F, 0.0F);//3
			positiontexturevertexB = new PositionTextureVertex(texX+x1,texY+ 0,16-depth, 0, 0);// 0.0F, 8.0F);//4
			positiontexturevertexC = new PositionTextureVertex(texX+x1,texY+y1,16-depth, 0, 0);// 8.0F, 8.0F);//5
			positiontexturevertexD = new PositionTextureVertex(texX+ 0,texY+y1,16-depth, 0, 0);// 8.0F, 0.0F);//6
			break;
		default:
			z1 = width;
			y1 = height;
			positiontexturevertexA = new PositionTextureVertex(depth,texY+ 0,texX+ 0, 0, 0);// 0.0F, 0.0F);//7
			positiontexturevertexB = new PositionTextureVertex(depth,texY+ 0,texX+z1, 0, 0);// 0.0F, 0.0F);//3
			positiontexturevertexC = new PositionTextureVertex(depth,texY+y1,texX+z1, 0, 0);// 8.0F, 0.0F);//6
			positiontexturevertexD = new PositionTextureVertex(depth,texY+y1,texX+ 0, 0, 0);// 8.0F, 0.0F);//2
			break;
		}
		this.vertexPositions[0] = positiontexturevertexC;
		this.vertexPositions[1] = positiontexturevertexD;
		this.vertexPositions[2] = positiontexturevertexA;
		this.vertexPositions[3] = positiontexturevertexB;
	}
	
    @SideOnly(Side.CLIENT)
    public void render(BufferBuilder renderer, float scale)
    {
    	this.quad.draw(renderer, scale);
    }
    
}
