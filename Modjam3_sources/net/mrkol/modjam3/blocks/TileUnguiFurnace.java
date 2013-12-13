package net.mrkol.modjam3.blocks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileUnguiFurnace extends TileEntity
{
	public ItemStack fuel = new ItemStack(Item.stick);
	public byte rotation = 0;
	
	
	public TileUnguiFurnace()
	{
		
	}
	
	@Override
	public void updateEntity()
	{
		
	}
}
