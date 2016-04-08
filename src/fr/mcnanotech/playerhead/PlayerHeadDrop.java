package fr.mcnanotech.playerhead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "playerheaddrop", name = "Player Head Drop", version = "1.2.0", acceptedMinecraftVersions = "[1.9]")
public class PlayerHeadDrop
{
    public static boolean pvpOnly;
    public static int dropchance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        try
        {
            cfg.load();
            pvpOnly = cfg.get(cfg.CATEGORY_GENERAL, "pvp only", false, "If true, player will only drop its head if it's was kill by an other player").getBoolean(false);
            dropchance = cfg.get(cfg.CATEGORY_GENERAL, "drop chance", 100, "It's in percentage").getInt();
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
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityLivingDeath(LivingDeathEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer)
        {
            if((pvpOnly && event.getSource().getSourceOfDamage() instanceof EntityPlayer || !pvpOnly) && event.getEntityLiving().worldObj.rand.nextDouble() * 100 <= dropchance)
            {
                ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
                playerHead.setTagCompound(new NBTTagCompound());
                EntityPlayer player = (EntityPlayer)event.getEntityLiving();
                NBTTagCompound nbtTag = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbtTag, player.getGameProfile());
                playerHead.getTagCompound().setTag("SkullOwner", nbtTag);
                event.getEntityLiving().entityDropItem(playerHead, 0.5F);
            }
        }
    }
}