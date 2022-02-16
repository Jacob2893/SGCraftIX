package pl.sgcraft.sgcraft.Database;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.UUID;


public class UsersTable {
    public static boolean playerExists(Player player) {
            try {
                PreparedStatement statement = DatabaseSQL.connection
                        .prepareStatement("SELECT * FROM users WHERE NAME=?");
                statement.setString(1, player.getName());

                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public static void createPlayer(final UUID uuid, Player player) {
            try {
                PreparedStatement statement = DatabaseSQL.connection
                        .prepareStatement("SELECT * FROM users WHERE NAME=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();
                results.next();
                if (!playerExists(player)) {
                    PreparedStatement insert = DatabaseSQL.connection
                            .prepareStatement("INSERT INTO users (NAME,UUID,MONEY) VALUES (?,?,?)");
                    insert.setString(1, player.getName());
                    insert.setString(2, uuid.toString());
                    insert.setInt(3, 0);
                    insert.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static Object get(Player player, String key){
            try {
                PreparedStatement statement = DatabaseSQL.connection
                        .prepareStatement("SELECT * FROM users WHERE NAME=?");
                statement.setString(1, player.getName());
                ResultSet results = statement.executeQuery();
                results.next();

                return results.getObject(key);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

    public static Object get(UUID uuid){
        try {
            PreparedStatement statement = DatabaseSQL.connection
                    .prepareStatement("SELECT * FROM users WHERE HUNTERORDER=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return results.getObject("uuid");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object get(OfflinePlayer player, String key){
        try {
            PreparedStatement statement = DatabaseSQL.connection
                    .prepareStatement("SELECT * FROM users WHERE NAME=?");
            statement.setString(1, player.getName());
            ResultSet results = statement.executeQuery();
            results.next();

            return results.getObject(key);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

        public static void update(Player player, String key, Integer value){
            try {
                PreparedStatement statement = DatabaseSQL.connection
                        .prepareStatement("UPDATE users SET " + key + "=? WHERE NAME=?");
                statement.setInt(1, value);
                statement.setString(2, player.getName());
                statement.execute();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    public static void update(Player player, String key, String value){
        try {
            PreparedStatement statement = DatabaseSQL.connection
                    .prepareStatement("UPDATE users SET " + key + "=? WHERE NAME=?");
            statement.setString(1, value);
            statement.setString(2, player.getName());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void update(String player, String key, Time value){
        try {
            PreparedStatement statement = DatabaseSQL.connection
                    .prepareStatement("UPDATE users SET " + key + "=? WHERE NAME=?");
            statement.setTime(1, value);
            statement.setString(2, player);
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
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