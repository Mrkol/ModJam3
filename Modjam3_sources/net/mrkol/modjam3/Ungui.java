package net.mrkol.modjam3;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "ungui", version="0.0.1.0", name="Ungui")
@NetworkMod()
public class Ungui 
{
	@Instance(value="ungui")
	public static Ungui instance;
	
	@SidedProxy(serverSide="net.mrkol.modjam3.ProxyCommonUngui3", clientSide="net.mrkol.modjam3.client.ProxyClientUngui", modId="ungui")
	public static ProxyCommonUngui proxy;
	
	
	public Configuration conifg;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent fmlpie) //FML   PIE!
	{
		this.conifg = new Configuration(new File("config/ungui.cfg"));
	}
	
	
	@EventHandler
	public void init(FMLInitializationEvent fmlie)
	{
		proxy.initServer();
		proxy.initClient();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent fmlpie) // FML-pie!
	{
		
	}
}
