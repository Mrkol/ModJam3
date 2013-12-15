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
	public static Cuboid6f m_cuboid = new Cuboid6f();
	
    private static boolean isVecInsideYZBounds(Cuboid6f c, Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.yCoord >= c.min.yCoord && par1Vec3.yCoord <= c.max.yCoord && par1Vec3.zCoord >= c.min.zCoord && par1Vec3.zCoord <= c.max.zCoord;
    }

    private static boolean isVecInsideXZBounds(Cuboid6f c, Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.xCoord >= c.min.xCoord && par1Vec3.xCoord <= c.max.xCoord && par1Vec3.zCoord >= c.min.zCoord && par1Vec3.zCoord <= c.max.zCoord;
    }

    private static boolean isVecInsideXYBounds(Cuboid6f c, Vec3 par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.xCoord >= c.min.xCoord && par1Vec3.xCoord <= c.max.xCoord && par1Vec3.yCoord >= c.min.yCoord && par1Vec3.yCoord <= c.max.yCoord;
    }
	
	public static MovingObjectPosition traceCuboid_vanilla(Cuboid6f c, Vec3 e, Vec3 s)
	{
        Vec3 vec32 = e.getIntermediateWithXValue(s, c.min.xCoord);
        Vec3 vec33 = e.getIntermediateWithXValue(s, c.max.xCoord);
        Vec3 vec34 = e.getIntermediateWithYValue(s, c.min.yCoord);
        Vec3 vec35 = e.getIntermediateWithYValue(s, c.max.yCoord);
        Vec3 vec36 = e.getIntermediateWithZValue(s, c.min.zCoord);
        Vec3 vec37 = e.getIntermediateWithZValue(s, c.max.zCoord);

        if (!isVecInsideYZBounds(c, vec32)) vec32 = null;
        if (!isVecInsideYZBounds(c, vec33)) vec33 = null;
        if (!isVecInsideXZBounds(c, vec34))  vec34 = null;
        if (!isVecInsideXZBounds(c, vec35)) vec35 = null;
        if (!isVecInsideXYBounds(c, vec36))  vec36 = null;
        if (!isVecInsideXYBounds(c, vec37)) vec37 = null;

        Vec3 hit = null;

        if (vec32 != null && (hit == null || e.squareDistanceTo(vec32) < e.squareDistanceTo(hit)))
        {
            hit = vec32;
        }

        if (vec33 != null && (hit == null || e.squareDistanceTo(vec33) < e.squareDistanceTo(hit)))
        {
            hit = vec33;
        }

        if (vec34 != null && (hit == null || e.squareDistanceTo(vec34) < e.squareDistanceTo(hit)))
        {
            hit = vec34;
        }

        if (vec35 != null && (hit == null || e.squareDistanceTo(vec35) < e.squareDistanceTo(hit)))
        {
            hit = vec35;
        }

        if (vec36 != null && (hit == null || e.squareDistanceTo(vec36) < e.squareDistanceTo(hit)))
        {
            hit = vec36;
        }

        if (vec37 != null && (hit == null || e.squareDistanceTo(vec37) < e.squareDistanceTo(hit)))
        {
            hit = vec37;
        }

        if (hit == null)
        {
            return null;
        }
        
        byte b0 = -1;

        if (hit == vec32) b0 = 4;
        if (hit == vec33)  b0 = 5;
        if (hit == vec34) b0 = 0;
        if (hit == vec35) b0 = 1;
        if (hit == vec36) b0 = 2;
        if (hit == vec37)  b0 = 3;

		m_distance = s.squareDistanceTo(hit);
		return new MovingObjectPosition(0, 0, 0, b0, hit);
	}
	
	public static MovingObjectPosition traceCuboids(Vec3 s, Vec3 e, List<Cuboid6f> l, int x, int y, int z, Block block)
	{
		m_cuboid = null;
		double d = 0;
		MovingObjectPosition hit = null;
		for(int i = 0; i < l.size(); i++)
		{
			MovingObjectPosition mop = traceCuboid_vanilla(l.get(i), s.addVector(-x, -y, -z), e.addVector(-x, -y, -z));
			if(mop != null && m_distance > d)
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
		
		if(block != null && m_cuboid != null)
		{
			block.setBlockBounds((float) m_cuboid.min.xCoord, (float) m_cuboid.min.yCoord, (float) m_cuboid.min.zCoord, (float) m_cuboid.max.xCoord, (float) m_cuboid.max.yCoord,  (float) m_cuboid.max.zCoord);
		}
		
		return hit;
	}
}
