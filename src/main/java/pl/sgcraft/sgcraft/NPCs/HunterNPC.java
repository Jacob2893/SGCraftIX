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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.sgcraft.sgcraft.Database.UsersTable;
import pl.sgcraft.sgcraft.Messages;
import pl.sgcraft.sgcraft.Utils.MoneyManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HunterNPC implements CommandExecutor, Listener {

    private static String dialogue[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lWitaj nieznajomy!"),
            ChatColor.translateAlternateColorCodes('&', "&4Jestem Hunter"),
            ChatColor.translateAlternateColorCodes('&', "&4Jeśli chciałbyś szybko zarobić, to mam coś dla ciebie"),
            ChatColor.translateAlternateColorCodes('&', "&4Co jakiś czas wpadaj do mnie, a ja zlece ci zabójstwo innego gracza"),
            ChatColor.translateAlternateColorCodes('&', "&4Przynieś mi jego głowę, a ja zapłacę"),
            ChatColor.translateAlternateColorCodes('&', "&4Proste, czyż nie?"),
            ChatColor.translateAlternateColorCodes('&', "&4Im więcej zleceń ukończysz tym wyższą pozycję społeczną uzyskasz"),
            ChatColor.translateAlternateColorCodes('&', "&4To samo tyczy się innych..."),
            ChatColor.translateAlternateColorCodes('&', "&4Jeżeli dostaniesz zlecenie na kogoś kto dla mnie pracował, nagroda zostanie zwiększona wraz z jego pozycją"),
            ChatColor.translateAlternateColorCodes('&', "&4Osoby z aktywnymi zleceniami mają specjlany znacznik obok nicku, abyś wiedział na kogo uważać.")
    };

    private static String darkShop[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lZdobyłeś moje zaufanie."),
            ChatColor.translateAlternateColorCodes('&', "&4&lMam znajomego w opuszczonej chacie."),
            ChatColor.translateAlternateColorCodes('&', "&4&lZajrzyj do niego jak będziesz mieć czas..."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPosiada on nielegalny towar, który ułatwi ci zadanie."),
    };

    public static void spawnHunter(Player player){
        NPCBuilder npcBuilder = new NPCBuilder("Hunter");
        npcBuilder.setSkin("MrHunterBright");
        npcBuilder.spawn(player.getLocation());
    }

    public static void removeHunter(){
        NPCBuilder.npcs.get("Hunter").remove();
    }

    @EventHandler
    public void onHunterClick(PlayerInteractEntityEvent event){

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();


        if(event.getRightClicked().getName().equalsIgnoreCase("Hunter")){
            if(UsersTable.get(player, "hunter").toString().equalsIgnoreCase("false")){
                if(event.getHand().equals(EquipmentSlot.HAND)) {
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        private int i = 0;

                        @Override
                        public void run() {
                            if (i != dialogue.length) {
                                player.sendMessage(dialogue[i]);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                i++;
                            } else {
                                timer.cancel();
                            }
                        }
                    }, 0, 1000 * 3);
                    UsersTable.update(player, "hunter", "true");
                    player.openInventory(inventory(player));
                }
            } else {
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

            if(event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&0Hunter"))){
                if(itemStack.getType() == Material.IRON_SWORD) {
                    takeOrder(player);
                    event.setCancelled(true);
                } else if(itemStack.getType() == Material.GOLD_BLOCK) {
                    getReward(player);
                    event.setCancelled(true);
                }
            } else {
                return;
            }
        }
    }

    private static Inventory inventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&0Hunter"));

        pl.sgcraft.sgcraft.Utils.Inventory.frame(inv);

        inv.setItem(21, pl.sgcraft.sgcraft.Utils.Inventory.takeOrderItemStack(player));
        inv.setItem(23, pl.sgcraft.sgcraft.Utils.Inventory.getRewardItemStack(player));
        inv.setItem(4, pl.sgcraft.sgcraft.Utils.Inventory.headInHunterGUI(player));
        inv.setItem(49, pl.sgcraft.sgcraft.Utils.Inventory.barrier());
        return inv;
    }

    public static void takeOrder(Player player) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        if(timeFormat.format(UsersTable.get(player, "hunterCountdown")).equalsIgnoreCase("00:00:00")){
            if(UsersTable.get(player, "hunterOrder").toString().equalsIgnoreCase("NULL")){
                ArrayList<String> allPlayers = new ArrayList<String>();
                String randomPlayerName = null;
                boolean rand = true;
                for(Player players : Bukkit.getOnlinePlayers()) {
                    allPlayers.add(players.getName());
                }
                while(rand) {
                    int random = new Random().nextInt(allPlayers.size());
                    randomPlayerName = allPlayers.get(random);
                    if(randomPlayerName != player.getName()){
                        rand = false;
                    }
                }
                player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', player.getName() + " &4✛"));
                UsersTable.update(player, "hunterOrder", Bukkit.getPlayer(randomPlayerName).getUniqueId().toString());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Twoim celem jest &f" + randomPlayerName + "&4. Wróć tu z głową, a dostaniesz nagrodę."));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Masz już przyjęte zlecenie. Idź i je wykonaj..."));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Na razie nie mam dla ciebie zleceń..."));
        }
    }

    public static void getReward(Player player){

        int reward = 0;
        if(!UsersTable.get(player, "hunterOrder").toString().equalsIgnoreCase("NULL")){
            boolean contains = false;
            for(int i = 0; i <= player.getInventory().getSize(); i++){
                if(player.getInventory().getItem(i) != null) {
                    if (player.getInventory().getItem(i).getType() == Material.PLAYER_HEAD) {
                        if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&4&l" + Bukkit.getOfflinePlayer(UUID.fromString(UsersTable.get(player, "hunterOrder").toString())).getName()))) {
                            ItemStack itemStack = player.getInventory().getItem(i);
                            itemStack.setAmount(1);
                            player.getInventory().remove(itemStack);
                            UsersTable.update(player, "hunterOrderDone", (Integer) UsersTable.get(player, "hunterOrderDone") + 1);
                            if ((int) UsersTable.get(player, "hunterOrderDone") == 35) {
                                UsersTable.update(player, "hunterRank", "Hitman");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGratulacje!!! Zdobyłeś nowy tytuł na mieście - &fHitman"));
                            } else if ((int) UsersTable.get(player, "hunterOrderDone") == 15) {
                                UsersTable.update(player, "hunterRank", "Mafiozo");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGratulacje!!! Zdobyłeś nowy tytuł na mieście - &fMafiozo"));
                            } else if ((int) UsersTable.get(player, "hunterOrderDone") == 5) {
                                UsersTable.update(player, "hunterRank", "Znajoma twarz");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lGratulacje!!! Zdobyłeś nowy tytuł na mieście - &fZnajoma twarz"));
                                Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new TimerTask() {
                                    private int i = 0;

                                    @Override
                                    public void run() {
                                        if (i != darkShop.length) {
                                            player.sendMessage(darkShop[i]);
                                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                            i++;
                                        } else {
                                            timer.cancel();
                                        }
                                    }
                                }, 0, 1000 * 3);
                            }
                            switch ((String) UsersTable.get(Bukkit.getOfflinePlayer(UUID.fromString(UsersTable.get(player,"hunterOrder").toString())), "hunterRank")) {
                                case "Nieznajomy":
                                    reward = 5;
                                    break;
                                case "Znajoma twarz":
                                    reward = 10;
                                    break;
                                case "Mafiozo":
                                    reward = 15;
                                    break;
                                case "Hitman":
                                    reward = 25;
                                    break;
                            }
                            MoneyManager.addMoney(player, reward);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Masz &f&l" + reward + "$&4. Oto twoja wypłata."));
                            UsersTable.update(player, "hunterOrder", "null");
                            UsersTable.update(player, "hunterCountdown", "06:00:00");
                            player.setPlayerListName(player.getName());
                            contains = true;
                        }
                    }
                }
            }
            if(!contains){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Nie mam dowodu, że wykonałeś zlecenie. Przynieś mi głowę..."));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &8» &4Nie masz przyjętego zlecenia."));
        }
    }

    public static void clearOrder(Player player) {
        if(!UsersTable.get(player, "hunterOrder").toString().equalsIgnoreCase("NULL")){
            UsersTable.update(player, "hunterOrder", "null");
            UsersTable.update(player, "hunterCountdown", "06:00:00");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[Hunter] &7» &cStraciłeś zlecenie..."));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(sender instanceof Player){
            if(command.getName().equalsIgnoreCase("spawnhunter")){
                if(player.hasPermission("sgcraft.spawnhunter")){
                    if(NPCBuilder.npcs.containsKey("Hunter")){
                        removeHunter();
                        spawnHunter(player);
                    } else {
                        spawnHunter(player);
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