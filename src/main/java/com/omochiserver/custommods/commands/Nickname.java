package com.omochiserver.custommods.commands;

import com.omochiserver.custommods.CustomMods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Nickname implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = CustomMods.getPlugin().getConfig();
        boolean result = false;
        String config_fly = config.getString("nickname");
        if (config_fly.equalsIgnoreCase("op")){
            result = !sender.isOp();
        } else if (config_fly.equalsIgnoreCase("not op")) {
            result = sender.isOp();
        } else if (config_fly.equalsIgnoreCase("true")) {
            result = false;
        } else if (config_fly.equalsIgnoreCase("false")) {
            result = true;
        }else{
            sender.sendMessage(ChatColor.RED + "plugin.ymlのNicknameタブの表記が異なります。指定できる値はtrue,false,op,not opの4つです。");
        }

        if (result)
        {
            sender.sendMessage(ChatColor.RED + "あなたには権限がありません");

            return true;
        }

        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "コンソールからは実行できません");
            return true;
        }
        if (args.length==0)
        {
            sender.sendMessage(ChatColor.RED + "ニックネームを入力してください");
            return true;

        }
        args[0]=args[0]+"&r";
        if (args[0].length() > 32)
        {
            sender.sendMessage(ChatColor.RED + "文字コードを含めて30文字以内にしてください");
            return true;
        }
        final Player p = (Player) sender;
        final String name = ChatColor.translateAlternateColorCodes('&',args[0]);
        p.setDisplayName(name);
        p.setPlayerListName(name);

        sender.sendMessage(ChatColor.GREEN + "あなたのニックネームは"+ name + "となりました");

        return true;
    }
}