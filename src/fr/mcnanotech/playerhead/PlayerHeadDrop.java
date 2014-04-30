package fr.mcnanotech.playerhead;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "playerheaddrop", name = "Player Head Drop", version = "1.1.0")
public class PlayerHeadDrop
{
	public static boolean pvpOnly;
	public static int dropchance;

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			pvpOnly = cfg.get(cfg.CATEGORY_GENERAL, "pvp only", false, "If true, player will only drop its head if it's was kill by an other player").getBoolean(false);
			dropchance = cfg.get(cfg.CATEGORY_GENERAL, "drop chance", 50, "It's in percentage").getInt();
		}
		catch(Exception ex)
		{
			event.getModLog().error("Failed to load config");
		}
		finally
		{
			if(cfg.hasChanged())
			{
				cfg.save();
			}
		}
	}

	@EventHandler
	public void Load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onEntityLivingDeath(LivingDeathEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			if((pvpOnly && event.source.equals("player") || !pvpOnly) && event.entityLiving.worldObj.rand.nextDouble() * 100 <= dropchance)
			{
				ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
				playerHead.setTagCompound(new NBTTagCompound());
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				String playerName = player.getCommandSenderName();
				playerHead.getTagCompound().setString("SkullOwner", playerName);
				event.entityLiving.entityDropItem(playerHead, 0.5F);
			}
		}
	}
}