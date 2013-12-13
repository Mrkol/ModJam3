package net.mrkol.modjam3.raytracing;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Raytracer
{
	private static Vec3 vec = Vec3.createVectorHelper(0, 0, 0);
	private static Vec3 vec2 = Vec3.createVectorHelper(0, 0, 0);
	private static Vec3 vec3 = Vec3.createVectorHelper(0, 0, 0);
	private static double m_distance = 0;
	private static int m_side = 0;
	private static Cuboid6f m_cuboid = new Cuboid6f();
	
	public static MovingObjectPosition traceCuboid(Cuboid6f c, Vec3 org, Vec3 dir)
	{
		m_distance = Double.MAX_VALUE;
		m_side = -1;
		for(int i = 0; i < 6; i++)
		{
			traceSide(c, org, dir, i);
		}
		if(m_side < 0) return null;
		MovingObjectPosition mop = new MovingObjectPosition(0, 0, 0, m_side, vec3);
		mop.entityHit = null;
		return mop;
	}
	
	public static MovingObjectPosition traceCuboids(Vec3 s, Vec3 e, List<Cuboid6f> l, int x, int y, int z, Block block)
	{
		double d = Double.MAX_VALUE;
		MovingObjectPosition hit = null;
		for(int i = 0; i < l.size(); i++)
		{
			MovingObjectPosition mop = traceCuboid(l.get(i), s, e);
			if(mop != null && m_distance < d)
			{
				d = m_distance;
				hit = mop;
				m_cuboid = l.get(i);
			}
		}
		
		if(hit != null)
		{
			hit.typeOfHit = EnumMovingObjectType.TILE;
			hit.blockX = x;
			hit.blockY = y;
			hit.blockZ = z;
		}
		
		if(block != null)
		{
			block.setBlockBounds(m_cuboid.X, m_cuboid.Y, m_cuboid.Z, m_cuboid.X + m_cuboid.H, m_cuboid.Y + m_cuboid.W, m_cuboid.Z + m_cuboid.D);
		}
		
		return hit;
	}
	
	public static void traceSide(Cuboid6f c, Vec3 org, Vec3 dir, int side)
	{
		vec.xCoord = org.xCoord;
		vec.yCoord = org.yCoord;
		vec.zCoord = org.zCoord;
		
		Vec3 hit = null;
		
		switch(side)
		{
			case 0:
				hit = vec.getIntermediateWithYValue(dir, c.Y);
				break;
			case 1:
				hit = vec.getIntermediateWithYValue(dir, c.Y + c.H);
				break;
			case 2:
				hit = vec.getIntermediateWithZValue(dir, c.Z);
				break;
			case 3:
				hit = vec.getIntermediateWithZValue(dir, c.Z + c.D);
				break;
			case 4:
				hit = vec.getIntermediateWithXValue(dir, c.X);
				break;
			case 5:
				hit = vec.getIntermediateWithXValue(dir, c.X + c.W);
				break;
		}
		if(hit == null)
		{
			return;
		}
		switch(side)
		{
			case 0:
			case 1:
				if(!(c.X < hit.xCoord && hit.xCoord < c.X + c.W) || !(c.Z < hit.zCoord && hit.zCoord < c.Z + c.D)) return;
				break;

			case 2:
			case 3:
				if(!(c.X < hit.xCoord && hit.xCoord < c.X + c.W) || !(c.Y < hit.yCoord && hit.yCoord < c.Y + c.H)) return;
				break;

			case 4:
			case 5:
				if(!(c.Y < hit.yCoord && hit.yCoord < c.Y + c.H) || !(c.Z < hit.zCoord && hit.zCoord < c.Z + c.D)) return;
				break;
		}

		vec2.xCoord = hit.xCoord;
		vec2.yCoord = hit.yCoord;
		vec2.zCoord = hit.zCoord;
		vec2 = vec2.subtract(org);
		double d = vec2.xCoord*vec2.xCoord + vec2.yCoord*vec2.yCoord + vec2.zCoord*vec2.zCoord;
		if(d < m_distance)
		{
			m_side = side;
			m_distance = d;
			vec3.xCoord = vec.xCoord;
			vec3.yCoord = vec.yCoord;
			vec3.zCoord = vec.zCoord;
		}
	}
}
