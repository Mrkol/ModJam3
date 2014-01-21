package net.mrkol.modjam3.client;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class EntityPoisonFX extends EntityFX
{
	public static Icon ICON;
	public int timer = 0;
	
	public EntityPoisonFX(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
        this.particleRed = 0;
        this.particleGreen = 1;
        this.particleBlue = 0;
        this.particleAlpha = 0.5f;
		this.noClip = true;
        this.setParticleIcon(ICON);
	}
	
	@Override
	public int getFXLayer()
	{
		return 1;
	}
	
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	timer++;
    	
        float f6 = (float)this.particleTextureIndexX / 16.0F;
        float f7 = f6 + 0.0624375F;
        float f8 = (float)this.particleTextureIndexY / 16.0F;
        float f9 = f8 + 0.0624375F;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null)
        {
            f6 = this.particleIcon.getMinU();
            f7 = this.particleIcon.getMaxU();
            f8 = this.particleIcon.getMinV();
            f9 = this.particleIcon.getMaxV();
        }

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float f14 = 1.0F;

        par1Tessellator.setColorRGBA_F(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha);

        
        double r = 0.5f; //radius
        int n = 6; //amount of points
        
        double slength = (2 * r * Math.sin(Math.PI / n));
        double t =(((double)timer) / 100d * slength) % (slength - 0.00000001d);
        double sangle = 2 * Math.PI / n;
        
        int side = (int)((((double)timer) / 100f) % n) ; //the side we are on right now
        //side = 0;
        
        double dx = Math.sin(side * (2d * Math.PI / n)) * r;
        double dy = Math.cos(side * (2d * Math.PI / n)) * r;
        
        double x = Math.sin((side + 1d) * (2d * Math.PI / n)) * r;
        double y = Math.cos((side + 1d) * (2d * Math.PI / n)) * r;
        
        double mysteryVar = Math.PI / (Math.PI / (Math.acos((r*r + slength*slength - r*r)/(2*r*slength))));
        
        double nx = (Math.sin((side * (2d * Math.PI / n)) + mysteryVar) * (slength - t));
        double ny = (Math.cos((side * (2d * Math.PI / n)) + mysteryVar) * (slength - t));
        
        double cx =   dx - nx;
        double cy =  dy -  ny;
        
        par1Tessellator.addVertexWithUV(f11/* + (cx * Math.sin(Math.PI / 6))*/        ,f12                        ,f13/* + (cy * Math.cos(Math.PI / 6))*/           ,f7, f9);
        par1Tessellator.addVertexWithUV(f11 + cx                                                    ,f12                        ,f13 + cy                                                      ,f7, f8);
        par1Tessellator.addVertexWithUV(f11 + cx                                                    ,f12 + 4f/16f         ,f13 + cy                                                      ,f6, f8);
        par1Tessellator.addVertexWithUV(f11/* + (cx * Math.sin(Math.PI / 6))*/        ,f12 + 4f/16f         , f13/* + (cy * Math.cos(Math.PI / 6))*/          ,f6, f9);
        

        par1Tessellator.addVertexWithUV(f11/* + (cx * Math.sin(Math.PI / 6))*/        ,f12                        ,f13/* + (cy * Math.cos(Math.PI / 6))*/           ,f7, f9);
        par1Tessellator.addVertexWithUV(f11/* + (cx * Math.sin(Math.PI / 6))*/        ,f12 + 4f/16f         , f13/* + (cy * Math.cos(Math.PI / 6))*/          ,f6, f9);
        par1Tessellator.addVertexWithUV(f11 + cx                                                    ,f12 + 4f/16f         ,f13 + cy                                                      ,f6, f8);
        par1Tessellator.addVertexWithUV(f11 + cx                                                    ,f12                        ,f13 + cy                                                      ,f7, f8);
    }

	public void onUpdate()
	{
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
	}
}
