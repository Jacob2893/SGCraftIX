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

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class DarkShopNPC implements CommandExecutor, Listener {

    private static final String messages[] = {
            ChatColor.translateAlternateColorCodes('&', "&5Kim jesteś? Nie zgubiłeś się przypadkiem?"),
            ChatColor.translateAlternateColorCodes('&', "&5Pierwszy raz cię widzę na oczy i nic o tobie nie słyszałem"),
            ChatColor.translateAlternateColorCodes('&', "&5&lNie będę handlował z byle darmozjadem"),
            ChatColor.translateAlternateColorCodes('&', "&5&lJak trochę się zasłużysz u mojego znajomego Huntera, przyjdź to może będę coś dla ciebie miał")
    };

    private static String quest1[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lHej! Dzięki za chęć pomocy."),
            ChatColor.translateAlternateColorCodes('&', "&4&lMam do ciebie prośbę."),
            ChatColor.translateAlternateColorCodes('&', "&4&lSłyszałem, że w piekielnym wymiarze występuje nowy materiał."),
            ChatColor.translateAlternateColorCodes('&', "&4&lWiem tyle że jest on ciemny i podobno o wiele wytrzymalszy od diamentu."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPrzynieś mi jego pojedyńczą sztabkę, a ja dam ci za nią nagrodę."),
    };

    private static String newQuest1[] = {
            ChatColor.translateAlternateColorCodes('&', "&4&lNOWE ZADANIE!"),
            ChatColor.translateAlternateColorCodes('&', "&4&lPrzynieś do Zbir'a jedną sztabkę netherite'u"),
            ChatColor.translateAlternateColorCodes('&', "&4&lNagroda: 5$"),
    };

    private static String quest2[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lDzięki za pomoc z poprzednim zleceniem."),
            ChatColor.translateAlternateColorCodes('&', "&4&lMam dla ciebie coś nowego."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPodobno w piekle występują pradawne fortece."),
            ChatColor.translateAlternateColorCodes('&', "&4&lBronią ich mroczni wojownicy."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPrzynieś mi głowę jednego z nich, a srogo cię wynagrodzę."),
    };

    private static String newQuest2[] = {
            ChatColor.translateAlternateColorCodes('&', "&4&lNOWE ZADANIE!"),
            ChatColor.translateAlternateColorCodes('&', "&4&lPrzynieś do Zbir'a jedną głowę witherowego szkieleta"),
            ChatColor.translateAlternateColorCodes('&', "&4&lNagroda: 10$"),
    };

    private static String quest3[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lDzięki za pomoc z poprzednim zleceniem."),
            ChatColor.translateAlternateColorCodes('&', "&4&lMam dla ciebie coś nowego."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPamiętasz głowę tego wojownika którą kazałem ci przynieść?"),
            ChatColor.translateAlternateColorCodes('&', "&4&lDokładnie się jej przyjrzałem i okazuje się, że można jej użyć do przyzwania pradawnego trójgłowego stworzenia."),
            ChatColor.translateAlternateColorCodes('&', "&4&lZabij go, oraz dostarcz mi artefakt, który przy sobie nosi, a dobrze za niego zapłacę."),
    };

    private static String newQuest3[] = {
            ChatColor.translateAlternateColorCodes('&', "&4&lNOWE ZADANIE!"),
            ChatColor.translateAlternateColorCodes('&', "&4&lZabij Wither'a oraz przynieś Zbirowi gwiazdę nether'u"),
            ChatColor.translateAlternateColorCodes('&', "&4&lNagroda: 15$"),
    };

    private static String quest4[] = {
            ChatColor.translateAlternateColorCodes('&', "\n&4&lDzięki za pomoc z poprzednim zleceniem."),
            ChatColor.translateAlternateColorCodes('&', "&4&lChyba wystarczy ci już tego biegania po piekle."),
            ChatColor.translateAlternateColorCodes('&', "&4&lTym razem wybierzesz się dla mnie w inne miejsce."),
            ChatColor.translateAlternateColorCodes('&', "&4&lGłęboko pod ziemią można znaleźć opuszczone twierdze."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPrzeszukaj jedną z nich, a znajdziesz wrota do innego wymiaru, zwanego Kresem."),
            ChatColor.translateAlternateColorCodes('&', "&4&lWymiar ten składa się z głównej wyspy bronionej przez smoka, oraz archipelagu małych wysepek oddalonych od niej o kilometry."),
            ChatColor.translateAlternateColorCodes('&', "&4&lPokonaj bestię, a otworzą się przejścia do tych wysepek."),
            ChatColor.translateAlternateColorCodes('&', "&4&lJeżeli zadasz sobie trochę trudu i dasz radę je pozwiedzać, natrafisz na miasta pełne łupu, bronione przez stworzenia , które potrafią wpasować się w ich strukturę."),
            ChatColor.translateAlternateColorCodes('&', "&4&lNiektóre z tych miast posiadają zadokowane w nich statki."),
            ChatColor.translateAlternateColorCodes('&', "&4&lNa ich pokładzie możesz znaleźć liczny łup oraz co najważniejszę parę skrzydeł oraz głowę smoka przybitą do dzioba statku."),
            ChatColor.translateAlternateColorCodes('&', "&4&lDostarcz mi głowę tej bestii, a uczciwie cię wynagrodzę."),
    };

    private static String newQuest4[] = {
            ChatColor.translateAlternateColorCodes('&', "&4&lNOWE ZADANIE!"),
            ChatColor.translateAlternateColorCodes('&', "&4&lDostań się do End'u, pokonaj smoka, znajdź End City ze statkiem oraz przynieś Zbirowi głowę smoka."),
            ChatColor.translateAlternateColorCodes('&', "&4&lNagroda: 20$"),
    };

    public static void spawnZbir(Player player){
        NPCBuilder npcBuilder = new NPCBuilder("Zbir");
        npcBuilder.setSkin("6dee");
        npcBuilder.spawn(player.getLocation());
    }

    @EventHandler
    public void onZbirClick(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();


        if(event.getRightClicked().getName().equalsIgnoreCase("Zbir")){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                if(UsersTable.get(Bukkit.getOfflinePlayer(player.getUniqueId()), "hunterRank").toString().equalsIgnoreCase("Nieznajomy")){
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
                }
                if(UsersTable.get(Bukkit.getOfflinePlayer(player.getUniqueId()), "hunterRank").toString().equalsIgnoreCase("Znajoma Twarz")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5Witaj z powrotem!"));
                    player.openInventory(inventory(player));
                }
            }
        } else {
            return;
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&0Zbir"))){
            //KUPNO TOWARU
            if(itemStack.getType() == Material.TURTLE_HELMET) {
                buyVietnam(player);
                event.setCancelled(true);
            }
            if(itemStack.getType() == Material.BLAZE_ROD) {
                giveMoney(player);
                event.setCancelled(true);
            }
            if(itemStack.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                buyApple(player);
                event.setCancelled(true);
            }
            //BRANIE QUESTA
            if(itemStack.getType() == Material.EMERALD_BLOCK) {
                player.closeInventory();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    private int i = 0;
                    @Override
                    public void run() {
                        if(i != quest1.length){
                            player.sendMessage(quest1[i]);
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
                            i++;
                        } else {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 0, 1000 * 2);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lNOWE ZADANIE!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lPrzynieś do Zbir'a jedną sztabkę netherite'u"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lNagroda: 5$"));
            }
        } else {
            return;
        }
    }

    private static Inventory inventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&0Zbir"));
        pl.sgcraft.sgcraft.Utils.Inventory.frame(inv);

        //TOWAR
        inv.setItem(10,pl.sgcraft.sgcraft.Utils.Inventory.goldenApple(player));

        //TESTOWE
        inv.setItem(41, pl.sgcraft.sgcraft.Utils.Inventory.wypierdalacz(player));
        inv.setItem(42, pl.sgcraft.sgcraft.Utils.Inventory.jacobSword(player));
        inv.setItem(43, pl.sgcraft.sgcraft.Utils.Inventory.vietnamHelmet(player));

        //GUI
        inv.setItem(4, pl.sgcraft.sgcraft.Utils.Inventory.headInHunterGUI(player));
        inv.setItem(49, pl.sgcraft.sgcraft.Utils.Inventory.barrier());
        inv.setItem(53, pl.sgcraft.sgcraft.Utils.Inventory.buttonQuest(player));
        return inv;
    }

    public static void giveMoney(Player player){
        MoneyManager.addMoney(player, 999);
    }

    public static void buyVietnam(Player player){
        if(MoneyManager.getMoney(player) > 1) {
            MoneyManager.removeMoney(player, 1);
            player.getInventory().addItem(pl.sgcraft.sgcraft.Utils.Inventory.vietnamHelmet(player));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie stać cię na to!"));
        }
    }

    public static void buyApple(Player player){
        if(MoneyManager.getMoney(player) > 64) {
            MoneyManager.removeMoney(player, 64);
            player.getInventory().addItem(pl.sgcraft.sgcraft.Utils.Inventory.goldenApple(player));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie stać cię na to!"));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(sender instanceof Player){
            if(command.getName().equalsIgnoreCase("spawndarkshop")){
                if(player.hasPermission("sgcraft.spawndarkshop")){
                    if(NPCBuilder.npcs.containsKey("Zbir")){
                        spawnZbir(player);
                    } else {
                        spawnZbir(player);
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