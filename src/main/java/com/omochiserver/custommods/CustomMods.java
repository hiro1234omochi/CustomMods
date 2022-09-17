package com.omochiserver.custommods;

import com.omochiserver.custommods.commands.SetConfig;
import com.omochiserver.custommods.motion.ViewTps;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.omochiserver.custommods.commands.Fly;
import com.omochiserver.custommods.commands.Nickname;
import com.omochiserver.custommods.motion.ExplosionGuard;

import java.util.Calendar;

public final class CustomMods extends JavaPlugin
{
    private static CustomMods plugin;
    public static boolean is_view_tps_modify;
    public static int view_tps_update_tick;
    public static BossBar bar;
    @Override

    public void onEnable()
    {
        plugin = this;
        //config.yamlの読み込み
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        //設定されてない値を追加
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new ExplosionGuard(), this);
        // Plugin startup logic
        getCommand("fly").setExecutor(new Fly());
        getCommand("nickname").setExecutor(new Nickname());
        getCommand("setconfig").setExecutor(new SetConfig());

        //スケジューラー実行
        CustomMods.is_view_tps_modify=false;
        CustomMods.view_tps_update_tick=config.getInt("viewtps.update_tick");
        CustomMods.bar = Bukkit.createBossBar("tps", BarColor.YELLOW, BarStyle.SOLID);
        new ViewTps().runTaskTimer(this, 0,CustomMods.view_tps_update_tick);

        // 起動出力
        getLogger().info("CustomModsが読み込まれました");
    }

    @Override
    public void onDisable()
    {
        CustomMods.bar.removeAll();
        // Plugin shutdown logic
    }
    public static CustomMods getPlugin()
    {
        return plugin;
    }
}
