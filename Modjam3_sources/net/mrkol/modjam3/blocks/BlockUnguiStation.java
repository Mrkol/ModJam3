package net.mrkol.modjam3.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.Raytracer;

public class BlockUnguiStation extends BlockContainer
{

	public BlockUnguiStation(int id)
	{
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0)
	{
		return null;
	}

	 public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack is)
	 {
		 TileEntity te = world.getBlockTileEntity(x, y, z);
		 
		 if(te instanceof TileUnguiFurnace)
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
			if(te instanceof TileUnguiStation)
			{
				TileUnguiStation tus = (TileUnguiStation)te;
				
				
				return Raytracer.traceCuboids(s, e, tus.cuboids, x, y, z, tus.getBlockType());
			}

			return null;
	 }
	 
	 
	 @Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(w.isRemote) return true;
		
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
