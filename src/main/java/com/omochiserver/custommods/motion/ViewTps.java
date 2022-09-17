package com.omochiserver.custommods.motion;
import com.omochiserver.custommods.CustomMods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ViewTps extends BukkitRunnable{

    @Override
    public void run() {
        FileConfiguration config = CustomMods.getPlugin().getConfig();
        if (CustomMods.is_view_tps_modify) {
            CustomMods.is_view_tps_modify=false;
            new ViewTps().runTaskTimer(CustomMods.getPlugin(), CustomMods.view_tps_update_tick, CustomMods.view_tps_update_tick);
            return;
        }
        double tps=Bukkit.getTPS()[0];
        CustomMods.bar.removeAll();
        final String config_data = config.getString("viewtps.permission");
        boolean is_player_accept;
        for (Player p:Bukkit.getOnlinePlayers()){
            if (config_data.equalsIgnoreCase("op")){
                is_player_accept=p.isOp();
            } else if (config_data.equalsIgnoreCase("notop")) {
                is_player_accept=!p.isOp();
            } else if (config_data.equalsIgnoreCase("true")) {
                is_player_accept=true;
            } else if (config_data.equalsIgnoreCase("false")) {
                is_player_accept=false;
            } else{
                Bukkit.getLogger().info(ChatColor.RED+"config.ymlのviewtpsのpermissionタブの表記が不正です。true,false,op,notopから選択できます。");
                is_player_accept=false;
            }
            if (is_player_accept){
                CustomMods.bar.addPlayer(p);
            }
        }
        CustomMods.bar.setTitle(" "+CustomMods.view_tps_update_tick+"ティックごとに更新しています。 "+"tps:"+String.format("%.2f", tps));

    }
}
