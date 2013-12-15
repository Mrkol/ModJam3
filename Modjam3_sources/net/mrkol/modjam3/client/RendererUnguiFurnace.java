package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.raytracing.Raytracer;

public class RendererUnguiFurnace extends TileEntitySpecialRenderer implements IItemRenderer
{
	public RenderItem itemRenderer;
	public ModelUnguiFurnace model = new ModelUnguiFurnace();
	
	public RendererUnguiFurnace()
	{
		this.itemRenderer = new RenderItem()
		{
			public byte getMiniBlockCount(ItemStack stack)
			{
				return 1;
			}
			
			public byte getMiniItemCount(ItemStack stack)
			{
				return 1;
			}
			
			public boolean shouldBob()
			{
				return false;
			}
			
			public boolean shouldSpreadItems()
			{
				return false;
			}
		};
		this.itemRenderer.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float arg4)
	{
		TileUnguiFurnace tile = (TileUnguiFurnace) te;
		GL11.glPushMatrix();
		{
			
			
			
				bindTexture(new ResourceLocation("ungui:textures/UnguiFurnace.png"));
				GL11.glTranslated(x, y, z);
				/*GL11.glPushMatrix();
				{
					Tessellator t = Tessellator.instance;
					t.startDrawingQuads();
					t.addVertex(Raytracer.m_cuboid.X                          , Raytracer.m_cuboid.Y + Raytracer.m_cuboid.W, Raytracer.m_cuboid.Z);
					t.addVertex(Raytracer.m_cuboid.X + Raytracer.m_cuboid.H , Raytracer.m_cuboid.Y + Raytracer.m_cuboid.W, Raytracer.m_cuboid.Z);
					t.addVertex(Raytracer.m_cuboid.X + Raytracer.m_cuboid.H , Raytracer.m_cuboid.Y                         , Raytracer.m_cuboid.Z);
					t.addVertex(Raytracer.m_cuboid.X                          , Raytracer.m_cuboid.Y                         , Raytracer.m_cuboid.Z);
					t.addVertex(Raytracer.m_cuboid.X                          , Raytracer.m_cuboid.Y                          , Raytracer.m_cuboid.Z + Raytracer.m_cuboid.D);
					t.addVertex(Raytracer.m_cuboid.X + Raytracer.m_cuboid.H , Raytracer.m_cuboid.Y                          , Raytracer.m_cuboid.Z + Raytracer.m_cuboid.D);
					t.addVertex(Raytracer.m_cuboid.X + Raytracer.m_cuboid.H, Raytracer.m_cuboid.Y + Raytracer.m_cuboid.W, Raytracer.m_cuboid.Z + Raytracer.m_cuboid.D);
					t.addVertex(Raytracer.m_cuboid.X                          , Raytracer.m_cuboid.Y + Raytracer.m_cuboid.W, Raytracer.m_cuboid.Z + Raytracer.m_cuboid.D);
					t.draw();
				}
				GL11.glPopMatrix();*/
				GL11.glTranslatef(0.5f, 1, 0.5f);
				switch(tile.rotation)
				{
					case 0:
						GL11.glRotatef(180, 0, 1, 0);
						break;
						
					case 1:
						GL11.glRotatef(90, 0, 1, 0);
						break;
						
					case 2:
						GL11.glRotatef(0, 0, 1, 0);
						break;
						
					case 3:
						GL11.glRotatef(270, 0, 1, 0);
						break;
				}
				
				GL11.glRotatef(180f, 1, 0, 0);
				
				model.render();

				GL11.glPushMatrix();
				{
					GL11.glTranslatef(0, 13.25f/16f, 0);
					
					if(tile.fuel != null)
					{
						GL11.glPushMatrix();
						{
							boolean isblock = (tile.fuel.getItem() instanceof ItemBlock);
							if(!isblock)
							{
								GL11.glTranslatef(0, 0.1f, 0);
								GL11.glRotatef(90f, 1, 0, 0);
							}
							else
							{
								GL11.glTranslatef(0, 0, 0);
								GL11.glRotatef(180f, 1, 0, 0);
							}
							
							EntityItem ie = new EntityItem(null, 0, 0, 0, tile.fuel);
							ie.hoverStart = 0;
							float fp = (float)tile.fuel.stackSize / (float)tile.fuel.getItem().getItemStackLimit(tile.fuel) * 100f;
							this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
							int sl =  tile.fuel.getItem().getItemStackLimit(tile.fuel);
							if(fp >= 25 && sl > 2) // >1/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(-4f/16f, 0, 2f/16f);
									}
									else
									{
										GL11.glTranslatef(0, -0.1f, 0.01f);
										GL11.glRotatef(10, 1, 0, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
										this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
							if(fp >= 50 && sl > 3) // >2/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(4f/16f, 0, 0);
									}
									else
									{
										GL11.glTranslatef(0.2f, 0, 0.02f);
										GL11.glRotatef(18, 0, 1, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
									this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
							if(fp >= 75 && sl > 4) // >3/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(0, 0, 4f/16f);
									}
									else
									{
										GL11.glTranslatef(0, 0.2f, 0.11f);
										GL11.glRotatef(45, 0, 0, 1);
										GL11.glRotatef(-38, 1, 0, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
									this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
						}
						GL11.glPopMatrix();
					}
					
					
					
					
					
					
					
					

					if(tile.smeltingIn != null)
					{
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0, -8f/16f, 0);
							
							boolean isblock = (tile.smeltingIn.getItem() instanceof ItemBlock);
							if(!isblock)
							{
								GL11.glTranslatef(0, 0.1f, 0);
								GL11.glRotatef(90f, 1, 0, 0);
							}
							else
							{
								GL11.glTranslatef(0, 0, 0);
								GL11.glRotatef(180f, 1, 0, 0);
							}
							
							EntityItem ie = new EntityItem(null, 0, 0, 0, tile.smeltingIn);
							ie.hoverStart = 0;
							float fp = (float)tile.smeltingIn.stackSize / (float)tile.smeltingIn.getItem().getItemStackLimit(tile.smeltingIn) * 100f;
							this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
							int sl =  tile.smeltingIn.getItem().getItemStackLimit(tile.smeltingIn);
							if(fp >= 25 && sl > 2) // >1/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(4f/16f, 0, 2f/16f);
									}
									else
									{
										GL11.glTranslatef(0, -0.1f, 0.01f);
										GL11.glRotatef(10, 1, 0, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
										this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
							if(fp >= 50 && sl > 3) // >2/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(-4f/16f, 0, 0);
									}
									else
									{
										GL11.glTranslatef(0.2f, 0, 0.02f);
										GL11.glRotatef(18, 0, 1, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
										this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
							if(fp >= 75 && sl > 4) // >3/4 of a stack
							{
								GL11.glPushMatrix();
								{
									if(isblock) 
									{
										GL11.glTranslatef(0, 0, 4f/16f);
									}
									else
									{
										GL11.glTranslatef(0, 0.2f, 0.11f);
										GL11.glRotatef(45, 0, 0, 1);
										GL11.glRotatef(-38, 1, 0, 0);
										GL11.glRotatef(90, 0, 0, 1);
									}
									this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
								}
								GL11.glPopMatrix();
							}
						}
						GL11.glPopMatrix();
					}
					
					
				}
				GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(ItemStack arg0, ItemRenderType arg1)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack arg1, Object... arg2)
	{
		GL11.glPushMatrix();
		{
			bindTexture(new ResourceLocation("ungui:textures/UnguiFurnace.png"));
			if(type == ItemRenderType.INVENTORY) 
			{
				GL11.glRotatef(180, 0, 0, 1);
				GL11.glTranslatef(0, -0.5f, 0);
				GL11.glRotatef(90, 0, 1, 0);
			}
			
			if(type == ItemRenderType.ENTITY)
			{
			}
			model.render();
		}
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2)
	{
		return true;
	}
	
}
