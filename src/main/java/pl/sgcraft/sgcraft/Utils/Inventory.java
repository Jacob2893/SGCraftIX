package pl.sgcraft.sgcraft.Utils;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import pl.sgcraft.sgcraft.Database.UsersTable;
import pl.sgcraft.sgcraft.Enchants.WitherAspect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

public class Inventory implements Listener {

    public static Integer invFrame[] = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};

    public static void frame(org.bukkit.inventory.Inventory inventory){
        for(int i = 0; i <= invFrame.length - 1; i++){
            inventory.setItem(invFrame[i], glassPane());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() != null) {
            if (event.getClickedInventory().getSize() != event.getWhoClicked().getInventory().getSize()) {
                Player player = (Player) event.getWhoClicked();
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack.getType() == Material.BARRIER) {
                    player.closeInventory();
                    event.setCancelled(true);
                } else if (itemStack.getType() == Material.BLACK_STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public static ItemStack glassPane(){
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack barrier(){
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Zamknij"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    //ITEMY W DARKSHOPIE

    public static ItemStack goldenApple(Player player){
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lJabłko Bogów"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&c64$")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack buttonQuest(Player player){
        ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lZadanie"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&aPrzyjmij zadanie, za którego wykonanie zostaniesz nagrodzony")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    //ITEMY DLA HUNTERA

    public static ItemStack headInHunterGUI(Player player){
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwningPlayer(player);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l" + player.getName()));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&4Kasa: &6" + UsersTable.get(player, "money")),
                ChatColor.translateAlternateColorCodes('&', "&4Wykonane zlecenia: &f" + UsersTable.get(player, "hunterOrderDone")),
                ChatColor.translateAlternateColorCodes('&', "&4Ranga: &f" + UsersTable.get(player, "hunterRank"))));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack takeOrderItemStack(Player player){

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time;
        String message = "&4Czas oczekiwania na nowe zlecenie: &f";
        if(timeFormat.format(UsersTable.get(player, "hunterCountdown")).equalsIgnoreCase("00:00:00")){
            time = "Zlecenie dostępne teraz";
        } else {
            time = timeFormat.format(UsersTable.get(player, "hunterCountdown"));
        }

        if(!UsersTable.get(player, "hunterOrder").toString().equalsIgnoreCase("null")) {
            message = "&4Twoim celem jest &f";
            time = Bukkit.getOfflinePlayer(UUID.fromString((String) UsersTable.get(player, "hunterOrder"))).getName();
        }

        ItemStack itemStack = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lPrzyjmij zlecenie"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&fPrzyjmij zlecenie od &4&lHunter'a"),
                ChatColor.translateAlternateColorCodes('&', "&fna głowę gracza,aby potem"),
                ChatColor.translateAlternateColorCodes('&', "&fodebrać za nią nagrodę"),
                ChatColor.translateAlternateColorCodes('&', " "),
                ChatColor.translateAlternateColorCodes('&', message + time)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getRewardItemStack(Player player){
        ItemStack itemStack = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lOdbierz nagrodę"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&fOdbierz nagrodę od &4&lHunter'a,"),
                ChatColor.translateAlternateColorCodes('&', "&fza wykonanie zlecenia,"),
                ChatColor.translateAlternateColorCodes('&', "&fw postaci &akasy")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack targetHead(Player player){
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(UsersTable.get(player, "hunterOrder").toString())));
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l" + Bukkit.getOfflinePlayer(UUID.fromString(UsersTable.get(player, "hunterOrder").toString())).getName()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack playerHead(Player player){
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwningPlayer(player);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "Głowa gracza " + player.getName()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    //WYJEBANE W KOSMOS ITEMY

    public static ItemStack kufloweMocne(Player player){
        ItemStack itemStack = new ItemStack(Material.POTION, 1, (byte) 8195);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lKuflowe Mocne 7.2%"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&cJak pierdolnie, to ino roz!")));

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack wypierdalacz(Player player){
        ItemStack itemStack = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lWypierdalacz 3000"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&cSiup i cie nie ma,"),
                ChatColor.translateAlternateColorCodes('&', "&cszmato")));
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 31071, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    //PRADAWNY MIECZ LESIA

    public static ItemStack lesiuSword(Player player){
        ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&l&oLESIU SWORD"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&4&oNiech moc gówna w ręku"),
                ChatColor.translateAlternateColorCodes('&', "&4&oBędzie z tobą")));
        itemMeta.addEnchant(Enchantment.KNOCKBACK,-2137, true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, -1, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    //VIETNAM FLASHBACKS

    public static ItemStack vietnamHelmet(Player player) {
        ItemStack itemStack = new ItemStack(Material.TURTLE_HELMET, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&l&oVietnam Flashbacks"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&f&oKiedy go nałożysz, zobaczysz"),
                ChatColor.translateAlternateColorCodes('&', "&f&oto co przeżywali żołnierze w Vietnamie")));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1995, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack witherAspectSword() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&0&lWither Sword"));
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(WitherAspect.WITHER_ASPECT, 1);
        return itemStack;
    }


    // ITEMY DO DARKSHOP'U
    public static ItemStack jacobSword(Player player){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lJacob's Sword"));
        itemMeta.setLore(Arrays.asList(" ",
                ChatColor.translateAlternateColorCodes('&', "&cPradawny miecz Jacob'a,"),
                ChatColor.translateAlternateColorCodes('&', "&cużył go aby zajebać wiktora")));
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 2137, true);
        itemStack.setItemMeta(itemMeta);
        itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - 1));
        return itemStack;
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