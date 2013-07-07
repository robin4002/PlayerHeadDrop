package fr.mcnanotech.playerhead;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "PlayerHead", name = "Player Head", version = "1.0.0")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)

public class PlayerHead
{
	@Init
	public void Load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PlayerHeadDrop());
	}
}
