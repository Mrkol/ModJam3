package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.mrkol.modjam3.raytracing.Cuboid6f;
import net.mrkol.modjam3.raytracing.Raytracer;

public class TileUnguiFurnace extends TileUnguiStation
{
	public ItemStack fuel;
	public ItemStack smeltingIn;
	public ItemStack smeltingOut;
	public ItemStack fueling;
	public int rotation = 0;
	private float factor = 1f/16f;
	
	public float  fuelTimer = 0;
	public float heatLevel = 0;
	public float smeltProgress = 0; //goes from 0 to 1

	public static final float SMELT_TIME = 250;
	public static final float BURN_TIME  = 1000;
	
    public void updateEntity()
    { //coal burns for 100 seconds, and generates energy for 8 items, 1 item cooks for 25 seconds
    	this.heatLevel += (TileEntityFurnace.getItemBurnTime(fueling) / 1600f) / BURN_TIME * 8; 
    	if(this.heatLevel > 0) this.heatLevel -= this.heatLevel / (SMELT_TIME);
    	if(this.heatLevel < 0) this.heatLevel = 0;
    	if(this.heatLevel < 1f/1000f) this.heatLevel = 0;
    	if(this.fuelTimer == 0)
    	{
    		this.fueling = null;
    		if(this.fuel != null && FurnaceRecipes.smelting().getSmeltingResult(this.smeltingIn) != null)
    		{
	    		this.fueling = this.fuel.splitStack(1);
	    		if(this.fuel.stackSize == 0) this.fuel = null;
	    		this.fuelTimer = BURN_TIME;
    		}
    	}
    	else
    	{
    		this.fuelTimer--;
    	}
		if(FurnaceRecipes.smelting().getSmeltingResult(this.smeltingIn) != null && this.heatLevel > 1)
		{
	    	this.smeltProgress += heatLevel / SMELT_TIME;
	    	this.heatLevel -= heatLevel / (SMELT_TIME);
	    	if(this.smeltProgress >= 1)
	    	{
		    	this.smeltProgress--;
	    		if(this.smeltingIn != null && FurnaceRecipes.smelting().getSmeltingResult(this.smeltingIn) != null)
	    		{
		    		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.smeltingIn);
		            if (this.smeltingOut == null)
		            {
		                this.smeltingOut = itemstack.copy();
		            }
		            else if (this.smeltingOut.isItemEqual(itemstack))
		            {
		                this.smeltingOut.stackSize += itemstack.stackSize;
		            }
		
		            --this.smeltingIn.stackSize;
		
		            if (this.smeltingIn.stackSize <= 0)
		            {
		                this.smeltingIn = null;
		            }
	    		}
	    	}
		}
		
		if(FurnaceRecipes.smelting().getSmeltingResult(this.smeltingIn) == null || this.heatLevel == 0)
		{
			this.smeltProgress = 0;
		}
    }
    
	public boolean onActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		boolean b = super.onActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
		
		int cid = -1;
		
		for(int i = 0; i < this.getCuboids().size(); i++)
		{
			if(this.getCuboids().get(i).equals(Raytracer.m_cuboid)) 
			{
				cid = i;
				break;
			}
		}
		
		ItemStack is = player.inventory.getCurrentItem();
		switch(cid)
		{
			case 1:
				
				if(is == null)
				{
					if(this.fuel != null && this.heatLevel == 0)
					{
						player.inventory.addItemStackToInventory(this.fuel.copy());
						this.fuel = null;
					}
				}
				else
				{
					if(TileEntityFurnace.isItemFuel(player.inventory.getStackInSlot(player.inventory.currentItem)))
					{
						ItemStack[] iss = mergeItemStacks(this.fuel, player.inventory.getStackInSlot(player.inventory.currentItem));
						if(iss[0] != null) this.fuel = iss[0].copy();
						else this.fuel = null;
						if(iss[1] != null) player.inventory.setInventorySlotContents(player.inventory.currentItem, iss[1].copy());
						else player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
				}
				break;
				
			case 2:				
				if(is == null)
				{
					if(this.heatLevel < 1f/50f)
					{
						if(this.smeltingIn != null)
						{
							player.inventory.addItemStackToInventory(this.smeltingIn.copy());
							this.smeltingIn = null;
						}
						else
						if(this.smeltingOut != null)
						{
							player.inventory.addItemStackToInventory(this.smeltingOut.copy());
							this.smeltingOut = null;
						}
							
					}
					else
					{
						if(this.smeltingOut != null)
						{
							player.inventory.addItemStackToInventory(this.smeltingOut.copy());
							this.smeltingOut = null;
						}
					}
				}
				else
				{
					{
						ItemStack[] iss = mergeItemStacks(this.smeltingIn, player.inventory.getStackInSlot(player.inventory.currentItem));
						if(iss[0] != null) this.smeltingIn = iss[0].copy();
						else this.smeltingIn = null;
						if(iss[1] != null) player.inventory.setInventorySlotContents(player.inventory.currentItem, iss[1].copy());
						else player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
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
		switch(this.rotation)
		{
			case 0:
				cuboids.add(new Cuboid6f(0, 0, factor*0.01f, 1, 1, factor*15.99f));
				cuboids.add(new Cuboid6f(factor * 2, 0,  0, factor*12, factor*7, factor*1));
				cuboids.add(new Cuboid6f(factor * 3, factor *8 ,  0, factor*10, factor*5, factor*1));
				break;
				
			case 1:
				cuboids.add(new Cuboid6f(0, 0, 0, factor*15.99f, 1, 1));
				cuboids.add(new Cuboid6f(15f/16f, 0,  factor * 2, factor*1, factor*7, factor*12));
				cuboids.add(new Cuboid6f(15f/16f, factor*8,  factor * 3, factor*1, factor*5, factor*10));
				break;
				
			case 2:
				cuboids.add(new Cuboid6f(0, 0, 0, 1, 1, factor*15.99f));
				cuboids.add(new Cuboid6f(factor * 2, 0,  15f/16f, factor*12, factor*7, factor*1));
				cuboids.add(new Cuboid6f(factor * 3, factor * 8,  15f/16f, factor*10, factor*5, factor*1));
				break;
				
			case 3:
				cuboids.add(new Cuboid6f(factor*0.01f, 0, 0, factor*15.99f, 1, 1));
				cuboids.add(new Cuboid6f(0, 0,  factor * 2, factor*1, factor*7, factor*12));
				cuboids.add(new Cuboid6f(0, factor * 8,  factor * 3, factor*1, factor*5, factor*10));
				break;
		}
		return cuboids;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	this.rotation = par1NBTTagCompound.getInteger("rotation");
    	super.readFromNBT(par1NBTTagCompound);
    }

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	par1NBTTagCompound.setInteger("rotation", rotation);
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
