package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.mrkol.modjam3.raytracing.Cuboid6f;

public class TileUnguiWorkbench extends TileUnguiStation
{
	public float buttons[] = new float[8];
	public ItemStack items[] = new ItemStack[9];
	private EntityPlayer lastCraftingPlayer = null;
	
	@Override
	public void updateEntity()
	{
		for(int i = 0; i < this.buttons.length; i++)
		{
			if(this.buttons[i] > 0f && this.buttons[i] < 1f)
			{
				this.buttons[i] += 0.05f;
			}
			if(!worldObj.isRemote && i == 7 && this.lastCraftingPlayer != null) this.lastCraftingPlayer.addChatMessage("" + this.buttons[i]);
			if(this.buttons[i] > 1)
			{
				this.buttons[i] = 0;
			}

			if(this.buttons[i] > 0.46f && this.buttons[i] < 0.54f && !worldObj.isRemote)
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
			                if(this.items[l].stackSize <= 0) this.items[l] = null;

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
			                            this.lastCraftingPlayer .dropPlayerItem(itemstack2);
			                        }
			                    }
			                }
			            }
			        }
			        
			        
					EntityItem ei = new EntityItem(worldObj,this.xCoord + 0.5f, this.yCoord + 1f + 1f/4f/2f, this.zCoord + 0.5f, is);
					ei.setVelocity(0, 0, 0);
					worldObj.spawnEntityInWorld(ei);
					
					
					
					this.onInventoryChanged();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param iss
	 * @param w
	 * @return
	 * itemstack[0][0] is your crafting result
	 * itemstack[1] is the crafting matrix
	 */
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
					}
				}
				else
				{
					if(items[cuboidID] != null)
					{
						player.inventory.addItemStackToInventory(items[cuboidID].copy());
						items[cuboidID] = null;
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
				this.lastCraftingPlayer = player;
				if(this.buttons[cuboidID - 9] == 0) 
				{
					this.buttons[cuboidID - 9] += 0.05f;
				}
				else
				{
					b = false;
				}
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
		
		cuboids.add(new Cuboid6f(1f/16f, 1f, 1f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 1f, 1f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 1f, 1f/16f, 4f/16f, 4f/16f, 4f/16f));

		cuboids.add(new Cuboid6f(1f/16f, 1f, 6f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 1f, 6f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 1f, 6f/16f, 4f/16f, 4f/16f, 4f/16f));

		cuboids.add(new Cuboid6f(1f/16f, 1f, 11f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(6f/16f, 1f, 11f/16f, 4f/16f, 4f/16f, 4f/16f));
		cuboids.add(new Cuboid6f(11f/16f, 1f, 11f/16f, 4f/16f, 4f/16f, 4f/16f));

		
		
		cuboids.add(new Cuboid6f(15f/16f, 13f/16f, 2f/16f, (1f - buttons[2])/16f, 1f/16f, 2f/16f));
		cuboids.add(new Cuboid6f(15f/16f, 13f/16f, 12f/16f, (1f - buttons[3])/16f, 1f/16f, 2f/16f));
		cuboids.add(new Cuboid6f(0, 13f/16f, 2f/16f, (1f -buttons[0])/16f, 1/16f, 2f/16f));
		cuboids.add(new Cuboid6f(0, 13f/16f, 12f/16f, (1f -buttons[1])/16f, 1f/16f, 2f/16f));

		cuboids.add(new Cuboid6f(12f/16f, 13f/16f, 0, 2f/16f, 1f/16f, (1f -buttons[5])/16f));
		cuboids.add(new Cuboid6f(2f/16f, 13f/16f, 0, 2f/16f , 1/16f, (1f -buttons[4])/16f));
		cuboids.add(new Cuboid6f(2f/16f, 13f/16f, 15f/16f, 2f/16f, 1f/16f, (1f - buttons[6])/16f));
		cuboids.add(new Cuboid6f(12f/16f, 13/16f, 15f/16f, 2f/16f, 1f/16f, (1f - buttons[7])/16f));
		
		cuboids.add(new Cuboid6f(1f/16f, 0, 1f/16f, 14f/16f, 1, 14f/16f));
		
		return cuboids;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
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
        
        
    	super.readFromNBT(par1NBTTagCompound);
    }

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
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
        
    	super.writeToNBT(par1NBTTagCompound);
    }
    
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
    }
    
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
    {
        readFromNBT(pkt.data);
    }
}
