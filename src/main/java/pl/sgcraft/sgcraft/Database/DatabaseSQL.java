package pl.sgcraft.sgcraft.Database;

import org.bukkit.Bukkit;
import pl.sgcraft.sgcraft.Messages;
import pl.sgcraft.sgcraft.SGCraft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSQL {

    private static String host = "localhost";
    private static String database = "sgcraft";
    private static String username = "root";
    private static String password = "";
    private static Integer port = 3306;

    public static Connection connection;

    public static void mysqlSetup(){
        if(host == null || database == null || username == null || password == null || port == null){
            Bukkit.getConsoleSender().sendMessage(Messages.consoleErrorPrefix + "Cannot connect to database. No data provided. Go to config and set login details!");
        } else {
            try {
                synchronized (SGCraft.getInstance()){
                    if(connection != null && !connection.isClosed()){
                        return;
                    }

                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":"
                            + port + "/" + database, username, password);

                    SGCraft.connected = true;
                    Bukkit.getConsoleSender().sendMessage(Messages.consolePrefix + "Connected to database");
                }
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(Messages.consoleErrorPrefix + "Cannot connect to database.");
                e.printStackTrace();
            } catch (ClassNotFoundException e){
            }
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