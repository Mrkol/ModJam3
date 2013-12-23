package net.mrkol.modjam3.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import net.mrkol.modjam3.ProxyCommonUngui;
import net.mrkol.modjam3.Ungui;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;
import net.mrkol.modjam3.blocks.TileUnguiWorkbench;

public class ProxyClientUngui extends ProxyCommonUngui
{
	@Override
	public void initClient()
	{
		RendererUnguiFurnace ruf = new RendererUnguiFurnace();
		ClientRegistry.bindTileEntitySpecialRenderer(TileUnguiFurnace.class, ruf);
		
		RenderUnguiWorkbench ruw = new RenderUnguiWorkbench();
		ClientRegistry.bindTileEntitySpecialRenderer(TileUnguiWorkbench.class, ruw);
		
		MinecraftForgeClient.registerItemRenderer(Ungui.proxy.blockUnguiStation.blockID, ruf);
	}
}
