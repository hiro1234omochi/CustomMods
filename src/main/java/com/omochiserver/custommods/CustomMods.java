package com.omochiserver.custommods;

import com.omochiserver.custommods.commands.SetConfig;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.omochiserver.custommods.commands.Fly;
import com.omochiserver.custommods.commands.Nickname;

public final class CustomMods extends JavaPlugin
{
    private static CustomMods plugin;
    @Override
    public void onEnable()
    {
        plugin = this;
        //config.yamlの読み込み
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // Plugin startup logic
        getCommand("fly").setExecutor(new Fly());
        getCommand("nickname").setExecutor(new Nickname());
        getCommand("setconfig").setExecutor(new SetConfig());

        // 起動出力
        getLogger().info("CustomModsが読み込まれました");
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
    public static CustomMods getPlugin()
    {
        return plugin;
    }
}
