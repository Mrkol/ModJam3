package net.mrkol.modjam3.raytracing;

public class Cuboid6f
{
	public float X;
	public float Y;
	public float Z;
	public float W;
	public float H;
	public float D;
	
	public Cuboid6f(float x, float y, float z, float w, float h, float d)
	{
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.W = w;
		this.H = h;
		this.D = d;
	}
	
	public Cuboid6f()
	{
		this(0, 0, 0, 1, 1, 1);
	}
}
