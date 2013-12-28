package net.mrkol.modjam3.blocks;

import java.util.List;
import org.lwjgl.input.Keyboard;
import net.minecraft.block.BlockColored;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemUnguiStation extends ItemBlock
{
	public ItemUnguiStation(int id)
	{
		super(id);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
    
    public static class ItemUnguiStation_0 extends ItemUnguiStation
    {

		public ItemUnguiStation_0(int id)
		{
			super(id);
		}

		
		@SuppressWarnings({
				"rawtypes", "unchecked"
		})
		@Override
		public void getSubItems(int i, CreativeTabs ct, List l)
		{
			l.add(new ItemStack(this, 1, 0));
		}
		
		
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	    {
	    	if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
	    	{
	    		par3List.add("\u00A7oPress the \u00A7a<SNEAK KEY>\u00A77\u00A7o for more info.");
	    	}
	    	else
	    	{
	    		switch(par1ItemStack.getItemDamage())
	    		{
	    			case 0:
			    		par3List.add("\u00A7oYou managed to put some");
			    		par3List.add("\u00A7ocobble in a furnace shape using");
			    		par3List.add("\u00A7oyour crafting table, and now");
			    		par3List.add("\u00A7oyou can use your brand new \u00A7r\u00A77\u00A7lFURNACE\u00A77\u00A7o");
			    		par3List.add("\u00A7oto cook things. Fuel goes into the bottom part,");
			    		par3List.add("\u00A7ogoods go into the top, and BAM! Your goods are");
			    		par3List.add("\u00A7oall cooked. Now, if only you could find a way");
			    		par3List.add("\u00A7oignite the fuel... Maybe some \u00A7r\u00A77\u00A7lflint\u00A77\u00A7os? Or rubbing");
			    		par3List.add("\u00A7r\u00A77\u00A7lstick\u00A77\u00A7os? Or booth?");
	    				break;
	    		}
	    	}
	    }
    }
    
    public static class ItemUnguiStation_1 extends ItemUnguiStation
    {

		public ItemUnguiStation_1(int id)
		{
			super(id);
		}

		
		@SuppressWarnings({
				"rawtypes", "unchecked"
		})
		@Override
		public void getSubItems(int i, CreativeTabs ct, List l)
		{
			l.add(new ItemStack(this, 1, 0));
		}
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	    {
	    	if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
	    	{
	    		par3List.add("\u00A7oPress the \u00A7a<SNEAK KEY>\u00A77\u00A7o for more info.");
	    	}
	    	else
	    	{
	    		switch(par1ItemStack.getItemDamage())
	    		{
	    			case 0:
			    		par3List.add("\u00A7oBy using some wooden planks");
			    		par3List.add("\u00A7oyou've crafted a neat-looking table!");
			    		par3List.add("\u00A7oThis could be useful for \u00A7r\u00A77\u00A7lcrafting\u00A77\u00A7o");
			    		par3List.add("\u00A7oand manufacturing!");
			    		par3List.add("\u00A7oAlso, you made some buttons on");
			    		par3List.add("\u00A7othe the siddes for no exact purpose.");
			    		par3List.add("\u00A7oYou just like to press them when working on");
			    		par3List.add("\u00A7othings and pretend like it's all automatic and");
			    		par3List.add("\u00A7omechanic. Oh, silly you.");
	    				break;
	    		}
	    	}
	    }
	    
	    
    }
}
