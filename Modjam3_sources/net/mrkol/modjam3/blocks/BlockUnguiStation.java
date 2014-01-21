package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mrkol.modjam3.client.EntityPoisonFX;
import net.mrkol.modjam3.raytracing.Cuboid6f;
import net.mrkol.modjam3.raytracing.Raytracer;

public class BlockUnguiStation extends BlockContainer
{
	private List<Icon> icons = new ArrayList<Icon>();
	
	public BlockUnguiStation(int id, Material m)
	{
		super(id, m);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		if(par1World.isRemote) return; 
		TileEntity t = par1World.getBlockTileEntity(par2, par3, par4);

        if (t != null && t instanceof TileUnguiStation)
        {
        	TileUnguiStation tile = (TileUnguiStation)par1World.getBlockTileEntity(par2, par3, par4);
        	
        	
        	tile.onBreakBlock();
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	
	@Override
    public int damageDropped(int par1)
    {
        return par1;
    }

	@Override
	public TileEntity createNewTileEntity(World arg0)
	{
		return null;
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB AABB, List al, Entity e)
	{
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
		super.addCollisionBoxesToList(world, x, y, z, AABB, al, e);
	}
    
    @Override
	 public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack is)
	 {
		 TileEntity te = world.getBlockTileEntity(x, y, z);
		 
		 if(te != null && te instanceof TileUnguiStation)
		 {
			 TileUnguiStation tus = (TileUnguiStation)te;
			 
			 tus.onBlockPlaced(elb, is);
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
		TileEntity te = w.getBlockTileEntity(x, y, z);

	    double d = ((player instanceof EntityPlayerMP)) && (player.isSneaking()) ? 1.54D : player.worldObj.isRemote ? 0.0D : 1.62D;

	    Vec3 headVec = Vec3.createVectorHelper(player.posX, player.posY + d, player.posZ);
	    Vec3 lookVec = player.getLook(1.0F);
	    
	    double reach = w.isRemote ? Minecraft.getMinecraft().playerController.getBlockReachDistance() : ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
	    
	    Vec3 endVec = headVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
	    
	    this.collisionRayTrace(w, x, y, z, headVec, endVec);
	    
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
				if(this.blockMaterial == Material.rock) return new TileUnguiFurnace();
				if(this.blockMaterial == Material.wood) return new TileUnguiWorkbench();
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		if(this.blockMaterial == Material.wood) this.icons.add(ir.registerIcon("planks_oak"));
		if(this.blockMaterial == Material.rock) this.icons.add(ir.registerIcon("furnace_side"));
		EntityPoisonFX.ICON = ir.registerIcon("ungui:ring");
	}
	
	@Override
    public Icon getBlockTexture(IBlockAccess ba, int x, int y, int z, int side)
    {
        return this.icons.get(ba.getBlockMetadata(x, y, z));
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
        return this.icons.get(meta);
    }
}
