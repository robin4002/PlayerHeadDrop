package fr.mcnanotech.playerhead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class PlayerHeadDrop
{
    @ForgeSubscribe
    public void onEntityLivingDeath(LivingDeathEvent event)
    {
    	if(event.entityLiving instanceof EntityPlayer)
    	{
    		ItemStack playerHead = new ItemStack(Item.skull, 1, 3);
    		playerHead.setTagCompound(new NBTTagCompound());
    		EntityPlayer player = (EntityPlayer)event.entityLiving;
    		String playerName = player.getEntityName();
    		playerHead.getTagCompound().setString("SkullOwner", playerName);
    		event.entityLiving.entityDropItem(playerHead, 0.5F);
    	}
    }
}
