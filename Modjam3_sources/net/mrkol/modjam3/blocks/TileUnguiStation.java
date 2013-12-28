package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.*;


public class TileUnguiStation extends TileEntity
{
	
	public boolean onActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		int cid = -1;
		
		for(int i = 0; i < this.getCuboids().size(); i++)
		{
			if(this.getCuboids().get(i).equals(Raytracer.m_cuboid)) 
			{
				cid = i;
				break;
			}
		}
			
		if(cid != -1) return this.onCuboidActivated(cid, player);
		
		return Raytracer.m_cuboid != null;
	}
	
	public boolean onCuboidActivated(int cuboidID, EntityPlayer player)
	{
		return false;
	}
	
	public List<Cuboid6f> getCuboids()
	{
		return null;
	}
	
	protected  static boolean canStacksMerge(ItemStack is1, ItemStack is2)
	{
		if(is1 == null && is2 == null) return false;
		if(is1 == null && is2 != null) return true;
		if(is1 != null && is2 == null) return true;
		if(is1.stackSize >= is1.getItem().getItemStackLimit(is1)) return false;
		if(is1.getItem() != is2.getItem()) return false;
		if(is1.getItemDamage() != is2.getItemDamage()) return false;
		if(is1.stackTagCompound != null && is2.stackTagCompound != null && !is1.stackTagCompound.equals(is2.stackTagCompound)) return false;
		return true;
	}
	
	protected static ItemStack[] mergeItemStacks(ItemStack is1, ItemStack is2)
	{
		if(canStacksMerge(is1, is2))
		{
			if(is1 != null && is2 != null)
			{
				int i = Math.min(is1.getItem().getItemStackLimit(is1) - is1.stackSize, is2.stackSize);
				is1.stackSize += i;
				is2.stackSize -= i;
				if(is2.stackSize <= 0) is2 = null;
			}
			if(is1 == null && is2 != null) return new ItemStack[] {is2, is1};
		}
		
		return new ItemStack[] {is1, is2};
	}

	public void onBreakBlock()
	{
		
	}

	public void onBlockPlaced(EntityLivingBase elb, ItemStack is)
	{
		
	}
}
