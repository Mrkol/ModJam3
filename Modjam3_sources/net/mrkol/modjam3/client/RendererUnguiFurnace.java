package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
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

public class RendererUnguiFurnace extends TileEntitySpecialRenderer
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
				GL11.glTranslatef(0.5f, 1, 0.5f);
				GL11.glRotatef(90f, 0, 1, 0);
				GL11.glRotatef(180f, 1, 0, 0);
				
				model.render();
				

				if(tile.fuel != null)
				{
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(0, 0.825f, 0);
						
						if(!(tile.fuel.getItem() instanceof ItemBlock))
						{
							GL11.glTranslatef(0, 0.1f, 0);
							GL11.glRotatef(90f, 1, 0, 0);
						}
						
						EntityItem ie = new EntityItem(null, 0, 0, 0, tile.fuel);
	
						ie.hoverStart = 0;
						float fp = tile.fuel.stackSize / tile.fuel.getItem().getItemStackLimit(tile.fuel) * 100;
						this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
						//if(fp > 25)
						{
							GL11.glPushMatrix();
							{
								GL11.glTranslatef(0, -0.1f, 0);
								GL11.glRotatef(10, 1, 0, 0);
								GL11.glRotatef(90, 0, 0, 1);
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
	
}
