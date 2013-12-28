package net.mrkol.modjam3;

import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.mrkol.modjam3.blocks.BlockUnguiStation;
import net.mrkol.modjam3.blocks.ItemUnguiStation;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.blocks.TileUnguiWorkbench;
import net.mrkol.modjam3.items.ItemDebug;
import net.mrkol.modjam3.items.ItemFlintAndStick;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ProxyCommonUngui
{
	public Block blockUnguiStationRock;
	public Block blockUnguiStationWood;
	public Item itemDebug;
	public Item itemFlintAndStick;
	
	public void initServer()
	{
		Block.blocksList[Block.furnaceIdle.blockID] = null;
		Item.itemsList[Block.furnaceIdle.blockID] = null;
		Block.blocksList[Block.workbench.blockID] = null;
		Item.itemsList[Block.workbench.blockID] = null;
		
		
		//blockUnguiStation = new BlockUnguiStation(Ungui.instance.conifg.getBlock("ungui_station", 3000).getInt());
		blockUnguiStationRock = new BlockUnguiStation(Block.furnaceIdle.blockID, Material.rock).setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		blockUnguiStationWood = new BlockUnguiStation(Block.workbench.blockID, Material.wood).setHardness(2.5F).setStepSound(Block.soundWoodFootstep);
		
		itemFlintAndStick = new ItemFlintAndStick(Ungui.instance.conifg.getItem("flint_and_stick", 3001).getInt());
		itemDebug = new ItemDebug(Ungui.instance.conifg.getItem("debug_tool", 3002).getInt());
		
		if(Ungui.instance.conifg.hasChanged()) Ungui.instance.conifg.save();


		blockUnguiStationRock.setUnlocalizedName("unguiStation_rock");
		blockUnguiStationWood.setUnlocalizedName("unguiStation_wood");

		GameRegistry.registerBlock(blockUnguiStationRock, ItemUnguiStation.ItemUnguiStation_0.class,  "unguiStation_rock");
		GameRegistry.registerBlock(blockUnguiStationWood, ItemUnguiStation.ItemUnguiStation_1.class,  "unguiStation_wood");

		itemDebug.setUnlocalizedName("itemDebug");
		GameRegistry.registerItem(itemDebug, "itemDebug", "ungui");
		
		itemFlintAndStick.setUnlocalizedName("flintAndStick");
		GameRegistry.registerItem(itemFlintAndStick, "flintAndStick", "ungui");

		GameRegistry.registerTileEntity(TileUnguiFurnace.class, "ungui_furnace");
		GameRegistry.registerTileEntity(TileUnguiWorkbench.class, "ungui_workbench");
		
		GameRegistry.addRecipe(new ItemStack(itemFlintAndStick, 1, 0), new Object[] {" #", "% ", '%', Item.flint, '#', Item.stick});
		
		LanguageRegistry.instance().loadLocalization("/lang/ungui/en_US.properties", "en_US", false);
	}
	
	public void initClient()
	{
		
	}
}
