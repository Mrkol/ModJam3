package net.mrkol.modjam3.raytracing;

import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
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
	public static Cuboid6f m_cuboid = new Cuboid6f();
	
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
		mop.typeOfHit = null;
		return mop;
	}
	
	public static MovingObjectPosition traceCuboid_vanilla(Cuboid6f c, Vec3 s, Vec3 e)
	{
		Vec3 minx = s.getIntermediateWithXValue(e, c.min.xCoord);
		Vec3 maxx = s.getIntermediateWithXValue(e, c.max.xCoord);
		Vec3 miny = s.getIntermediateWithXValue(e, c.min.yCoord);
		Vec3 maxy = s.getIntermediateWithXValue(e, c.max.yCoord);
		Vec3 minz = s.getIntermediateWithXValue(e, c.min.zCoord);
		Vec3 maxz = s.getIntermediateWithXValue(e, c.max.zCoord);
		
		
		Vec3 hit = null;
		if((minx != null) && ((hit == null) || (s.squareDistanceTo(minx) < s.squareDistanceTo(hit)))) hit = minx;
		if((maxx != null) && ((hit == null) || (s.squareDistanceTo(maxx) < s.squareDistanceTo(hit)))) hit = maxx;
		if((miny != null) && ((hit == null) || (s.squareDistanceTo(miny) < s.squareDistanceTo(hit)))) hit = miny;
		if((maxy != null) && ((hit == null) || (s.squareDistanceTo(maxy) < s.squareDistanceTo(hit)))) hit = maxy;
		if((minz != null) && ((hit == null) || (s.squareDistanceTo(minz) < s.squareDistanceTo(hit)))) hit = minz;
		if((maxz != null) && ((hit == null) || (s.squareDistanceTo(maxz) < s.squareDistanceTo(hit)))) hit = maxz;
		
		if(hit == null) {
			return null;
		}
		byte side = -1;
		if(hit == minx) side = 4;
		if(hit == maxx) side = 5;
		if(hit == miny) side = 0;
		if(hit == maxy) side = 1;
		if(hit == minz) side = 2;
		if(hit == maxz) side = 3;
		
		m_distance = s.squareDistanceTo(hit);
		return new MovingObjectPosition(0, 0, 0, side, hit);
	}
	
	public static MovingObjectPosition traceCuboids(Vec3 s, Vec3 e, List<Cuboid6f> l, int x, int y, int z, Block block)
	{
		double d = Double.MAX_VALUE;
		MovingObjectPosition hit = null;
		for(int i = 0; i < l.size(); i++)
		{
			MovingObjectPosition mop = traceCuboid_vanilla(l.get(i), s.addVector(-x, -y, -z), e.addVector(-x, -y, -z));
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
			hit.hitVec = hit.hitVec.addVector(x, y, z);
			hit.blockX = x;
			hit.blockY = y;
			hit.blockZ = z;
		}
		
		if(block != null)
		{
			block.setBlockBounds((float) m_cuboid.min.xCoord, (float) m_cuboid.min.yCoord, (float) m_cuboid.min.zCoord, (float) m_cuboid.max.xCoord, (float) m_cuboid.max.yCoord,  (float) m_cuboid.max.zCoord);
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
				hit = XZcheck(vec, dir, c.min.yCoord);
				break;
			case 1:
				hit = XZcheck(vec, dir, c.max.yCoord);
				break;
			case 2:
				hit = XYcheck(vec, dir, c.min.zCoord);
				break;
			case 3:
				hit = XYcheck(vec, dir, c.max.zCoord);
				break;
			case 4:
				hit = YZcheck(vec, dir, c.min.xCoord);
				break;
			case 5:
				hit = YZcheck(vec, dir, c.max.xCoord);
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
				if(!(c.min.xCoord <= hit.xCoord && hit.xCoord <= c.max.xCoord) || !(c.min.zCoord <= hit.zCoord && hit.zCoord <= c.max.zCoord)) return;
				break;

			case 2:
			case 3:
				if(!(c.min.xCoord <= hit.xCoord && hit.xCoord <= c.max.xCoord) || !(c.min.yCoord <= hit.yCoord && hit.yCoord <= c.max.yCoord)) return;
				break;

			case 4:
			case 5:
				if(!(c.min.yCoord <= hit.yCoord && hit.yCoord <= c.max.yCoord) || !(c.min.zCoord <= hit.zCoord && hit.zCoord <= c.max.zCoord)) return;
				break;
		}

		vec2.xCoord = hit.xCoord;
		vec2.yCoord = hit.yCoord;
		vec2.zCoord = hit.zCoord;
		double d = Math.pow(vec2.subtract(org).lengthVector(), 2);
		if(d < m_distance)
		{
			m_side = side;
			m_distance = d;
			vec3.xCoord = vec.xCoord;
			vec3.yCoord = vec.yCoord;
			vec3.zCoord = vec.zCoord;
		}
	}

	public static Vec3 YZcheck(Vec3 s, Vec3 e, double p)
	{
		double dx = e.xCoord - s.xCoord;
		double dy = e.yCoord - s.yCoord;
		double dz = e.zCoord - s.zCoord;
		
		//if(dx == 0) return null;
		double d = (p - s.xCoord) / dx;
		if(-1d/100000d < d && d < 1d/100000d) return s;
		if(0 > d && d > 1) return null;
		
		s.xCoord = p;
		s.yCoord += d * dy;
		s.zCoord += d * dz;
		
		return s;
	}
	
	public static Vec3 XZcheck(Vec3 s, Vec3 e, double p)
	{
		double dx = e.xCoord - s.xCoord;
		double dy = e.yCoord - s.yCoord;
		double dz = e.zCoord - s.zCoord;
		
		//if(dx == 0) return null;
		double d = (p - s.yCoord) / dy;
		if(-1d/100000d < d && d < 1d/100000d) return s;
		if(0 > d && d > 1) return null;
		
		s.xCoord += d * dx;
		s.yCoord = p;
		s.zCoord += d * dz;
		
		return s;
	}

	public static Vec3 XYcheck(Vec3 s, Vec3 e, double p)
	{
		double dx = e.xCoord - s.xCoord;
		double dy = e.yCoord - s.yCoord;
		double dz = e.zCoord - s.zCoord;
		
		//if(dx == 0) return null;
		double d = (p - s.zCoord) / dz;
		if(-1d/100000d < d && d < 1d/100000d) return s;
		if(0 > d && d > 1) return null;
		
		s.xCoord += d * dx;
		s.yCoord += d * dy;
		s.zCoord = p;
		
		return s;
	}
}
