package pl.sgcraft.sgcraft.Utils;

import org.bukkit.entity.Player;
import pl.sgcraft.sgcraft.Database.UsersTable;

public class MoneyManager {

    public static void addMoney(Player player, Integer i){
        UsersTable.update(player, "money", (Integer) UsersTable.get(player, "money") + i);
    }

    public static void removeMoney(Player player, Integer i){
        UsersTable.update(player, "money", (Integer) UsersTable.get(player, "money") - i);
    }

    public static Integer getMoney(Player player){
        return (Integer) UsersTable.get(player, "money");
    }

    public static void resetMoney(Player player){
        UsersTable.update(player, "money", 0);
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