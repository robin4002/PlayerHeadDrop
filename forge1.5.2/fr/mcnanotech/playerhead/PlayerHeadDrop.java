package fr.mcnanotech.playerhead;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "PlayerHeadDrop", name = "Player Head Drop", version = "1.0.1")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)

public class PlayerHeadDrop
{
	public static boolean pvponly;
	public static int dropchance;
	
	@PreInit
	public void PreLoad(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			pvponly = cfg.get(cfg.CATEGORY_GENERAL, "pvp only", false).getBoolean(false);
			dropchance = cfg.get(cfg.CATEGORY_GENERAL, "drop chance", 50).getInt();
			Property dropchance1 = cfg.get(cfg.CATEGORY_GENERAL, "drop chance", "");
			dropchance1.comment = "It's in percentage";
		}
		catch(Exception ex)
		{
			event.getModLog().log(Level.SEVERE, "Failed to load config");
		}
        finally
		{
        	if(cfg.hasChanged())
        		cfg.load();
		}
	}
	
	@Init
	public void Load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
    @ForgeSubscribe
    public void onEntityLivingDeath(LivingDeathEvent event)
    {
    	Random rand = new Random();
    	if(event.entityLiving instanceof EntityPlayer)
    	{
    		boolean dodrop = true;
    		if(pvponly && !event.source.equals("player"))
    		{
    			dodrop = false;
    		}
    		
    		double chance = rand.nextDouble() * 100;
    		if((dodrop && chance <= dropchance))
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
}
