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

public class ProxyCommonUngui
{
	public Block blockUnguiStation = new BlockUnguiStation(3000);
	public Item itemDebug = new ItemDebug(1000);
	public Item itemFlintAndStick = new ItemFlintAndStick(1001);
	
	public void initServer()
	{
		blockUnguiStation.setUnlocalizedName("unguiStation");
		GameRegistry.registerBlock(blockUnguiStation, ItemUnguiStation.class,  "unguiStation");

		itemDebug.setUnlocalizedName("itemDebug");
		GameRegistry.registerItem(itemDebug, "itemDebug", "ungui");
		itemFlintAndStick.setUnlocalizedName("flintAndStick");
		GameRegistry.registerItem(itemFlintAndStick, "flintAndStick", "ungui");

		GameRegistry.registerTileEntity(TileUnguiFurnace.class, "ungui_furnace");
		
		GameRegistry.addRecipe(new ItemStack(itemFlintAndStick, 1, 0), new Object[] {" #", "% ", '%', Item.flint, '#', Item.stick});
	}
	
	public void initClient()
	{
		
	}
}
