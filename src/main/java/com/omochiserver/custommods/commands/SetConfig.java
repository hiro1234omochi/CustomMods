package com.omochiserver.custommods.commands;

import com.omochiserver.custommods.CustomMods;
import com.omochiserver.custommods.motion.ViewTps;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitScheduler;
import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetConfig implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender.isOp()))
        {
            sender.sendMessage(ChatColor.RED +  "あなたには権限がありません");
            return true;
        }
        if(args.length<2)
        {
            sender.sendMessage(ChatColor.RED + "引数が足りません。詳しくは /help setconfig を参照してください。");
            return true;
        }
        if(!(args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("nickname") || args[0].equalsIgnoreCase("explosion") || args[0].equalsIgnoreCase("viewtps")))
        {
            sender.sendMessage(ChatColor.RED + "引数の1番目が間違っています。詳しくは /help setconfig を参照してください");
            return true;
        }
        if(!(args[1].equalsIgnoreCase("op") || args[1].equalsIgnoreCase("notop") || args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")
                || args[1].equalsIgnoreCase("sensing_radius") || args[1].equalsIgnoreCase("sensing_fire") || args[1].equalsIgnoreCase("permission") || args[1].equalsIgnoreCase("update_tick")))
        {
            sender.sendMessage(ChatColor.RED + "引数の2番目が間違っています。詳しくは /help setconfig を参照してください");
            return true;
        }
        FileConfiguration config = CustomMods.getPlugin().getConfig();
        if(args.length > 2 && args[0].equalsIgnoreCase("explosion") &&(args[1].equalsIgnoreCase("sensing_radius") || args[1].equalsIgnoreCase("sensing_fire"))) {
            if (args[1].equalsIgnoreCase("sensing_radius")) {


                String[] radius_array = args[2].split("-");
                for (String str : radius_array) {
                    try {
                        Float a = Float.valueOf(str);
                        if (a > 10 || a < 1) {
                            a = Float.valueOf("a");
                        }
                    } catch (NumberFormatException nfex) {
                        sender.sendMessage(ChatColor.RED + "引数の3番目が間違っています。詳しくは /help setconfigを参照してください。");
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "ブロック破壊を無効にする対象の爆発の大きさ(sensing_radius)を" + String.join(",", radius_array) + "に設定しました");
                config.set("sensing_radius", null);
                int count = 1;
                for (String list : radius_array) {
                    config.set("sensing_radius.key" + count, new Float(list));
                    count++;
                }

            } else if (args[1].equalsIgnoreCase("sensing_fire")) {
                if (!(args[2].equalsIgnoreCase("all") || args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("null"))) {
                    sender.sendMessage(ChatColor.RED + "引数の3番目が間違っています。詳しくは /help setconfig を参照してください");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "ブロック破壊を無効にする対象の火の爆発(sensing_fire)を" + args[2] + "に変更しました");
                config.set("sensing_fire", args[2]);
            }
        } else if (args.length > 2 && args[0].equalsIgnoreCase("viewtps") && (args[1].equalsIgnoreCase("permission") || args[1].equalsIgnoreCase("update_tick"))) {
            if (args[1].equalsIgnoreCase("permission")){
                if (!(args[2].equalsIgnoreCase("op") || args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("notop"))){
                    sender.sendMessage(ChatColor.RED + "引数の3番目が間違っています。詳しくは /help setconfig を参照してください");
                    return true;
                }
                config.set("viewtps.permission",args[2]);
                sender.sendMessage(ChatColor.GREEN+"tpsを表示できるプレイヤーの種類(permission)を"+args[2]+"に変更しました");
            } else if (args[1].equalsIgnoreCase("update_tick")) {
                if(NumberUtils.isNumber(args[2])){
                    if(Integer.parseInt(args[2])>4){
                        config.set("viewtps.update_tick",Integer.parseInt(args[2]));
                        CustomMods.view_tps_update_tick=Integer.parseInt(args[2]);
                        CustomMods.is_view_tps_modify=true;
                        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                        scheduler.cancelTasks(CustomMods.getPlugin());
                        new ViewTps().runTask(CustomMods.getPlugin());
                        sender.sendMessage(ChatColor.GREEN+"tpsを更新するティック(update_tick)を"+args[2]+"に変更しました");
                    }else{
                        sender.sendMessage(ChatColor.RED+"引数の3番目が間違っています。詳しくは/help setconfigを参照してください。");
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.RED+"引数の3番目が間違っています。詳しくは/help setconfigを参照してください。");
                    return true;
                }
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
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setconfig")){
            if (args.length == 1) {
                if (args[0].length() == 0) { // /setconfigまで
                    return Arrays.asList("fly","nickname","explosion","viewtps");
                    
                } else {
                    if ("fly".startsWith(args[0]) && "nickname".startsWith(args[0]) && "explosion".startsWith(args[0]) && "viewtps".startsWith(args[0])) {
                        return Arrays.asList("fly","nickname","explosion","viewtps");
                    }
                    else if("fly".startsWith(args[0])){
                        return Collections.singletonList("fly");
                    }
                    else if("nickname".startsWith(args[0])){
                        return Collections.singletonList("nickname");
                    }
                    else if("explosion".startsWith(args[0])){
                        return Collections.singletonList("explosion");
                    }
                    else if("viewtps".startsWith(args[0])){
                        return Collections.singletonList("viewtps");
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("fly")) 
                {
                    if (args[1].length() == 0)
                    {
                        return Arrays.asList("true","false","op","notop");
                    } else{
                        if ("true".startsWith(args[1]) && "false".startsWith(args[1]) && "op".startsWith(args[1]) && "notop".startsWith(args[1]))
                        {
                            return Arrays.asList("true","false","op","notop");
                        } else if ("true".startsWith(args[1])) {
                            return Collections.singletonList("true");
                        } else if ("false".startsWith(args[1])) { 
                            return Collections.singletonList("false");
                        } else if ("op".startsWith(args[1])) {
                            return Collections.singletonList("op");
                        } else if ("not".startsWith(args[1])) {
                            return Collections.singletonList("notop");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("nickname")) {
                    if (args[1].length() == 0)
                    {
                        return Arrays.asList("true","false","op","notop");
                    } else{
                        if ("true".startsWith(args[1]) && "false".startsWith(args[1]) && "op".startsWith(args[1]) && "notop".startsWith(args[1]))
                        {
                            return Arrays.asList("true","false","op","notop");
                        } else if ("true".startsWith(args[1])) {
                            return Collections.singletonList("true");
                        } else if ("false".startsWith(args[1])) {
                            return Collections.singletonList("false");
                        } else if ("op".startsWith(args[1])) {
                            return Collections.singletonList("op");
                        } else if ("not".startsWith(args[1])) {
                            return Collections.singletonList("notop");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("explosion")) {
                    if (args[1].length() == 0)
                    {
                        return Arrays.asList("sensing_fire","sensing_radius");
                    }else {
                        if ("sensing_fire".startsWith(args[1]) && "sensing_radius".startsWith(args[1])) {
                            return Arrays.asList("sensing_fire","sensing_radius");
                        } else if ("sensing_fire".startsWith(args[1])) {
                            return Collections.singletonList("sensing_fire");
                        } else if ("sensing_radius".startsWith(args[1])) {
                            return Collections.singletonList("sensing_radius");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("viewtps")){
                    if (args[1].length() == 0)
                    {
                        return Arrays.asList("update_tick","permission");
                    }else{
                        if ("update_tick".startsWith(args[1]) && "permission".startsWith(args[1])) {
                            return Arrays.asList("update_tick","permission");
                        } else if ("update_tick".startsWith(args[1])) {
                            return Collections.singletonList("update_tick");
                        } else if ("permission".startsWith(args[1])) {
                            return Collections.singletonList("permission");
                        }
                    }
                }
            } else if (args.length == 3) {
                if(args[0].equalsIgnoreCase("explosion")){
                    if(args[1].equalsIgnoreCase("sensing_radius")){
                        if (args[2].length() == 0){
                            return Arrays.asList("3-4","3.0-4.0-9.0");
                        }
                    } else if (args[1].equalsIgnoreCase("sensing_fire")) {
                        if (args[2].length() == 0){
                            return Arrays.asList("true","false","all","null");
                        }else{
                            if ("true".startsWith(args[2]) && "false".startsWith(args[2]) && "all".startsWith(args[2]) && "null".startsWith(args[2])) {
                                return Arrays.asList("false","true","all","null");
                            } else if ("true".startsWith(args[2])) {
                                return Collections.singletonList("true");
                            } else if ("false".startsWith(args[2])) {
                                return Collections.singletonList("false");
                            } else if ("all".startsWith(args[2])) {
                                return Collections.singletonList("all");
                            } else if ("null".startsWith(args[2])) {
                                return Collections.singletonList("null");
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("viewtps")) {
                    if(args[1].equalsIgnoreCase("update_tick")){
                        if (args[2].length() == 0){
                            return Arrays.asList("5","10","20","50");
                        }
                    } else if (args[1].equalsIgnoreCase("permission")) {
                        if (args[2].length() == 0){
                            return Arrays.asList("true","false","op","notop");
                        }else{
                            if ("true".startsWith(args[2]) && "false".startsWith(args[2]) && "op".startsWith(args[2]) && "notop".startsWith(args[2])) {
                                return Arrays.asList("false","true","op","notop");
                            } else if ("true".startsWith(args[2])) {
                                return Collections.singletonList("true");
                            } else if ("false".startsWith(args[2])) {
                                return Collections.singletonList("false");
                            } else if ("op".startsWith(args[2])) {
                                return Collections.singletonList("op");
                            } else if ("notop".startsWith(args[2])) {
                                return Collections.singletonList("notop");
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
