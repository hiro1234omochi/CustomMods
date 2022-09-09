package com.omochiserver.custommods.motion;


import com.omochiserver.custommods.CustomMods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.ExplosionPrimeEvent;
public class MobsGuard implements Listener
{

    @EventHandler

    public void onCreeperDoNotBreakBlock(ExplosionPrimeEvent e)

    {


        FileConfiguration config = CustomMods.getPlugin().getConfig();
        boolean decision=false;

        for (String key : config.getConfigurationSection("sensing_radius").getKeys(false)) {

            if (String.valueOf(config.getDouble("sensing_radius." + key)).equalsIgnoreCase(String.valueOf(e.getRadius())))
            {

                if ((String.valueOf(e.getFire()).equalsIgnoreCase(config.getString("sensing_fire"))) || "all".equalsIgnoreCase(config.getString("sensing_fire")))
                {
                    decision=true;
                }
            }
        }
        if (decision)
        {
            int power = (int)e.getRadius();
            e.setRadius(0);
            Location loc= e.getEntity().getLocation();
            double x=loc.getX();
            double y=loc.getY();
            double z=loc.getZ();
            loc.getWorld().createExplosion(x,y,z,power,false,false);

        }
    }


}
