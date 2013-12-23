package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.raytracing.Raytracer;

public class RendererUnguiFurnace extends RenderUnguiStation implements IItemRenderer
{
	public ModelUnguiFurnace model = new ModelUnguiFurnace();
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float arg4)
	{
		TileUnguiFurnace tile = (TileUnguiFurnace) te;
		
		GL11.glPushMatrix();
		{
				bindTexture(new ResourceLocation("ungui:textures/UnguiFurnace.png"));
				GL11.glTranslated(x, y, z);
				GL11.glTranslatef(0.5f, 0, 0.5f);
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
				
				GL11.glPushMatrix();
				{
					
					GL11.glTranslatef(0, 1, 0);
					
					GL11.glRotatef(180f, 1, 0, 0);
					
					
					model.render();
				}
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				{
					if(tile.fuel != null)
					{
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0, 1f/16f, 0); 
							this.renderItemStack(tile.fuel, 100, false);
						}
						GL11.glPopMatrix();
					}
					
					
					if(tile.fueling != null)
					{
						GL11.glPushMatrix();
						{
							if(!(tile.fueling.getItem() instanceof ItemBlock))
							{
								GL11.glTranslatef(0, (0.5f/8f) * (1 - (tile.fuelTimer / tile.BURN_TIME)), 0);
							}
							GL11.glScalef(1, tile.fuelTimer / tile.BURN_TIME, 1);
							
							GL11.glTranslatef(0, 1f/16f, 0); 
							this.renderItemStack(tile.fueling, 1, true);
							
						}
						GL11.glPopMatrix();
					}

					if(tile.smeltingIn != null)
					{
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0, 9f/16f, 0); 
							this.renderItemStack(tile.smeltingIn, 100, false);
						}
						GL11.glPopMatrix();
					}

					if(tile.smeltingOut != null)
					{
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0, 9f/16f, 0); 
							this.renderItemStack(tile.smeltingOut, 100, true);
						}
						GL11.glPopMatrix();
					}
					
				}
				GL11.glPopMatrix();				
		}
		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(ItemStack is, ItemRenderType arg1)
	{
		return is.getItemDamage() == 0;
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
				GL11.glTranslatef(0, 1f, 0);
				GL11.glRotatef(90, 0, -1, 0);
				GL11.glRotatef(180, 0, 0, 1);
			}
			
			if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			{
				GL11.glTranslatef(0.5f, 1f, 0.5f);
				GL11.glRotatef(90, 0, -1, 0);
				GL11.glRotatef(180, 0, 0, 1);
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
