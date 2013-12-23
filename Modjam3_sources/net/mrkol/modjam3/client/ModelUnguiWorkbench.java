package net.mrkol.modjam3.client;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelUnguiWorkbench extends ModelBase
{
    ModelRenderer Base;
    ModelRenderer Button0;
    ModelRenderer Button1;
    ModelRenderer Button2;
    ModelRenderer Button3;
    ModelRenderer Button4;
    ModelRenderer Button5;
    ModelRenderer Button6;
    ModelRenderer Button7;
  
  public ModelUnguiWorkbench()
  {
    textureWidth = 128;
    textureHeight = 64;
    setTextureOffset("Base.Base0", 0, 0);
    setTextureOffset("Base.Leg0", 0, 17);
    setTextureOffset("Base.Leg1", 16, 33);
    setTextureOffset("Base.Leg2", 0, 33);
    setTextureOffset("Base.Leg3", 16, 17);
    setTextureOffset("Base.Cover0", 44, 5);
    setTextureOffset("Base.Cover1", 42, 0);
    setTextureOffset("Base.Cover2", 68, 5);
    setTextureOffset("Base.Cover3", 42, 0);
    setTextureOffset("Base.Cover4", 68, 5);
    setTextureOffset("Base.Cover5", 42, 2);
    setTextureOffset("Base.Cover6", 32, 17);
    setTextureOffset("Base.Cover7", 42, 2);
    setTextureOffset("Base.Cover8", 32, 17);
    setTextureOffset("Base.Cover9", 42, 4);
    setTextureOffset("Base.Cover10", 32, 26);
    setTextureOffset("Base.Cover11", 42, 4);
    setTextureOffset("Base.Cover12", 32, 26);
    setTextureOffset("Base.Cover13", 42, 6);
    setTextureOffset("Base.Cover14", 32, 33);
    setTextureOffset("Base.Cover15", 42, 6);
    setTextureOffset("Base.Cover16", 32, 33);
    setTextureOffset("Base.Cover17", 42, 8);
    setTextureOffset("Base.Cover18", 32, 38);
    setTextureOffset("Base.Cover19", 42, 8);
    setTextureOffset("Base.Cover20", 32, 38);
    
    Base = new ModelRenderer(this, "Base");
    Base.setRotationPoint(-8F, 0F, -8F);
    setRotation(Base, 0F, 0F, 0F);
    Base.mirror = true;
      Base.addBox("Base0", 1F, 1F, 1F, 14, 3, 14);
      Base.addBox("Leg0", 1F, 4F, 1F, 4, 12, 4);
      Base.addBox("Leg1", 11F, 4F, 1F, 4, 12, 4);
      Base.addBox("Leg2", 11F, 4F, 11F, 4, 12, 4);
      Base.addBox("Leg3", 1F, 4F, 11F, 4, 12, 4);
      Base.addBox("Cover0", 2F, 0F, 2F, 12, 1, 12);
      Base.addBox("Cover1", 3F, 0F, 1F, 10, 1, 1);
      Base.addBox("Cover2", 1F, 0F, 3F, 1, 1, 10);
      Base.addBox("Cover3", 3F, 0F, 14F, 10, 1, 1);
      Base.addBox("Cover4", 14F, 0F, 3F, 1, 1, 10);
      Base.addBox("Cover5", 4F, 0F, 0F, 8, 1, 1);
      Base.addBox("Cover6", 0F, 0F, 4F, 1, 1, 8);
      Base.addBox("Cover7", 4F, 0F, 15F, 8, 1, 1);
      Base.addBox("Cover8", 15F, 0F, 4F, 1, 1, 8);
      Base.addBox("Cover9", 5F, 1F, 0F, 6, 1, 1);
      Base.addBox("Cover10", 0F, 1F, 5F, 1, 1, 6);
      Base.addBox("Cover11", 5F, 1F, 15F, 6, 1, 1);
      Base.addBox("Cover12", 15F, 1F, 5F, 1, 1, 6);
      Base.addBox("Cover13", 6F, 2F, 0F, 4, 1, 1);
      Base.addBox("Cover14", 0F, 2F, 6F, 1, 1, 4);
      Base.addBox("Cover15", 6F, 2F, 15F, 4, 1, 1);
      Base.addBox("Cover16", 15F, 2F, 6F, 1, 1, 4);
      Base.addBox("Cover17", 7F, 3F, 0F, 2, 1, 1);
      Base.addBox("Cover18", 0F, 3F, 7F, 1, 1, 2);
      Base.addBox("Cover19", 7F, 3F, 15F, 2, 1, 1);
      Base.addBox("Cover20", 15F, 3F, 7F, 1, 1, 2);
      Button0 = new ModelRenderer(this, 0, 0);
      Button0.addBox(0F, 0F, 0F, 1, 1, 2);
      Button0.setRotationPoint(7F, 2F, 4F);
      Button0.setTextureSize(128, 64);
      Button0.mirror = true;
      setRotation(Button0, 0F, 0F, 0F);
      Button1 = new ModelRenderer(this, 0, 0);
      Button1.addBox(0F, 0F, -2F, 1, 1, 2);
      Button1.setRotationPoint(7F, 2F, -4F);
      Button1.setTextureSize(128, 64);
      Button1.mirror = true;
      setRotation(Button1, 0F, 0F, 0F);
      Button2 = new ModelRenderer(this, 0, 0);
      Button2.addBox(-1F, 0F, 0F, 1, 1, 2);
      Button2.setRotationPoint(-7F, 2F, 4F);
      Button2.setTextureSize(128, 64);
      Button2.mirror = true;
      setRotation(Button2, 0F, 0F, 0F);
      Button3 = new ModelRenderer(this, 0, 0);
      Button3.addBox(-1F, 0F, -2F, 1, 1, 2);
      Button3.setRotationPoint(-7F, 2F, -4F);
      Button3.setTextureSize(128, 64);
      Button3.mirror = true;
      setRotation(Button3, 0F, 0F, 0F);
      Button4 = new ModelRenderer(this, 0, 3);
      Button4.addBox(0F, 0F, 0F, 2, 1, 1);
      Button4.setRotationPoint(4F, 2F, 7F);
      Button4.setTextureSize(128, 64);
      Button4.mirror = true;
      setRotation(Button4, 0F, 0F, 0F);
      Button5 = new ModelRenderer(this, 0, 3);
      Button5.addBox(-2F, 0F, 0F, 2, 1, 1);
      Button5.setRotationPoint(-4F, 2F, 7F);
      Button5.setTextureSize(128, 64);
      Button5.mirror = true;
      setRotation(Button5, 0F, 0F, 0F);
      Button6 = new ModelRenderer(this, 0, 3);
      Button6.addBox(-2F, 0F, -1F, 2, 1, 1);
      Button6.setRotationPoint(-4F, 2F, -7F);
      Button6.setTextureSize(128, 64);
      Button6.mirror = true;
      setRotation(Button6, 0F, 0F, 0F);
      Button7 = new ModelRenderer(this, 0, 3);
      Button7.addBox(0F, 0F, -1F, 2, 1, 1);
      Button7.setRotationPoint(4F, 2F, -7F);
      Button7.setTextureSize(128, 64);
      Button7.mirror = true;
      setRotation(Button7, 0F, 0F, 0F);
  }
  
  public void render(float[] buttons)
  {
	 float factor = 1f/16f;
    super.render(null, 0, 0, 0, 0, 0, factor);
    setRotationAngles(0, 0, 0, 0, 0, factor);
    Base.render(factor);
    
    GL11.glPushMatrix();
    GL11.glTranslated(-Math.sin(Math.toRadians(buttons[0] * 180))/16f * (3f/4f), 0, 0);
    Button0.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(-Math.sin(Math.toRadians(buttons[1] * 180))/16f * (3f/4f), 0, 0);
    Button1.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(Math.sin(Math.toRadians(buttons[2] * 180))/16f * (3f/4f), 0, 0);
    Button2.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(Math.sin(Math.toRadians(buttons[3] * 180))/16f * (3f/4f), 0, 0);
    Button3.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(0, 0, -Math.sin(Math.toRadians(buttons[4] * 180))/16f * (3f/4f));
    Button4.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(0, 0, -Math.sin(Math.toRadians(buttons[5] * 180))/16f * (3f/4f));
    Button5.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(0, 0, Math.sin(Math.toRadians(buttons[6] * 180))/16f * (3f/4f));
    Button6.render(factor);
    GL11.glPopMatrix();
    
    GL11.glPushMatrix();
    GL11.glTranslated(0, 0, Math.sin(Math.toRadians(buttons[7] * 180))/16f * (3f/4f));
    Button7.render(factor);
    GL11.glPopMatrix();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
