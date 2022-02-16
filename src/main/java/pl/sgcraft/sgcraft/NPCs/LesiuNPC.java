package pl.sgcraft.sgcraft.NPCs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.sgcraft.sgcraft.Messages;
import pl.sgcraft.sgcraft.Utils.MoneyManager;

import java.util.Timer;
import java.util.TimerTask;

public class LesiuNPC implements CommandExecutor, Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){

        Player player = event.getPlayer();
        if(event.getItem().getType() == Material.POTION){
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 0, 0));
        }
    }

    private static final String messages[] = {
            ChatColor.translateAlternateColorCodes('&', "&6Kiedy byłem mały..."),
            ChatColor.translateAlternateColorCodes('&', "&9chlip chlip"),
            ChatColor.translateAlternateColorCodes('&', "&6Koledzy &c&o[REDACTED] &6mnie w &c&o[REDACTED]")
    };

    public static void spawnLesiu(Player player){
        NPCBuilder npcBuilder = new NPCBuilder("Lesiu");
        npcBuilder.setSkin("SG_Lechu");
        npcBuilder.spawn(player.getLocation());
    }

    @EventHandler
    public void onLesiuClick(PlayerInteractEntityEvent event) throws InterruptedException {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(event.getRightClicked().getName().equalsIgnoreCase("Lesiu")){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    private int i = 0;
                    @Override
                    public void run() {
                        if(i != messages.length){
                            player.sendMessage(messages[i]);
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
                            i++;
                        } else {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 0, 1000 * 2);
                Thread.sleep(1000L * 5);
                player.openInventory(inventory(player));
            }
        } else {
            return;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null){

            Inventory inventory = event.getInventory();
            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();

            if(event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&0Lesiu"))){
                if(itemStack.getType() == Material.POTION) {
                    buyKuflowe(player);
                    event.setCancelled(true);
                }
            } else {
                return;
            }
        }
    }

    private static Inventory inventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&0Lesiu"));
        pl.sgcraft.sgcraft.Utils.Inventory.frame(inv);

        inv.setItem(4, pl.sgcraft.sgcraft.Utils.Inventory.headInHunterGUI(player));
        inv.setItem(10, pl.sgcraft.sgcraft.Utils.Inventory.kufloweMocne(player));
        inv.setItem(49, pl.sgcraft.sgcraft.Utils.Inventory.barrier());
        return inv;
    }

    public static void buyKuflowe(Player player){
        MoneyManager.removeMoney(player, 5);
        player.getInventory().addItem(pl.sgcraft.sgcraft.Utils.Inventory.kufloweMocne(player));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        if(commandSender instanceof Player){
            if(command.getName().equalsIgnoreCase("spawnlesiu")){
                if(player.hasPermission("sgcraft.spawnlesiu")){
                    spawnLesiu(player);
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