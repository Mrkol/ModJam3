package net.mrkol.modjam3.items;

import java.util.List;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.blocks.TileUnguiWorkbench;

public class ItemDebug extends Item 
{

	public ItemDebug(int par1) 
	{
		super(par1);
		this.setTextureName("minecraft:fishing_rod_cast");
	}
	
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    	{
    		par3List.add("\u00A7oPress the \u00A7a<SNEAK KEY>\u00A77\u00A7o for more info.");
    	}
    	else
    	{
    		par3List.add("\u00A7oHow did you even manage to get this thing?");
    	}
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
    		if(te instanceof TileUnguiWorkbench)
    		{
    			TileUnguiWorkbench tuw = (TileUnguiWorkbench)te;
    			p.addChatMessage("ROT: " + tuw.rotation);
    		}
    	}
    	
        return false;
    }
}
