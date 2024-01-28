package com.corruptedbyte.netherrpg;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public final class NetherRPG extends JavaPlugin implements Listener {
    private final Logger log = getLogger();
    //❤
    public static HashMap<String, Integer> typesData = new HashMap<>();
    public static HashMap<String, Integer> classesData = new HashMap<>();

    @Override
    public void onEnable() {

        try {getConfig().getKeys(false).forEach(name -> typesData.put(name, getConfig().getInt(name+".type")));}
        catch (NullPointerException e) {log.warning("Error loading types! (No config.yml file?) It can be first run issue.");}
        try {getConfig().getKeys(false).forEach(name -> classesData.put(name, getConfig().getInt(name+".class")));}
        catch (NullPointerException e) {log.warning("Error loading classes! (No config.yml file?) It can be first run issue.");}

        getServer().getPluginManager().registerEvents(this, this);
    }


        // Y level buff/debuff
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    if (player.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL){
//                        switch (getType(player)) {
//                            case 2: {
//                                if (player.getY() > 62) {
//                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 120, 0, false, false));
//                                }
//                                else if (player.getY() < 20) player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 110, 0, false, false));
//                                break;
//                            }
//                            case 3: {
//                                if (player.getY() > 62) player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 110, 0, false, false));
//                                else if (player.getY() < 0) {
//                                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0, false, false));
//                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 0, false, false));
//                                }
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }.runTaskTimer(this, 0, 100);

        // In/On block buff/debuff + static effects
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    Block inBlock = player.getLocation().getBlock();
//                    Block onBlock = (player.getLocation().add(0,-1,0)).getBlock();
//                    switch (getType(player)) {
//                        case 4: {
//                            if (player.isInWaterOrRainOrBubbleColumn()) player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 40, 0, false, false));
//                            else player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, false, false));
//                            break;
//                        } case 5: {
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0, false, false));
//                            if (player.isInWaterOrRainOrBubbleColumn()) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0, false, false));
//                            if (coldBlocks.contains(onBlock.getType()) || coldBlocks.contains(inBlock.getType())) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false));
//                            if (onBlock.getType() == Material.SOUL_SAND || inBlock.getType() == Material.SOUL_SAND) player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1, false, false));
//                            break;
//                        } case 6: {
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0, false, false));
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0, false, false));
//                            if (player.isInWaterOrRainOrBubbleColumn()) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false));
//                            if (onBlock.getType() == Material.SOUL_SAND || inBlock.getType() == Material.SOUL_SAND) player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 2, false, false));
//                            if (coldBlocks.contains(onBlock.getType()) || coldBlocks.contains(inBlock.getType())) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, false, false));
//                            break;
//                        }
//                    }
//                }
//            }
//        }.runTaskTimer(this, 0, 20);

//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    if (getType(player) == 4) player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 150, 0, false, false));
//                }
//            }
//        }.runTaskTimer(this, 0, 300);
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Player player = Bukkit.getPlayer("DragonTheByte");
//                if (player != null && player.isOnline()) {
//                    for (Player target : Bukkit.getOnlinePlayers()) {
//                        if (getType(target) == 5) {
//                            if (target.getGameMode() != GameMode.SPECTATOR) circleParticles(target.getLocation(), 3, 0.4d, Particle.DRAGON_BREATH, spinParticlesSlow, player, target);
//                        }
//                    }
//                    spinParticlesSlow += 0.5d;
//                    if (spinParticlesSlow > 360d) spinParticlesSlow -= 360d;
//                }
//            }
//        }.runTaskTimer(this, 0, 1);
//
//        log.info("Loaded!");
//    }

//    private void circleParticles(Location location, int particleCount, double radius, Particle particle, double start_angle, Player player, Player source) {
//        location.add(0, 1, 0);
//        List<Player> receive = new ArrayList<>();
//        receive.add(player);
//        for (int i = 0; i < particleCount; i++) {
//            double angle = (i * (360.0 / particleCount)) + start_angle;
//            double radians = Math.toRadians(angle);
//            double x = Math.cos(radians) * radius;
//            double z = Math.sin(radians) * radius;
//
//            location.add(x, 0, z);
//            location.getWorld().spawnParticle(particle, receive, source, location.getX(), location.getY(), location.getZ(), 1, 0, 0, 0, 0, null);
//            location.subtract(x, 0, z);
//        }
//    }

    @Override
    public void onDisable() {
        typesData.keySet().forEach(name -> getConfig().set(name+".type", typesData.get(name)));
        classesData.keySet().forEach(name -> getConfig().set(name+".class", classesData.get(name)));
        saveConfig();
    }


    public static int getType(String name) {
        return typesData.get(name);
    }

    public static int getClass(String name) {
        return classesData.get(name);
    }

    public static String getClassName(int Class) {
        return switch (Class) {
            case 0 -> "Без класса";
            case 1 -> "Воин";
            case 2 -> "Лучник";
            case 3 -> "Берсерк";
            case 4 -> "Фантом";
            case 5 -> "Маг";
            case 6 -> "Арбалетчик";
            case 50 -> "Хранитель пустоты";
            default -> "> Error, please contact DragonTheByte <";
        };
    }

    public static String getTypeName(int Type) {
        return switch (Type) {
            case 0 -> "Человек";
            case 1 -> "Драконид";
            case 2 -> "Эльф";
            case 3 -> "Гном";
            case 4 -> "Орк";
            case 5 -> "Вампир";
            case 50 -> "Дракон";
            case 51 -> "Exclusive-2";
            default -> "> Error, please contact DragonTheByte <";
        };
    }

    /*
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (event.getCurrentItem() != null && isVoid(event.getCurrentItem())) {
            if (clickedInventory == null || (!clickedInventory.getType().name().equals("PLAYER") && banVoidInvActions.contains(event.getAction())) || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.HOTBAR_SWAP) event.setCancelled(true);
        } else if (isVoid(event.getCursor())) {
            if (clickedInventory == null || (!clickedInventory.getType().name().equals("PLAYER") && banVoidInvActions.contains(event.getAction())) || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.HOTBAR_SWAP) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (isVoid(event.getItemDrop().getItemStack())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && isVoid(event.getItem())) event.setCancelled(true);
    }


    @EventHandler
    public void onPlayerItemFrameChange(PlayerItemFrameChangeEvent event) {
        if (isVoid(event.getItemStack())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getCursor() != null && isVoid(event.getCursor())) event.setCancelled(true);
        if (isVoid(event.getOldCursor())) event.setCancelled(true);
    }

    private boolean isVoid(ItemStack item) {
        return item.getEnchantments().containsKey(Enchantment.VANISHING_CURSE) && item.getType().equals(Material.ECHO_SHARD);
    }
    */
}
