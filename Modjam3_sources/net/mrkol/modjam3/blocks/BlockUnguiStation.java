package net.mrkol.modjam3.blocks;

import java.util.List;
import java.util.logging.ConsoleHandler;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0)
	{
		return null;
	}
    
    @Override
	 public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack is)
	 {
		 TileEntity te = world.getBlockTileEntity(x, y, z);
		 
		 if(te != null && te instanceof TileUnguiFurnace)
		 {
			 TileUnguiFurnace tuf = (TileUnguiFurnace)te;
			 int i = MathHelper.floor_double(elb.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
			if(tuf.rotation == -1) tuf.rotation = (byte)i;
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
		if(w.isRemote) return false;
		
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
