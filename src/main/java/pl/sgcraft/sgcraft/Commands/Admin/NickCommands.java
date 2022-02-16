package pl.sgcraft.sgcraft.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.sgcraft.sgcraft.Messages;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NickCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("nick")){
                if(player.hasPermission("sgcraft.nick")){
                    if(strings.length != 0){
                        if(strings[0].equalsIgnoreCase("set")){
                            if(strings.length <= 1){
                                player.sendMessage(Messages.consoleErrorPrefix + "Nie podano argumentu");
                            } else {
                                player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', strings[1]));
                                changeName(ChatColor.translateAlternateColorCodes('&', strings[1]), player);
                            }
                        } else if(strings[0].equalsIgnoreCase("remove")){
                            player.setPlayerListName(player.getName());
                            changeName(player.getName(), player);
                        }
                    } else {
                        player.sendMessage(Messages.consoleErrorPrefix + "Nie podano argumentu");
                    }
                } else {
                    player.sendMessage(Messages.consoleErrorPrefix + "Nie masz uprawnień");
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Messages.consoleErrorPrefix + "Nah...");
        }
        return true;
    }

    public static void changeName(String name, Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle",
                    (Class<?>[]) null);
            try {
                Class.forName("com.mojang.authlib.GameProfile");
            } catch (ClassNotFoundException e) {
                return;
            }
            Object profile = getHandle.invoke(player).getClass()
                    .getMethod("getProfile")
                    .invoke(getHandle.invoke(player));
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, name);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
                players.showPlayer(player);
            }
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

/*
 * +-------------------------------+
 * | §0	black                      |
 * | §1	dark_blue                  |
 * | §2	dark_green                 |
 * | §3	dark_aqua                  |
 * | §4	dark_red                   |
 * | §5	dark_purple                |
 * | §6	gold                       |
 * | §7	gray                       |
 * | §8	dark_gray                  |
 * | §9	blue                       |
 * | §a	green                      |
 * | §b	aqua                       |
 * | §c	red                        |
 * | §d	light_purple               |
 * | §e	yellow                     |
 * | §f	white                      |
 * |                               |
 * | §k	Obfuscated (MAGIC)         |
 * | §l	Bold                       |
 * | §m	Strikethrough              |
 * | §n	Underline                  |
 * | §o	Italic                     |
 * | §r	Reset                      |
 * +-------------------------------+
 */