package pl.sgcraft.sgcraft.TabList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import pl.sgcraft.sgcraft.SGCraft;
import pl.sgcraft.sgcraft.Utils.MoneyManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MainTabList implements Listener {

    private List<ChatComponentText> headers = new ArrayList<>();
    private List<ChatComponentText> footers = new ArrayList<>();
    public static HashMap<String, MainTabList> tabListHashMap = new HashMap<>();

    private Player player;

    private SGCraft plugin;

    public MainTabList(SGCraft plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        addHeader(ChatColor.translateAlternateColorCodes('&', "&6&lSG&8&lCraft&r\n&6Witamy na serwerze &f" + player.getName() + "&6!"));
        addHeader(ChatColor.translateAlternateColorCodes('&', "&8&lSG&6&lCraft&r\n&6Witamy na serwerze &f" + player.getName() + "&6!"));

        addFooter(ChatColor.translateAlternateColorCodes('&', "&6Saldo w banku: &f" + MoneyManager.getMoney(player)));
        addFooter(ChatColor.translateAlternateColorCodes('&', "&8Saldo w banku: &f" + MoneyManager.getMoney(player)));

        showTab();
    }

    public void showTab() {
        if(headers.isEmpty() && footers.isEmpty())
            return;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            int count1 = 0;
            int count2 = 0;

            @Override
            public void run() {
                try {

                    Field a = packet.getClass().getDeclaredField("header");
                    a.setAccessible(true);
                    Field b = packet.getClass().getDeclaredField("footer");
                    b.setAccessible(true);

                    if (count1 >= headers.size())
                        count1 = 0;
                    if (count2 >= footers.size())
                        count2 = 0;

                    a.set(packet,  headers.get(count1));
                    a.setAccessible(a.isAccessible());
                    b.set(packet, footers.get(count2));
                    b.setAccessible(a.isAccessible());

                    ((CraftPlayer)Bukkit.getPlayer(player.getName())).getHandle().playerConnection.sendPacket(packet);

                    count1++;
                    count2++;

                    footers.remove(0);
                    addFooter("OnlinePlayers: " + Bukkit.getServer().getOnlinePlayers().size());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10, 40);
    }

    public void addHeader(String header) {
        headers.add(new ChatComponentText(header));
    }

    public void addFooter(String footer) {
        footers.add(new ChatComponentText(footer));
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public Player getPlayer() {
        return player;
    }
}



/*
 * +-------------------------------+
 * | §0    black                   |
 * | §1    dark_blue               |
 * | §2    dark_green              |
 * | §3    dark_aqua               |
 * | §4    dark_red                |
 * | §5    dark_purple             |
 * | §6    gold                    |
 * | §7    gray                    |
 * | §8    dark_gray               |
 * | §9    blue                    |
 * | §a    green                   |
 * | §b    aqua                    |
 * | §c    red                     |
 * | §d    light_purple            |
 * | §e    yellow                  |
 * | §f    white                   |
 * |                               |
 * | §k    Obfuscated (MAGIC)      |
 * | §l    Bold                    |
 * | §m    Strikethrough           |
 * | §n    Underline               |
 * | §o    Italic                  |
 * | §r    Reset                   |
 * +-------------------------------+
 */