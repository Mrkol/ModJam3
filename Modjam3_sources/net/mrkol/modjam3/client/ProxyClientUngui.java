package net.mrkol.modjam3.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import net.mrkol.modjam3.ProxyCommonUngui;
import net.mrkol.modjam3.Ungui;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;

public class ProxyClientUngui extends ProxyCommonUngui
{
	@Override
	public void initClient()
	{
		RendererUnguiFurnace ruf = new RendererUnguiFurnace();
		ClientRegistry.bindTileEntitySpecialRenderer(TileUnguiFurnace.class, ruf);
		MinecraftForgeClient.registerItemRenderer(Ungui.proxy.blockUnguiStation.blockID, ruf);
		
	}
}
