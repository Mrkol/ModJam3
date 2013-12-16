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
    		par3List.add("§oPress the §a<SNEAK KEY>§7§o for more info.");
    	}
    	else
    	{
    		par3List.add("§oYou managed to put some");
    		par3List.add("§ocobble in a furnace shape using");
    		par3List.add("§oyour crafting table, and now");
    		par3List.add("§oyou can use your brand new §r§7§lFURNACE§7§o");
    		par3List.add("§oto cook things. Fuel goes into the bottom part,");
    		par3List.add("§ogoods go into the top, and BAM! Your goods are");
    		par3List.add("§oall cooked. Now, if only you could find a way");
    		par3List.add("§oignite the fuel... Maybe some §r§7§lflint§7§os? Or rubbing");
    		par3List.add("§r§7§lstick§7§os? Or booth?");
    	}
    }
    
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
