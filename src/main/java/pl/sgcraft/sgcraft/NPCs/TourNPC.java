package pl.sgcraft.sgcraft.NPCs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitScheduler;
import pl.sgcraft.sgcraft.Messages;
import pl.sgcraft.sgcraft.SGCraft;

import java.util.Timer;
import java.util.TimerTask;

public class TourNPC implements CommandExecutor, Listener {

    private static final String messages[] = {
            ChatColor.translateAlternateColorCodes('&', "&6&lWitaj na 9 edycji serwera SGCraft!"),
            ChatColor.translateAlternateColorCodes('&', "&6Minęło dużo czasu odkąd zaczęliśmy..."),
            ChatColor.translateAlternateColorCodes('&', "&6Pierwszą edycją serwera, była edycja 0"),
            ChatColor.translateAlternateColorCodes('&', "&6Grali na niej tylko osoby z team'u SG"),
            ChatColor.translateAlternateColorCodes('&', "&6Po trzeciej edycji dołączyło do nas wiele osób."),
            ChatColor.translateAlternateColorCodes('&', "&6Od tamtej pory serwer się bardzo rozwijał, jednak administracja ciągle poszukiwała czegoś nowego, innowacyjnego."),
            ChatColor.translateAlternateColorCodes('&', "&6Ta edycja jest czymś nowym dla tego serwera."),
            ChatColor.translateAlternateColorCodes('&', "&6Po raz pierwszy zostały dodane tu własne pluginy i mapy..."),
            ChatColor.translateAlternateColorCodes('&', "&6Pracowaliśmy dniami i nocami aby dodać własne rozwiązania i tak oto powstały..."),
            ChatColor.translateAlternateColorCodes('&', "&6- Waluta serwera,"),
            ChatColor.translateAlternateColorCodes('&', "&6- NPC, u których zarobicie pieniądze wykonując proste zadania,"),
            ChatColor.translateAlternateColorCodes('&', "&6- System sklepów dla użytkowników; Każdy z was będzie mógł wykupić swój stragan na spawnie,"),
            ChatColor.translateAlternateColorCodes('&', "&6- Eventy, które będą się odbywać co jakiś czas"),
            ChatColor.translateAlternateColorCodes('&', "&6- I wiele, wiele innych."),
            ChatColor.translateAlternateColorCodes('&', "&6Miłej zabawy.")
    };

    public static void spawnTour(Player player){
        NPCBuilder npcBuilder = new NPCBuilder("Przewodnik");
        npcBuilder.setSkin("SG_Jacob");
        npcBuilder.spawn(player.getLocation());
    }

    @EventHandler
    public void onTourClick(PlayerInteractEntityEvent event){

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();



        if(event.getRightClicked().getName().equalsIgnoreCase("Przewodnik")){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    private int i = 0;
                    @Override
                    public void run() {
                       if(i != messages.length){
                           player.sendMessage(messages[i]);
                           player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                           i++;
                       } else {
                           timer.cancel();
                       }
                    }
                }, 0, 1000 * 3);
            }
        } else {
            return;
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        if(commandSender instanceof Player){
            if(command.getName().equalsIgnoreCase("spawnprzewodnik")){
                if(player.hasPermission("sgcraft.spawnprzewodnik")){
                    spawnTour(player);
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