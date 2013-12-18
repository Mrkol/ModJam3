package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.mrkol.modjam3.raytracing.Cuboid6f;

public class TileUnguiWorkbench extends TileUnguiStation
{
	public boolean onCuboidActivated(int cuboidID, EntityPlayer player)
	{
		boolean b = true;
		switch(cuboidID)
		{
			case 0:
				
				break;
				
			case 1:
				
				break;
			
			default:
				b = false;
				break;
		}
		return b;
	}
	@Override
	public List<Cuboid6f> getCuboids()
	{
		List<Cuboid6f> cuboids = new ArrayList<Cuboid6f>();
		cuboids.add(new Cuboid6f(0, 0, 0, 1, 0.5f, 1));
		cuboids.add(new Cuboid6f(0, 0.5f, 0, 1, 0.5f, 1));
		
		return cuboids;
	}
}
