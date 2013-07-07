package fr.mcnanotech.playerhead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "PlayerHead", name = "Player Head", version = "1.0.0")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)

public class PlayerHead
{
	@EventHandler
	public void Load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PlayerHeadDrop());
	}
}
