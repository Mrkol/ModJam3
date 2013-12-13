package net.mrkol.modjam3.blocks;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.mrkol.modjam3.raytracing.*;


public class TileUnguiStation extends TileEntity
{
	public List<Cuboid6f> cuboids = new ArrayList<Cuboid6f>();
	
	public boolean onActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		Block b = Block.blocksList[w.getBlockId(x, y, z)];
		if(b == null) return false;
		double d = (player instanceof EntityPlayerMP) && (player.isSneaking()) ? 1.54D  : 1.62D;
		Vec3 hvec = Vec3.createVectorHelper(player.posX, player.posY + d, player.posZ);
		Vec3 lvec = player.getLook(1.0F);
		double rad = w.isRemote ? FMLClientHandler.instance().getClient().playerController.getBlockReachDistance() : ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
		Vec3 evec = hvec.addVector(lvec.xCoord * rad, lvec.yCoord * rad, lvec.zCoord * rad);
		MovingObjectPosition mop = b.collisionRayTrace(w, x, y, z, hvec, evec);
		
		return false;
	}
}
