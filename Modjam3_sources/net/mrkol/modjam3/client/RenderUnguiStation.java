package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderUnguiStation extends TileEntitySpecialRenderer implements IItemRenderer
{
	public RenderItem itemRenderer;

	public RenderUnguiStation()
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
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
		
	}
	
	public void renderItemStack(ItemStack is, float percent, boolean AZF)
	{
		if(is == null) return;
		GL11.glPushMatrix();
		{
			boolean isblock = (is.getItem() instanceof ItemBlock);
			if(!isblock)
			{
				GL11.glTranslatef(0, 0.5f/16f/2f, -1f/16f); 
				GL11.glRotatef(90f, 1, 0, 0);
			}
			else
			{
				GL11.glTranslatef(0, 8f/16f/4f, 0); 
			}
			
			EntityItem ie = new EntityItem(null, 0, 0, 0, is);
			ie.hoverStart = 0;

			float fp = (float)is.stackSize / (float)is.getItem().getItemStackLimit(is) * percent;
			int sl =  is.getItem().getItemStackLimit(is);
			
			if(fp > 0)
			{
				GL11.glPushMatrix();
				{
					if(!isblock) GL11.glTranslatef(0, -2f/16f/2f, 0);
					if(AZF) GL11.glScalef(1f + 1f/10000f, 1f + 1f/10000f, 1f + 1f/10000f);
					this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}
			if(fp >= (percent / 4f) && sl > 2) // >1/4 of a stack
			{
				GL11.glPushMatrix();
				{
					if(isblock) 
					{
						GL11.glTranslatef(-4f/16f, 0, 2f/16f);
					}
					else
					{
						GL11.glTranslatef(-4f/16f/2f, -8f/16/2f, 1f/10000f);
					}
					if(AZF) GL11.glScalef(1f + 1f/10000f, 1f + 1f/10000f, 1f + 1f/10000f);
					this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}
			if(fp >= (percent/2f) && sl > 3) // >2/4 of a stack
			{
				GL11.glPushMatrix();
				{
					if(isblock) 
					{
						GL11.glTranslatef(4f/16f, 0, 0);
					}
					else
					{
						GL11.glTranslatef(0f, -3f/16/2f, -1f/10000f);
						GL11.glRotatef(-45, 0, 0, 1);
					}
					if(AZF) GL11.glScalef(1f + 1f/10000f, 1f + 1f/10000f, 1f + 1f/1000f);
					this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}
			if(fp >= (percent/4f*3f) && sl > 4) // >3/4 of a stack
			{
				GL11.glPushMatrix();
				{
					if(isblock) 
					{
						GL11.glTranslatef(0, 0, 4f/16f);
					}
					else
					{
						GL11.glTranslatef(1f/16f, 0, -1f/16f/2f);
						GL11.glRotatef(90, 0, 0, 1);
					}
					if(AZF) GL11.glScalef(1f + 1f/10000f, 1f + 1f/10000f, 1f + 1f/10000f);
					this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}
			if(fp >= (percent/4f*4f) && sl > 4) // >3/4 of a stack
			{
				GL11.glPushMatrix();
				{
					if(isblock) 
					{
						GL11.glTranslatef(0, 0, 4f/16f);
					}
					else
					{
						GL11.glTranslatef(-2f/16f, 4f/16f/2f, -2.1f/16f/2f);
						GL11.glRotatef(-30, 0, 0,1);
						GL11.glRotatef(15, 1, 1, 0);
					}
					if(AZF) GL11.glScalef(1f + 1f/10000f, 1f + 1f/10000f, 1f + 1f/10000f);
					this.itemRenderer.doRender(ie, 0, 0, 0, 0, 0);
				}
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();
	}


	@Override
	public boolean handleRenderType(ItemStack is, ItemRenderType arg1)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack is, Object... arg2)
	{
		switch(is.getItemDamage())
		{
			case 0:
				GL11.glPushMatrix();
				{
					if(Block.blocksList[is.getItem().itemID] != null && Block.blocksList[is.getItem().itemID].blockMaterial == Material.rock)
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
							GL11.glTranslatef(0, 0.5f, 0);
							GL11.glRotatef(90, 0, -1, 0);
							GL11.glRotatef(180, 0, 0, 1);
						}
						
						if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
						{
							GL11.glTranslatef(0.5f, 1f, 0.5f);
							GL11.glRotatef(90, 0, -1, 0);
							GL11.glRotatef(180, 0, 0, 1);
						}
						
						
						RendererUnguiFurnace.model.render();
					}
					GL11.glPopMatrix();
				}
				
				if(Block.blocksList[is.getItem().itemID] != null && Block.blocksList[is.getItem().itemID].blockMaterial == Material.wood)
				{
					GL11.glPushMatrix();
					{
						bindTexture(new ResourceLocation("ungui:textures/UnguiWorkbench.png"));
						if(type == ItemRenderType.INVENTORY) 
						{
							GL11.glRotatef(180, 0, 0, 1);
							GL11.glTranslatef(0, -0.5f, 0);
							GL11.glRotatef(90, 0, 1, 0);
						}
						
						if(type == ItemRenderType.ENTITY)
						{
							GL11.glTranslatef(0, 0.5f, 0);
							GL11.glRotatef(90, 0, -1, 0);
							GL11.glRotatef(180, 0, 0, 1);
						}
						
						if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
						{
							GL11.glTranslatef(0.5f, 1f, 0.5f);
							GL11.glRotatef(90, 0, -1, 0);
							GL11.glRotatef(180, 0, 0, 1);
						}
						
						
						RenderUnguiWorkbench.model.render(new float[] {0, 0, 0, 0, 0, 0, 0, 0});
					}
					GL11.glPopMatrix();
				}
				break;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2)
	{
		return true;
	}
}
