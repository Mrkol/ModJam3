package net.mrkol.modjam3.blocks;

import java.util.List;
import java.util.logging.ConsoleHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.Cuboid6f;
import net.mrkol.modjam3.raytracing.Raytracer;

public class BlockUnguiStation extends BlockContainer
{

	public BlockUnguiStation(int id)
	{
		super(id, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHardness(3.5F);
		this.setStepSound(soundStoneFootstep);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		if(par1World.isRemote) return; 
		TileUnguiFurnace tile = (TileUnguiFurnace)par1World.getBlockTileEntity(par2, par3, par4);

        if (tile != null)
        {
        	ItemStack itemstack = null;
        	for(int i = 0; i < 3; i++)
        	{
        		if(i == 0) itemstack = tile.smeltingIn;
        		if(i == 1) itemstack = tile.smeltingOut;
        		if(i == 2) itemstack = tile.fuel;
        		
                if (itemstack != null)
                {
                    float f = par1World.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = par1World.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = par1World.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
                    {
                        int k1 = par1World.rand.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)par1World.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)par1World.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)par1World.rand.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
                
                
                if(tile.heatLevel > 100) par1World.setBlock(par2, par3, par4, Block.lavaMoving.blockID);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

	@Override
	public TileEntity createNewTileEntity(World arg0)
	{
		return null;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB AABB, List al, Entity e)
	{
		this.setBlockBounds(0.01f, 0.01f, 0.01f, 0.98f, 0.98f, 0.98f);
		super.addCollisionBoxesToList(world, x, y, z, AABB, al, e);
	}
    
    @Override
	 public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack is)
	 {
		 TileEntity te = world.getBlockTileEntity(x, y, z);
		 
		 if(te != null && te instanceof TileUnguiFurnace)
		 {
			 TileUnguiFurnace tuf = (TileUnguiFurnace)te;
			 int i = MathHelper.floor_double(elb.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
			tuf.rotation = (byte)i;
		 }
		 
	}
	 
	 @Override
	 public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 s, Vec3 e)
	 {
			TileEntity te = w.getBlockTileEntity(x, y, z);
			if(te != null && te instanceof TileUnguiStation)
			{
				TileUnguiStation tus = (TileUnguiStation)te;
				
				return Raytracer.traceCuboids(s, e, tus.getCuboids(), x, y, z, tus.getBlockType());
				/**MovingObjectPosition mop = Raytracer.traceCuboid_vanilla(tus.cuboids.get(0), s.addVector((double)(-x), (double)(-y), (double)(-z)), e.addVector((double)(-x), (double)(-y), (double)(-z)));
				if(mop != null)
				{
					mop.blockX = x;
					mop.blockY = y;
					mop.blockZ = z;
					mop.hitVec = mop.hitVec.addVector((double)x, (double)y, (double)z);
				}
				return mop;**/
			}

			return null;
	 }
	 
	 @Override
	 public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	 {
		 return AxisAlignedBB.getAABBPool().getAABB((double)par2, (double)par3, (double)par4, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
	 }
	 
	 
	 @Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		//if(w.isRemote) return false;
		
		TileEntity te = w.getBlockTileEntity(x, y, z);
		if(te instanceof TileUnguiStation)
		{
			TileUnguiStation tus = (TileUnguiStation)te;
			return tus.onActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
		}
		
		return false;
	}
	 
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		switch(metadata)
		{
			case 0:
				return new TileUnguiFurnace();
		}
		return null;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return false;
	}
	
	@Override
	public void registerIcons(IconRegister ir)
	{
		this.blockIcon = ir.registerIcon("furnace_side");
	}
}
