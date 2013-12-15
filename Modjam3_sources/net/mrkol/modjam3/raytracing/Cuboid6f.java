package net.mrkol.modjam3.raytracing;

import net.minecraft.util.Vec3;

public class Cuboid6f
{
	public Vec3 min = Vec3.createVectorHelper(0, 0, 0);
	public Vec3 max = Vec3.createVectorHelper(0, 0, 0);
	
	public Cuboid6f(float x, float y, float z, float w, float h, float d)
	{
		min.xCoord = x;
		min.yCoord = y;
		min.zCoord = z;

		max.xCoord = x + w;
		max.yCoord = y + h;
		max.zCoord = z + d;
	}
	
	public Cuboid6f(Vec3 min, Vec3 max)
	{
		this.min = min;
		this.max = max;
	}
	
	public Cuboid6f()
	{
		this(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Cuboid6f)
		{
			Cuboid6f c = (Cuboid6f)obj;
			if(c.min.xCoord == this.min.xCoord && c.min.yCoord == this.min.yCoord && c.min.zCoord == this.min.zCoord && c.max.xCoord == this.max.xCoord && c.max.yCoord == this.max.yCoord && c.max.zCoord == this.max.zCoord)
			{
				return true;
			}
			
		}
		return false;
	}
}
