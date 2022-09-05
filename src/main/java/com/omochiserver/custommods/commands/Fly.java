package com.omochiserver.custommods.commands;

import com.omochiserver.custommods.CustomMods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class Fly implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        //権限確認
        FileConfiguration config = CustomMods.getPlugin().getConfig();
        boolean result = false;
        String config_fly = config.getString("fly");
        if (config_fly.equalsIgnoreCase("op")){
            result = !sender.isOp();
        } else if (config_fly.equalsIgnoreCase("notop")) {
            result = sender.isOp();
        } else if (config_fly.equalsIgnoreCase("true")) {
            result = false;
        } else if (config_fly.equalsIgnoreCase("false")) {
            result = true;
        }else{
            sender.sendMessage(ChatColor.RED + "plugin.ymlのFlyタブの表記が異なります。指定できる値はtrue,false,op,notopの4つです。");
            return true;
        }

        if (result)
        {
            sender.sendMessage(ChatColor.RED + "あなたには権限がありません");

            return true;
        }
        if (args.length != 0)
        {


            if (Bukkit.getPlayerExact(args[0])== null)
            {
                sender.sendMessage(ChatColor.RED+"指定したプレイヤーはいません");
                return true;
            }

            final Player p = Bukkit.getPlayerExact(args[0]);
            if (!(p.getGameMode()== GameMode.SURVIVAL || p.getGameMode()==GameMode.ADVENTURE))
            {
                sender.sendMessage(ChatColor.RED + args[0] + "はゲームモードがクリエイティブまたはスペクテイターモードのため実行できません");
                return true;
            }
            if (p.getAllowFlight())
            {
                p.setAllowFlight(false);
            }else{
                p.setAllowFlight(true);
            }

            sender.sendMessage(ChatColor.GREEN + args[0] + "はフライモードが" + p.getAllowFlight() + "になりました");
            p.sendMessage(ChatColor.GREEN+ "貴方はフライモードが"+p.getAllowFlight() +"になりました");
            return true;

        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "コンソールからは実行できません");
            return true;
        }
        final Player p = (Player) sender;
        if (!(p.getGameMode()== GameMode.SURVIVAL || p.getGameMode()==GameMode.ADVENTURE))
        {
            sender.sendMessage(ChatColor.RED + "ゲームモードがクリエイティブまたはスペクテイターモードのため実行できません");
            return true;
        }
        if (p.getAllowFlight())
        {
            p.setAllowFlight(false);
            sender.sendMessage(ChatColor.RED + "flyモードが無効化されました");
            return true;
        }
        p.setAllowFlight(true);
        sender.sendMessage(ChatColor.GREEN + "flyモードが有効化されました");

        return true;
    }

}

