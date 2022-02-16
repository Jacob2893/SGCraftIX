package pl.sgcraft.sgcraft.NPCs;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class NPCBuilder {
    public static HashMap<String, NPCBuilder> npcs = new HashMap<String, NPCBuilder>();

    private NPC npc;

    public NPCBuilder(String name){
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        npcs.put(name, this);
    }

    public void setSkin(String skin){
        npc.data().setPersistent(NPC.PLAYER_SKIN_UUID_METADATA, skin);
        npc.data().setPersistent(NPC.PLAYER_SKIN_USE_LATEST, false);
    }

    public void spawn(Location l){
        npc.spawn(l);
    }

    public void remove(){
        CitizensAPI.removeNamedNPCRegistry(npc.getName());
        npcs.remove(npc.getName());
        npc.destroy();
    }

    public int getId(){
        return npc.getId();
    }

    public UUID getUniqueId(){
        return npc.getUniqueId();
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