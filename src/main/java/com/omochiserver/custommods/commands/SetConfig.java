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
        if (!(sender.isOp()))
        {
            sender.sendMessage(ChatColor.RED +  "あなたには権限がありません");
        }
        if(args.length<2)
        {
            sender.sendMessage(ChatColor.RED + "引数が足りません。詳しくは /help setconfig を参照してください。");
            return true;
        }
        if(!(args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("nickname") || args[0].equalsIgnoreCase("explosion")))
        {
            sender.sendMessage(ChatColor.RED + "引数の1番目が間違っています。詳しくは /help setconfig を参照してください");
            return true;
        }
        if(!(args[1].equalsIgnoreCase("op") || args[1].equalsIgnoreCase("notop") || args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")
                || args[1].equalsIgnoreCase("sensing_radius") || args[1].equalsIgnoreCase("sensing_fire")))
        {
            sender.sendMessage(ChatColor.RED + "引数の2番目が間違っています。詳しくは /help setconfig を参照してください");
            return true;
        }
        FileConfiguration config = CustomMods.getPlugin().getConfig();
        if(args.length > 2 && (args[1].equalsIgnoreCase("sensing_radius") || args[1].equalsIgnoreCase("sensing_fire")))
        {
            if (args[1].equalsIgnoreCase("sensing_radius"))
            {


                String[] radius_array = args[2].split("-");
                for (String str:radius_array)
                {
                    try {
                        Float a =Float.valueOf(str);
                        if (a>10 || a<1)
                        {
                            a = Float.valueOf("a");
                        }
                    }catch(NumberFormatException nfex){
                        sender.sendMessage(ChatColor.RED + "引数の3番目が間違っています。詳しくは /help setconfigを参照してください。");
                        return true;
                }
                }
                sender.sendMessage(ChatColor.GREEN + "ブロック破壊を無効にする対象の爆発の大きさ(sensing_radius)を" + String.join(",",radius_array) + "に設定しました");
                config.set("sensing_radius",null);
                int count=1;
                for (String list:radius_array)
                {
                    config.set("sensing_radius.key" + count,new Float(list));
                    count++;
                }

            }else if (args[1].equalsIgnoreCase("sensing_fire")) {
                if (!(args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("false")  || args[2].equalsIgnoreCase("true")))
                {
                    sender.sendMessage(ChatColor.RED + "引数の3番目が間違っています。詳しくは /help setconfig を参照してください");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "ブロック破壊を無効にする対象の火の爆発(sensing_fire)を" + args[2] + "に変更しました");
                config.set("sensing_fire",args[2]);
            }
        }else{
            sender.sendMessage(ChatColor.GREEN + args[0] + "に必要な権限を" + args[1] + "に変更しました");
            config.set(args[0].toLowerCase(),args[1].toLowerCase());
        }



        CustomMods.getPlugin().saveConfig();
        CustomMods.getPlugin().reloadConfig();
        //Bukkit.reload();

        return true;
    }
}
