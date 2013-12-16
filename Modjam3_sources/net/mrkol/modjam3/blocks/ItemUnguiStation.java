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
	public ItemUnguiStation(int arg0)
	{
		super(arg0);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
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
    		par3List.add("\u00A7oYou managed to put some");
    		par3List.add("\u00A7ocobble in a furnace shape using");
    		par3List.add("\u00A7oyour crafting table, and now");
    		par3List.add("\u00A7oyou can use your brand new \u00A7r\u00A77\u00A7lFURNACE\u00A77\u00A7o");
    		par3List.add("\u00A7oto cook things. Fuel goes into the bottom part,");
    		par3List.add("\u00A7ogoods go into the top, and BAM! Your goods are");
    		par3List.add("\u00A7oall cooked. Now, if only you could find a way");
    		par3List.add("\u00A7oignite the fuel... Maybe some \u00A7r\u00A77\u00A7lflint\u00A77\u00A7os? Or rubbing");
    		par3List.add("\u00A7r\u00A77\u00A7lstick\u00A77\u00A7os? Or booth?");
    	}
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
