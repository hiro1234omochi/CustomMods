package com.omochiserver.custommods.commands;

import com.omochiserver.custommods.CustomMods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;

public class SetConfig implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length<2)
        {
            sender.sendMessage(ChatColor.RED + "引数が足りません。詳しくは /help setconfig を参照してください。");
            return true;
        }
        if(!(args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("nickname")))
        {
            sender.sendMessage(ChatColor.RED + "引数の1番目が間違っています。詳しくは /help setconfig を参照してください");
        }
        if(!(args[1].equalsIgnoreCase("op") || args[1].equalsIgnoreCase("not op") || args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")))
        {
            sender.sendMessage(ChatColor.RED + "引数の2番目が間違っています。詳しくは /help setconfig を参照してください");
        }

        FileConfiguration config = CustomMods.getPlugin().getConfig();
        config.set(args[0].toLowerCase(),args[1].toLowerCase());
        CustomMods.getPlugin().saveConfig();
        Bukkit.reload();
        return true;
    }
}
