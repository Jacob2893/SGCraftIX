package pl.sgcraft.sgcraft.NPCs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.sgcraft.sgcraft.Messages;

import javax.swing.*;
import java.util.HashMap;

public class JacobNPC implements CommandExecutor, Listener {

    public static void spawnJacob(Player player){
        NPCBuilder npcBuilder = new NPCBuilder("Jacob");
        npcBuilder.setSkin("SG_Jacob");
        npcBuilder.spawn(player.getLocation());
    }

    @EventHandler
    public void onJacobClick(PlayerInteractEntityEvent event){

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();



        if(event.getRightClicked().getName().equalsIgnoreCase("Jacob")){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Siema!\n&cJestem Jacob\n&aW czym mogę ci pomóc?"));
            }
        } else {
            return;
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        if(commandSender instanceof Player){
            if(command.getName().equalsIgnoreCase("spawnjacob")){
                if(player.hasPermission("sgcraft.spawnjacob")){
                        spawnJacob(player);
                } else {
                    player.sendMessage(Messages.consoleErrorPrefix + "Nie masz uprawnień");
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Messages.consoleErrorPrefix + "Nah...");
        }
        return false;
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