package net.mrkol.modjam3.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.mrkol.modjam3.ProxyCommonUngui;
import net.mrkol.modjam3.blocks.TileUnguiFurnace;

public class ProxyClientUngui extends ProxyCommonUngui
{
	@Override
	public void initClient()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileUnguiFurnace.class, new RendererUnguiFurnace());
	}
}
