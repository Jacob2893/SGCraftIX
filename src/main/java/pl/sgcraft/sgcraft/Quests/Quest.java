package pl.sgcraft.sgcraft.Quests;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.sgcraft.sgcraft.Utils.MoneyManager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Quest {

    public static HashMap<String, Quest> playerQuest = new HashMap<>();

    public static HashMap<String, HashMap> npcQuests = new HashMap<>();
    public static HashMap<Integer, Quest> quests = new HashMap<>();

    private String messages[];
    private Integer reward; //MONEY
    private ItemStack order;

    public Quest(ItemStack order, Integer reward){
        this.order = order;
        this.reward = reward;
    }

    public void addMessage(String msg){
        messages[messages.length] = ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void getReward(Player player){
        MoneyManager.addMoney(player, reward);
    }

    public void sendMessage(Player player){
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