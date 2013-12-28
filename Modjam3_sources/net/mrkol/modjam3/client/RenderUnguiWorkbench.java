package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.mrkol.modjam3.blocks.TileUnguiWorkbench;

public class RenderUnguiWorkbench extends RenderUnguiStation
{
	public static ModelUnguiWorkbench model = new ModelUnguiWorkbench();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		TileUnguiWorkbench tile = (TileUnguiWorkbench) tileentity;

		GL11.glPushMatrix();
		{
			bindTexture(new ResourceLocation("ungui:textures/UnguiWorkbench.png"));
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
				
				
				model.render(tile.buttons);
			}
			GL11.glPopMatrix();

			GL11.glTranslatef(-0.5f, 0, -0.5f);
			GL11.glPushMatrix();
			{
				int X = 1;
				int Y = 1;
				for(int i = 0; i < tile.items.length; i++)
				{
					if(tile.items != null)
					{
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(X/16f + 0.5f/4f, 1, Y/16f + 0.5f/4f); 
							GL11.glScalef(0.5f, 0.5f, 0.5f);
							GL11.glRotatef(180f, 0, 1, 0);
							this.renderItemStack(tile.items[i], 100, false);
						}
						GL11.glPopMatrix();
						
						X += 5;
						if(X > 11)
						{
							Y += 5;
							X = 1;
						}
					}
				}
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
}
