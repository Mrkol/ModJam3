package net.mrkol.modjam3;

import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.mrkol.modjam3.blocks.BlockUnguiStation;
import net.mrkol.modjam3.blocks.ItemUnguiStation;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.items.ItemDebug;
import net.mrkol.modjam3.items.ItemFlintAndStick;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ProxyCommonUngui
{
	public Block blockUnguiStation;
	public Item itemDebug;
	public Item itemFlintAndStick;
	
	public void initServer()
	{
		blockUnguiStation = new BlockUnguiStation(Ungui.instance.conifg.getBlock("ungui_station", 3000).getInt());
		itemFlintAndStick = new ItemFlintAndStick(Ungui.instance.conifg.getItem("flint_and_stick", 3001).getInt());
		itemDebug = new ItemDebug(Ungui.instance.conifg.getItem("debug_tool", 3002).getInt());
		
		
		
		
		if(Ungui.instance.conifg.hasChanged()) Ungui.instance.conifg.save();
		
		blockUnguiStation.setUnlocalizedName("unguiStation");
		GameRegistry.registerBlock(blockUnguiStation, ItemUnguiStation.class,  "unguiStation");

		itemDebug.setUnlocalizedName("itemDebug");
		GameRegistry.registerItem(itemDebug, "itemDebug", "ungui");
		itemFlintAndStick.setUnlocalizedName("flintAndStick");
		GameRegistry.registerItem(itemFlintAndStick, "flintAndStick", "ungui");

		GameRegistry.registerTileEntity(TileUnguiFurnace.class, "ungui_furnace");
		
		GameRegistry.addRecipe(new ItemStack(itemFlintAndStick, 1, 0), new Object[] {" #", "% ", '%', Item.flint, '#', Item.stick});
		
		LanguageRegistry.instance().loadLocalization("/lang/ungui/en_US.properties", "en_US", false);
	}
	
	public void initClient()
	{
		
	}
}
