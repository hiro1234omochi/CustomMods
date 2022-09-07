package com.omochiserver.custommods.motion;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
public class MobsGuard implements Listener
{

    @EventHandler

    public void onCreeperDoNotBreakBlock(ExplosionPrimeEvent e)

    {

        if (!(e.getFire()) && e.getRadius()==3.0)
        {
            e.setRadius(0);
            Location loc= e.getEntity().getLocation();
            double x=loc.getX();
            double y=loc.getY();
            double z=loc.getZ();
            loc.getWorld().createExplosion(x,y,z,3,false,false);

        }
    }
}
