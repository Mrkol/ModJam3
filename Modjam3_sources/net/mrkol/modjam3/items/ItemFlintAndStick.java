package net.mrkol.modjam3.items;

import java.util.List;
import org.lwjgl.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlintAndStick extends Item
{

	public ItemFlintAndStick(int par1)
	{
		super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(8);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.iconString = "ungui:FlingAndStick";
	}
	
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    	{
    		par3List.add("\u00A7oPress the \u00A7a<SNEAK KEY>\u00A77\u00A7o for more info.");
    	}
    	else
    	{
    		par3List.add("\u00A7oYou've managed to create");
    		par3List.add("\u00A7oa cheap spark making device!");
    		par3List.add("\u00A7oWon't last for very long though.");
    		par3List.add("\u00A7oThis also can be used to");
    		par3List.add("\u00A7l\u00A7mBURN 5#|7 WITH FIRE");
    		par3List.add("\u00A7oignite things.");
    	}
    }
	
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par3World.isRemote) return true;
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            if (par3World.isAirBlock(par4, par5, par6) && par3World.rand.nextInt(3) == 0)
            {
                par3World.setBlock(par4, par5, par6, Block.fire.blockID);
            }

            par1ItemStack.damageItem(1, par2EntityPlayer);
            return true;
        }
    }
}
