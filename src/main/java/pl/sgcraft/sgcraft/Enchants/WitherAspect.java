package pl.sgcraft.sgcraft.Enchants;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class WitherAspect implements Listener {

    public static final Enchantment WITHER_ASPECT = new CustomEnchant("wither_aspect", "Wither Aspect", 1);

    public static void register(){
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(WITHER_ASPECT);

        if(!registered){
            registerEnchantment(WITHER_ASPECT);
        }
    }


    public static void registerEnchantment(Enchantment enchantment){
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if(registered){
            Bukkit.getServer().getConsoleSender().sendMessage("WihterAspect Zarejestrowano");
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            ItemStack mainhand = player.getInventory().getItemInMainHand();

            if (mainhand.getItemMeta().hasEnchant(WitherAspect.WITHER_ASPECT)) {
                if(!event.getEntity().isDead()){
                    ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 1));
                }
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