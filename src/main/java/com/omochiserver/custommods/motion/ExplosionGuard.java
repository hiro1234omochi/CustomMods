package com.omochiserver.custommods.motion;


import com.omochiserver.custommods.CustomMods;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
public class ExplosionGuard implements Listener
{

    @EventHandler

    public void onCreeperDoNotBreakBlock(ExplosionPrimeEvent e)

    {


        FileConfiguration config = CustomMods.getPlugin().getConfig();
        boolean decision=false;
        //エラーがあるか確認
        for (String key : config.getConfigurationSection("sensing_radius").getKeys(false))
        {
            if (config.isString("sensing_radius." + key) || config.getDouble("sensing_radius." + key) >10 || config.getDouble("sensing_radius." + key) <1)
            {
                CustomMods.getPlugin().getLogger().info("config.ymlのsensing_radiusの" + key + "の値が不正です。1~10の間の少数または整数が指定できます。");
            }
        }
        if (!(config.getString("sensing_fire").equalsIgnoreCase("true") || config.getString("sensing_fire").equalsIgnoreCase("false")
        || config.getString("sensing_fire").equalsIgnoreCase("all") || config.getString("sensing_fire").equalsIgnoreCase("null")))
        {
            CustomMods.getPlugin().getLogger().info("config.ymlのsensing_fireタグの値が不正です。true,false,allから選択できます。");
        }
        for (String key : config.getConfigurationSection("sensing_radius").getKeys(false)) {

            if (String.valueOf(config.getDouble("sensing_radius." + key)).equalsIgnoreCase(String.valueOf(e.getRadius())))
            {

                if ((String.valueOf(e.getFire()).equalsIgnoreCase(config.getString("sensing_fire"))) || "all".equalsIgnoreCase(config.getString("sensing_fire")) && (!("all".equalsIgnoreCase(config.getString("sensing_fire")))))
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
