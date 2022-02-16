package pl.sgcraft.sgcraft.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.sgcraft.sgcraft.Database.UsersTable;
import pl.sgcraft.sgcraft.NPCs.HunterNPC;
import pl.sgcraft.sgcraft.SGCraft;
import pl.sgcraft.sgcraft.TabList.MainTabList;
import pl.sgcraft.sgcraft.Utils.Inventory;

import java.util.UUID;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(!UsersTable.playerExists(player)){
            UsersTable.createPlayer(player.getUniqueId(), player);
        }

        MainTabList.tabListHashMap.put(player.getName(), new MainTabList(SGCraft.getInstance(), player));
        player.getInventory().addItem(Inventory.witherAspectSword());
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){

        if(event.getEntity().getType() == EntityType.PLAYER){
            Player player = (Player) event.getEntity();

            if(UsersTable.get(player.getUniqueId()).toString() != null){
                event.getDrops().add(Inventory.targetHead((Player) Bukkit.getOfflinePlayer(UUID.fromString(UsersTable.get(player.getUniqueId()).toString()))));
            } else {
                event.getDrops().add(Inventory.playerHead(player));
            }
            HunterNPC.clearOrder(player);
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