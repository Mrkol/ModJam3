package net.mrkol.modjam3.items;

import cpw.mods.fml.common.modloader.ModLoaderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;

public class ItemDebug extends Item 
{

	public ItemDebug(int par1) 
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setTextureName("minecraft:textures/items:diamond");
	}

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer p, World w, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
    	TileEntity te = w.getBlockTileEntity(x, y, z);
    	
    	if(te != null) 
    	{
    		if(w.isRemote) p.addChatMessage("CLIENT:\nX: " + te.xCoord + "\nY: " + te.yCoord + "\nZ: " + te.zCoord);
    		else p.addChatMessage("SERVER:\nX: " + te.xCoord + "\nY: " + te.yCoord + "\nZ: " + te.zCoord);
    		if(te instanceof TileUnguiFurnace)
    		{
    			TileUnguiFurnace tuf = (TileUnguiFurnace)te;
    			p.addChatMessage("ROT: " + tuf.rotation);
    			p.addChatMessage("fuelTimer: " + tuf.fuelTimer);
    			p.addChatMessage("fuel: " + tuf.fuel);
    			p.addChatMessage("fueling: " + tuf.fueling);
    			p.addChatMessage("heat: " + tuf.heatLevel);
    			p.addChatMessage("smelting: " + tuf.smeltProgress);
    		}
    	}
    	
        return false;
    }
}
