package pl.sgcraft.sgcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.sgcraft.sgcraft.Commands.Admin.NickCommands;
import pl.sgcraft.sgcraft.Database.DatabaseSQL;
import pl.sgcraft.sgcraft.Database.UsersTable;
import pl.sgcraft.sgcraft.Enchants.CustomEnchant;
import pl.sgcraft.sgcraft.Enchants.WitherAspect;
import pl.sgcraft.sgcraft.Events.PlayerEvents;
import pl.sgcraft.sgcraft.NPCs.*;
import pl.sgcraft.sgcraft.Quests.Quest;
import pl.sgcraft.sgcraft.TabList.MainTabList;
import pl.sgcraft.sgcraft.Utils.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class SGCraft extends JavaPlugin {

    private static SGCraft instance;
    public static boolean connected = false;
    public MainTabList tab;

    public static SGCraft getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        DatabaseSQL.mysqlSetup();
        WitherAspect.register();
        registerEvents();
        registerCommands();

        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[SGCraft] Turning ON &r"));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
            @Override
            public void run(){

                Statement st = null;
                ResultSet res = null;
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    st = DatabaseSQL.connection.createStatement();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                     res = st.executeQuery("SELECT * FROM  users");
                     while (res.next()){
                         if(!timeFormat.format(res.getTime("hunterCountdown")).equalsIgnoreCase("00:00:00")){
                            Time oldTime = res.getTime("hunterCountdown");
                            Time time = Time.valueOf(oldTime.getHours() + ":" + oldTime.getMinutes() + ":" + (oldTime.getSeconds() - 1));
                            UsersTable.update(res.getString("name"), "hunterCountdown", time);
                         }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        },0L, 20L);

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[SGCraft] Turning OFF &r"));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), getInstance());
        Bukkit.getPluginManager().registerEvents(new HunterNPC(), getInstance());
        Bukkit.getPluginManager().registerEvents(new JacobNPC(), getInstance());
        Bukkit.getPluginManager().registerEvents(new LesiuNPC(), getInstance());
        Bukkit.getPluginManager().registerEvents(new TourNPC(), getInstance());
        Bukkit.getPluginManager().registerEvents(new DarkShopNPC(), getInstance());
        Bukkit.getPluginManager().registerEvents(new Inventory(), getInstance());
        Bukkit.getPluginManager().registerEvents(new WitherAspect(), getInstance());
    }


    public void registerCommands(){
        this.getCommand("nick").setExecutor(new NickCommands());
        this.getCommand("spawnhunter").setExecutor(new HunterNPC());
        this.getCommand("spawnprzewodnik").setExecutor(new TourNPC());
        this.getCommand("spawndarkshop").setExecutor(new DarkShopNPC());
        this.getCommand("spawnjacob").setExecutor(new JacobNPC());
        this.getCommand("spawnlesiu").setExecutor(new LesiuNPC());
    }

    private void registerQuests(){
        Quest netheriteIngot = new Quest(new ItemStack(Material.NETHERITE_INGOT), 5);
        netheriteIngot.addMessage("&4&lNOWE ZADANIE!");
        netheriteIngot.addMessage("&4&lPrzynieś do Zbir'a jedną sztabkę netherite'u");
        netheriteIngot.addMessage("&4&lNagroda: 5$");
        Quest.playerQuest.put("netheriteIngot", netheriteIngot);

        Quest witherSkeletonSkull = new Quest(new ItemStack(Material.WITHER_SKELETON_SKULL), 5);
        witherSkeletonSkull.addMessage("&4&lNOWE ZADANIE!");
        witherSkeletonSkull.addMessage("&4&lPrzynieś do Zbir'a jedną głowę witherowego szkieleta");
        witherSkeletonSkull.addMessage("&4&lNagroda: 10$");
        Quest.playerQuest.put("witherSkeletonSkull", witherSkeletonSkull);

        Quest netherStar = new Quest(new ItemStack(Material.NETHER_STAR), 5);
        netherStar.addMessage("&4&lNOWE ZADANIE!");
        netherStar.addMessage("&4&lZabij Wither'a oraz przynieś Zbirowi gwiazdę nether'u");
        netherStar.addMessage("&4&lNagroda: 15$");
        Quest.playerQuest.put("netherStar", netherStar);

        Quest dragonHead = new Quest(new ItemStack(Material.DRAGON_HEAD), 5);
        dragonHead.addMessage("&4&lNOWE ZADANIE!");
        dragonHead.addMessage("&4&lDostań się do End'u, pokonaj smoka, znajdź End City ze statkiem oraz przynieś Zbirowi głowę smoka.");
        dragonHead.addMessage("&4&lNagroda: 20$");
        Quest.playerQuest.put("dragonHead", dragonHead);
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