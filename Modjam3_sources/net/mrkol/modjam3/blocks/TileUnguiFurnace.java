package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.Cuboid6f;

public class TileUnguiFurnace extends TileUnguiStation
{
	public ItemStack fuel = new ItemStack(Item.stick);
	public byte rotation = -1;
	private float factor = 1f/16f;

	public List<Cuboid6f> getCuboids()
	{
		List<Cuboid6f> cuboids = new ArrayList<Cuboid6f>();
		switch(this.rotation)
		{
			case 0:
				cuboids.add(new Cuboid6f(0, 0, factor*0.01f, 1, 1, factor*15.99f));
				cuboids.add(new Cuboid6f(factor * 2, 0,  0, factor*12, factor*7, factor*1));
				break;
				
			case 1:
				cuboids.add(new Cuboid6f(0, 0, 0, factor*15.99f, 1, 1));
				cuboids.add(new Cuboid6f(15f/16f, 0,  factor * 2, factor*1, factor*7, factor*12));
				break;
				
			case 2:
				cuboids.add(new Cuboid6f(0, 0, 0, 1, 1, factor*15.99f));
				cuboids.add(new Cuboid6f(factor * 2, 0,  15f/16f, factor*12, factor*7, factor*1));
				break;
				
			case 3:
				cuboids.add(new Cuboid6f(factor*0.01f, 0, 0, factor*15.99f, 1, 1));
				cuboids.add(new Cuboid6f(0, 0,  factor * 2, factor*1, factor*7, factor*12));
				break;
		}
		return cuboids;
	}
}
