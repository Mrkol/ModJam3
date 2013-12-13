package net.mrkol.modjam3.blocks;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
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

}
