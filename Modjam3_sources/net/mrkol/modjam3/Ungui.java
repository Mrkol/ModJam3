package net.mrkol.modjam3;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

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
}
