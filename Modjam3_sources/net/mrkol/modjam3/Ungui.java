package net.mrkol.modjam3;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "ungui", version="", name="Ungui")
@NetworkMod()
public class Ungui 
{
	@Instance(value="ungui")
	public static Ungui instance;
	
	@SidedProxy(serverSide="net.mrkol.modjam3.ProxyCommonUngui3", clientSide="net.mrkol.modjam3.client.ProxyClientUngui", modId="ungui")
	public static ProxyCommonUngui proxy;
	
	
	@EventHandler
	public void init(FMLInitializationEvent fmlie)
	{
		proxy.initServer();
		proxy.initClient();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent fmlpie) // FML-pie!
	{
		List l = CraftingManager.getInstance().getRecipeList();
		
		Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
		while (iterator.hasNext())
		{
		        IRecipe recipe = iterator.next();
		        if (recipe == null) continue;
		        ItemStack output = recipe.getRecipeOutput();
		        if (output != null && output.itemID == Block.furnaceIdle.blockID)  iterator.remove();
		}
			
		GameRegistry.addRecipe(new ItemStack(Ungui.proxy.blockUnguiStation.blockID, 1, 0), new Object[] {"###", "# #", "###", '#', Block.cobblestone});
	}
}
