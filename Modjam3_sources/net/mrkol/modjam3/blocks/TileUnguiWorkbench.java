package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.mrkol.modjam3.raytracing.Cuboid6f;

public class TileUnguiWorkbench extends TileUnguiStation
{
	public float buttons[] = new float[8];
	public ItemStack items[] = new ItemStack[9];
	private EntityPlayer lastCraftingPlayer = null;
	public int rotation = 0;
	
	@Override
	public void updateEntity()
	{
		for(int i = 0; i < this.buttons.length; i++)
		{
			if(this.buttons[i] > 0 && this.buttons[i] < 1)
			{
				this.buttons[i] += 0.05f;
			}
			
			if(this.buttons[i] >= 1)
			{
				this.buttons[i] = 0;
			}

			if(this.buttons[i] > 0.46f && this.buttons[i] < 0.54f)
			{
				boolean b = true;
				for(int k = 0; k < this.buttons.length; k++)
				{
					if(k != i && this.buttons[k] != 0) b = false; 
				}
				
				if(!b) return;
				
				ItemStack is = getCraftingResult(this.items, worldObj);
				if(is != null) 
				{
			        for (int l = 0; l < this.items.length; ++l)
			        {
			            ItemStack itemstack1 = this.items[l];

			            if (itemstack1 != null)
			            {
			                this.items[l].stackSize--;
			                if(this.items[l].stackSize <= 0)
			                {
			                	this.items[l] = null;
			                }


			                if (itemstack1.getItem().hasContainerItem())
			                {
			                    ItemStack itemstack2 = itemstack1.getItem().getContainerItemStack(itemstack1);

			                    if (itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage())
			                    {
			                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(lastCraftingPlayer , itemstack2));
			                        itemstack2 = null;
			                    }

			                    if (itemstack2 != null && (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.lastCraftingPlayer .inventory.addItemStackToInventory(itemstack2)))
			                    {
			                        if (this.items[l] == null)
			                        {
			                            this.items[l] = itemstack2;
			                        }
			                        else
			                        {
			                            this.lastCraftingPlayer.dropPlayerItem(itemstack2);
			                        }
			                    }
			                }
		    				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			            }
			        }
			        
			        if( !worldObj.isRemote)
			        {
						EntityItem ei = new EntityItem(worldObj,this.xCoord + 0.5f, this.yCoord + 1f + 1f/4f/2f, this.zCoord + 0.5f, is);
						ei.setVelocity(0, 0, 0);
						worldObj.spawnEntityInWorld(ei);
			        }
				}
			}
		}
	}
	
	public static ItemStack getCraftingResult(ItemStack[] iss, World w)
	{

        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < iss.length; ++j)
        {
            ItemStack itemstack2 = iss[j];

            if (itemstack2 != null)
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.itemID == itemstack1.itemID && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && Item.itemsList[itemstack.itemID].isRepairable())
        {
            Item item = Item.itemsList[itemstack.itemID];
            int k = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int l = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int i1 = k + l + item.getMaxDamage() * 5 / 100;
            int j1 = item.getMaxDamage() - i1;

            if (j1 < 0)
            {
                j1 = 0;
            }

            return new ItemStack(itemstack.itemID, 1, j1);
        }
        else
        {
            for (j = 0; j < CraftingManager.getInstance().getRecipeList().size(); ++j)
            {
                IRecipe irecipe = (IRecipe)CraftingManager.getInstance().getRecipeList().get(j);

                InventoryCrafting ic = new InventoryCrafting(new Container() {
					@Override
					public boolean canInteractWith(EntityPlayer entityplayer)
					{
						return false;
					}}, 3, 3);
                
                for(int k = 0; k < iss.length; k++)
                {
                	ic.setInventorySlotContents(k, iss[k]);
                }
                
                if (irecipe.matches(ic, w))
                {
                	ItemStack is = irecipe.getCraftingResult(ic);
                    
                    return is;
                }
            }

            return null;
        }
	}

    @Override
    public void onBreakBlock()
    {
    	ItemStack itemstack = null;
    	for(int i = 0; i < this.items.length; i++)
    	{
    		itemstack = this.items[i];
    		
    		
            if (itemstack != null)
            {
                float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem;

                for (float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; worldObj.spawnEntityInWorld(entityitem))
                {
                    int k1 = worldObj.rand.nextInt(21) + 10;

                    if (k1 > itemstack.stackSize)
                    {
                        k1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= k1;
                    entityitem = new EntityItem(worldObj, (double)(this.xCoord + f), (double)(this.yCoord + f1), (double)(this.zCoord + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (double)((float)worldObj.rand.nextGaussian() * f3);
                    entityitem.motionY = (double)((float)worldObj.rand.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double)((float)worldObj.rand.nextGaussian() * f3);

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                    
                    worldObj.spawnEntityInWorld(entityitem);
                }
            }
        }
    }
    
    @Override
    public void onBlockPlaced(EntityLivingBase elb, ItemStack is)
    {
		 int i = MathHelper.floor_double(elb.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		this.rotation = (byte)i;
    }
    
	public boolean onCuboidActivated(int cuboidID, EntityPlayer player)
	{
		boolean b = true;
		switch(cuboidID)
		{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				if(player.inventory.getStackInSlot(player.inventory.currentItem) != null)
				{
					if(items[cuboidID] == null)
					{
						items[cuboidID] = player.inventory.getCurrentItem().copy();
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						player.inventory.onInventoryChanged();
					}
				}
				else
				{
					if(items[cuboidID] != null)
					{
						player.inventory.addItemStackToInventory(items[cuboidID].copy());
						items[cuboidID] = null;
						
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						player.inventory.onInventoryChanged();
					}
					else
					{
						b = false;
					}
				}
				break;

			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
				if(this.buttons[cuboidID - 9] == 0) 
				{
					this.buttons[cuboidID - 9] += 0.05f;
				}
				else
				{
					b = false;
				}
				this.lastCraftingPlayer = player;
				break;
				
			default:
				b = false;
				break;
		}
		return b;
	}
	
	@Override
	public List<Cuboid6f> getCuboids()
	{
		List<Cuboid6f> cuboids = new ArrayList<Cuboid6f>();
		
		cuboids.add(new Cuboid6f(1f/16f, 15f/16f, 1f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 15f/16f, 1f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 15f/16f, 1f/16f, 4f/16f, 1f/16f, 4f/16f));

		cuboids.add(new Cuboid6f(1f/16f, 15f/16f, 6f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 15f/16f, 6f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 15f/16f, 6f/16f, 4f/16f, 1f/16f, 4f/16f));

		cuboids.add(new Cuboid6f(1f/16f, 15f/16f, 11f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 15f/16f, 11f/16f, 4f/16f, 1f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 15f/16f, 11f/16f, 4f/16f, 1f/16f, 4f/16f));

		
		cuboids.add(new Cuboid6f(15f/16f - buttons[2], 13f/16f, 2f/16f, 1f/16f, 1f/16f, 2f/16f));
		cuboids.add(new Cuboid6f(15f/16f - buttons[3], 13f/16f, 12f/16f, 1f/16f, 1f/16f, 2f/16f));
		cuboids.add(new Cuboid6f(0 -buttons[0], 13f/16f, 2f/16f, 1f/16f, 1/16f, 2f/16f));
		cuboids.add(new Cuboid6f(0 -buttons[1], 13f/16f, 12f/16f, 1f/16f, 1f/16f, 2f/16f));

		cuboids.add(new Cuboid6f(12f/16f, 13f/16f, 0 -  -buttons[5], 2f/16f, 1f/16f, 1f/16f));
		cuboids.add(new Cuboid6f(2f/16f, 13f/16f, 0 -buttons[4], 2f/16f , 1/16f, 1f/16f));
		cuboids.add(new Cuboid6f(2f/16f, 13f/16f, 15f/16f - buttons[6], 2f/16f, 1f/16f, 1f/16f));
		cuboids.add(new Cuboid6f(12f/16f, 13/16f, 15f/16f - buttons[7], 2f/16f, 1f/16f, 1f/16f));
		
		cuboids = rotateCuboids(cuboids, this.rotation);
		
		cuboids.add(new Cuboid6f(1f/16f, 0, 1f/16f, 14f/16f, 15.9f/16f, 14f/16f));
		
		return cuboids;
	}
	
	
	private static List<Cuboid6f> rotateCuboids(List<Cuboid6f> l, int r)
	{
		int i = r % 4;
		for(int j = 0; j < l.size(); j++)
		{
			switch(i)
			{
				case 0:
					double minx0 = 1f - l.get(j).max.xCoord;
					double minz0 = 1f - l.get(j).max.zCoord;
					double maxx0 = 1f - l.get(j).min.xCoord;
					double maxz0 = 1f - l.get(j).min.zCoord;
					l.get(j).min.xCoord = minx0;
					l.get(j).min.zCoord = minz0;
					l.get(j).max.xCoord = maxx0;
					l.get(j).max.zCoord = maxz0;
					break;
				
				case 1:
					double minx1 = l.get(j).min.zCoord;
					double minz1 = 1f - l.get(j).max.xCoord;
					double maxx1 = l.get(j).max.zCoord;
					double maxz1 = 1f - l.get(j).min.xCoord;
					l.get(j).min.xCoord = minx1;
					l.get(j).min.zCoord = minz1;
					l.get(j).max.xCoord = maxx1;
					l.get(j).max.zCoord = maxz1;
					break;
					
				case 2:
					
					break;
					
				case 3:
					double minx3 = 1f - l.get(j).max.zCoord;
					double minz3 = l.get(j).min.xCoord;
					double maxx3 = 1f - l.get(j).min.zCoord;
					double maxz3 = l.get(j).max.xCoord;
					l.get(j).min.xCoord = minx3;
					l.get(j).min.zCoord = minz3;
					l.get(j).max.xCoord = maxx3;
					l.get(j).max.zCoord = maxz3;
					break;
			}
		}
		return l;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	
    	
    	
    	this.rotation = par1NBTTagCompound.getInteger("rotation");
    	
        NBTTagList items = new NBTTagList();
        items = par1NBTTagCompound.getTagList("items");

        NBTTagList itemslist = par1NBTTagCompound.getTagList("items");

        for (int i = 0; i < itemslist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)itemslist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.items.length)
            {
                this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1).copy();
            }
        }
        
        
    }

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    	
    	
    	par1NBTTagCompound.setInteger("rotation", rotation);
    	
        NBTTagList itemslist = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.items[i].writeToNBT(nbttagcompound1);
                itemslist.appendTag(nbttagcompound1);
            }
        }
        
        par1NBTTagCompound.setTag("items", itemslist);
        
    }
    
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
    }
    
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
    {
        readFromNBT(pkt.data);
    }
}
