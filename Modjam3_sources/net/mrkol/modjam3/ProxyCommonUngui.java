package net.mrkol.modjam3;

import net.minecraft.block.Block;
import net.mrkol.modjam3.blocks.BlockUnguiStation;
import net.mrkol.modjam3.blocks.ItemUnguiStation;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import cpw.mods.fml.common.registry.GameRegistry;

public class ProxyCommonUngui
{
	public Block blockUnguiStation = new BlockUnguiStation(3000);
	
	public void initServer()
	{
		blockUnguiStation.setUnlocalizedName("unguiStation");
		GameRegistry.registerBlock(blockUnguiStation, ItemUnguiStation.class,  "unguiStation");
		GameRegistry.registerTileEntity(TileUnguiFurnace.class, "ungui_furnace");
	}
	
	public void initClient()
	{
		
	}
}
