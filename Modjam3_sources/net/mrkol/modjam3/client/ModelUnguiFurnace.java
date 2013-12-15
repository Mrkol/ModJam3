package net.mrkol.modjam3.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelUnguiFurnace extends ModelBase
{
    ModelRenderer Base;
  
  public ModelUnguiFurnace()
  {
    textureWidth = 128;
    textureHeight = 64;
    setTextureOffset("Base.Frame", 0, 0);
    setTextureOffset("Base.Fuel0", 64, 0);
    setTextureOffset("Base.Fuel1", 64, 0);
    setTextureOffset("Base.Fuel2", 0, 47);
    setTextureOffset("Base.Fuel3", 64, 18);
    setTextureOffset("Base.Fuel4", 64, 18);
    setTextureOffset("Base.Fuel5", 64, 33);
    setTextureOffset("Base.Fuel6", 64, 33);
    setTextureOffset("Base.Fuel7", 12, 32);
    setTextureOffset("Base.Fuel8", 0, 32);
    setTextureOffset("Base.Cook0", 80, 32);
    setTextureOffset("Base.Cook1", 34, 33);
    setTextureOffset("Base.Cook2", 34, 33);
    setTextureOffset("Base.Cook3", 64, 33);
    setTextureOffset("Base.Cook4", 64, 33);
    setTextureOffset("Base.Cook5", -2, 47);
    setTextureOffset("Base.Cook6", 1, 39);
    
    Base = new ModelRenderer(this, "Base");
    Base.setRotationPoint(0F, 0F, 0F);
    setRotation(Base, 0F, 0F, 0F);
    Base.mirror = true;
      Base.addBox("Frame", -8F, 0F, -8F, 16, 16, 16);
      Base.addBox("Fuel0", 5F, 12F, -8F, 1, 4, 14);
      Base.addBox("Fuel1", -6F, 12F, -8F, 1, 4, 14);
      Base.addBox("Fuel2", -2F, 9F, -8F, 4, 1, 14);
      Base.addBox("Fuel3", -4F, 10F, -8F, 2, 1, 14);
      Base.addBox("Fuel4", 2F, 10F, -8F, 2, 1, 14);
      Base.addBox("Fuel5", -5F, 11F, -8F, 1, 1, 14);
      Base.addBox("Fuel6", 4F, 11F, -8F, 1, 1, 14);
      Base.addBox("Fuel7", -5F, 15F, -8F, 10, 1, 14);
      Base.addBox("Fuel8", -6F, 9F, 6F, 12, 7, 1);
      Base.addBox("Cook0", -4F, 7F, -8F, 8, 1, 14);
      Base.addBox("Cook1", 4F, 5F, -8F, 1, 3, 14);
      Base.addBox("Cook2", -5F, 5F, -8F, 1, 3, 14);
      Base.addBox("Cook3", -4F, 4F, -8F, 1, 1, 14);
      Base.addBox("Cook4", 3F, 4F, -8F, 1, 1, 14);
      Base.addBox("Cook5", -3F, 3F, -8F, 6, 1, 14);
      Base.addBox("Cook6", -5F, 3F, 6F, 10, 5, 1);
  }
  
  public void render()
  {
	float factor = 1f/16f;
    super.render(null, 0, 0, 0, 0, 0, factor);
    setRotationAngles(0, 0, 0, 0, 0, factor);
    Base.render(factor);
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
