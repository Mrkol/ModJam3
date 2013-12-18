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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.mrkol.modjam3.Ungui;
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
	public boolean isBurning = false;

	public static final float SMELT_TIME = 250;
	public static final float BURN_TIME  = 1000;
	
    public void updateEntity()
    {
    	if(this.heatLevel > 0.1)
    	{
        	float x0 = ((float)this.worldObj.rand.nextInt(7) - 4f) / 16f;
        	float y0 = ((float)this.worldObj.rand.nextInt(3) - 2f) / 16f;
        	float h = (float) Math.sqrt(10  / this.heatLevel * 2) * 5;
        	float a = this.heatLevel * 10;
        	
        	
			switch(this.rotation)
			{
				case 0:
					for(int i = 0; i < a; i++) this.worldObj.spawnParticle("smoke", this.xCoord + 0.5f + x0, this.yCoord +3f/16f + y0, this.zCoord + 0.5f,  0 + ((worldObj.rand.nextFloat() - 0.5f) / 20), -0.01f, -0.05f - ((worldObj.rand.nextFloat() - 0.5f) / h));
					break;
				case 1:
					for(int i = 0; i < a; i++) this.worldObj.spawnParticle("smoke", this.xCoord + 0.5f, this.yCoord +3f/16f + y0, this.zCoord + 0.5f +x0,  0.05f + ((worldObj.rand.nextFloat() - 0.5f) / h), -0.01f, 0 + ((worldObj.rand.nextFloat() - 0.5f) / 20));
					break;
				case 2:
					for(int i = 0; i < a; i++) this.worldObj.spawnParticle("smoke", this.xCoord + 0.5f + x0, this.yCoord +3f/16f + y0, this.zCoord + 0.5f,  0 + ((worldObj.rand.nextFloat() - 0.5f) / 20), -0.01f, 0.05f + ((worldObj.rand.nextFloat() - 0.5f) / h));
					break;
				case 3:
					for(int i = 0; i < a; i++) this.worldObj.spawnParticle("smoke", this.xCoord + 0.5f, this.yCoord +3f/16f + y0, this.zCoord + 0.5f + x0,  -0.05f - ((worldObj.rand.nextFloat() - 0.5f) / h), -0.01f, 0 + ((worldObj.rand.nextFloat() - 0.5f) / 20));
					break;
			}
    	}
    	
    	if(this.fueling != null && this.heatLevel > 0.1f)
    	{
        	float x0 = ((float)this.worldObj.rand.nextInt(9) - 5f) / 16f;
        	float z0 = ((float)this.worldObj.rand.nextInt(9) - 5f) / 16f;
        	float y0 = ((float)this.worldObj.rand.nextInt(2) - 1.5f) / 16f;
			if((((int)this.heatLevel) * 10) % 2 == 0) this.worldObj.spawnParticle("flame", this.xCoord + 0.5f + x0, this.yCoord +4f/16f + y0, this.zCoord + 0.5f + z0,  0, 0, 0);
    	}
    	
    	
    	float fuelFactor = 160f;
    	
    	//coal burns for 100 seconds, and generates energy for 8 items, 1 item cooks for 25 seconds
    	this.heatLevel += (TileEntityFurnace.getItemBurnTime(fueling) / fuelFactor) / BURN_TIME * 8; 
    	if(this.heatLevel > 0) this.heatLevel -= this.heatLevel / (SMELT_TIME) * (TileEntityFurnace.getItemBurnTime(this.fueling) != 0 ? Math.sqrt(TileEntityFurnace.getItemBurnTime(this.fueling) / fuelFactor) * Math.PI : 1); //case FUCK YOU, that's why.
    	if(this.heatLevel < 0) this.heatLevel = 0;
    	if(this.heatLevel < 1f/1000f) this.heatLevel = 0;
    	if(this.fuelTimer == 0)
    	{
    		this.fueling = null;
    		if(this.fuel != null && this.isBurning)
    		{
	    		this.fueling = this.fuel.splitStack(1);
	    		if(this.fuel.stackSize == 0) this.fuel = null;
	    		this.fuelTimer = BURN_TIME;
    		}
    		else
    		{
    			this.isBurning = false;
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

	public boolean onCuboidActivated(int cuboidID, EntityPlayer player)
	{
		boolean b = true;
		ItemStack is = player.inventory.getCurrentItem();
		switch(cuboidID)
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
					if(is.getItem() == Item.flintAndSteel || is.getItem() == Ungui.proxy.itemFlintAndStick)
					{
						int c = 0;
						if(is.getItem() == Ungui.proxy.itemFlintAndStick) c = 3;
						if(c == 0 || worldObj.rand.nextInt(c) == 0) this.isBurning = true;
						is.damageItem(1, player);
					}
					
					if(is.getItem() == Item.bucketWater)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Item.bucketEmpty, 1, 0));
						
						this.heatLevel -=5;
						this.isBurning = false;
						this.fueling = null;
						this.fuelTimer = 0;
					}
					
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
    	this.smeltProgress = par1NBTTagCompound.getFloat("smelting");
    	this.fuelTimer = par1NBTTagCompound.getFloat("fuel");
    	this.heatLevel = par1NBTTagCompound.getFloat("heat");
    	this.isBurning = par1NBTTagCompound.getBoolean("burning");
    	
        NBTTagCompound nbttc0 = new NBTTagCompound();
        NBTTagCompound nbttc1 = new NBTTagCompound();
        NBTTagCompound nbttc2 = new NBTTagCompound();
        NBTTagCompound nbttc3 = new NBTTagCompound();

        nbttc0 = (NBTTagCompound) par1NBTTagCompound.getTag("i_smelting_in");
        nbttc1 = (NBTTagCompound) par1NBTTagCompound.getTag("i_smelting_out");
        nbttc2 = (NBTTagCompound) par1NBTTagCompound.getTag("i_fuel");
        nbttc3 = (NBTTagCompound) par1NBTTagCompound.getTag("i_fueling");

        if(nbttc0 != null)
        {
        	this.smeltingIn = ItemStack.loadItemStackFromNBT(nbttc0);
        }
        if(nbttc1 != null) 
    	{
    		this.smeltingOut = ItemStack.loadItemStackFromNBT(nbttc1);
    	}
        if(nbttc2 != null) 
        {
        	this.fuel = ItemStack.loadItemStackFromNBT(nbttc2);
        }
        if(nbttc3 != null) 
        {
        	this.fueling = ItemStack.loadItemStackFromNBT(nbttc3);
        }
        
    	super.readFromNBT(par1NBTTagCompound);
    }

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	par1NBTTagCompound.setInteger("rotation", rotation);
    	par1NBTTagCompound.setFloat("smelting", this.smeltProgress);
    	par1NBTTagCompound.setFloat("fuel", this.fuelTimer);
    	par1NBTTagCompound.setFloat("heat", this.heatLevel);
    	par1NBTTagCompound.setBoolean("burning", this.isBurning);
    	
        
        NBTTagCompound nbttc0 = new NBTTagCompound();
        if(this.smeltingIn != null)
		{
        	this.smeltingIn.writeToNBT(nbttc0);
		}
        par1NBTTagCompound.setTag("i_smelting_in", nbttc0);
        
    	NBTTagCompound nbttc1 = new NBTTagCompound();
        if(this.smeltingOut != null) 
		{
        	this.smeltingOut.writeToNBT(nbttc1);
		}
    	par1NBTTagCompound.setTag("i_smelting_out", nbttc1);
        	
        NBTTagCompound nbttc2 = new NBTTagCompound();
        if(this.fuel != null)
		{ 
        	this.fuel.writeToNBT(nbttc2);
		}
        par1NBTTagCompound.setTag("i_fuel", nbttc2);
        
        NBTTagCompound nbttc3 = new NBTTagCompound();
        if(this.fueling != null)
		{ 
        	this.fueling.writeToNBT(nbttc3);
		}
        par1NBTTagCompound.setTag("i_fueling", nbttc3);
                
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
