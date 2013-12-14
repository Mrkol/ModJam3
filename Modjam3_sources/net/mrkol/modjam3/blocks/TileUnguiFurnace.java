package net.mrkol.modjam3.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.Cuboid6f;

public class TileUnguiFurnace extends TileUnguiStation
{
	public ItemStack fuel = new ItemStack(Item.stick);
	public byte rotation = 0;
	
	
	public TileUnguiFurnace()
	{
		float factor = 1f/16f;
		this.cuboids.add(new Cuboid6f(factor*8, factor*8, factor*8, factor*4, factor*8, factor*8));
		this.cuboids.add(new Cuboid6f(0,0, 0, factor*16, factor*8, factor*8));
	}
	
	@Override
	public void updateEntity()
	{
		
	}
}
