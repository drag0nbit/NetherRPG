package com.corruptedbyte.netherrpg;

import com.corruptedbyte.netherrpg.commands.CustomGiveCommand;
import com.corruptedbyte.netherrpg.managers.ItemDataBase;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;


import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import net.kyori.adventure.text.Component;

import static org.bukkit.ChatColor.*;

public final class NetherRPG extends JavaPlugin implements Listener {
    private final Logger log = getLogger();
    private final Random rand = new Random();

    private static BukkitScheduler scheduler;



    public static HashMap<String, Integer> currencyData = new HashMap<>();
    public static HashMap<String, Integer> typesData = new HashMap<>();
    public static HashMap<String, Integer> classesData = new HashMap<>();
    public static HashMap<String, Integer> statHealthData = new HashMap<>();
    public static HashMap<String, Integer> statStrengthData = new HashMap<>();
    public static HashMap<String, Integer> statIntelligenceData = new HashMap<>();
    public static HashMap<String, Integer> statDexterityData = new HashMap<>();
    public static HashMap<String, Integer> statForagingData = new HashMap<>();
    public static HashMap<String, Integer> statMiningData = new HashMap<>();
    public static HashMap<String, Integer> levelData = new HashMap<>();
    public static HashMap<String, Integer> expHealthData = new HashMap<>();
    public static HashMap<String, Integer> expStrengthData = new HashMap<>();
    public static HashMap<String, Integer> expIntelligenceData = new HashMap<>();
    public static HashMap<String, Integer> expDexterityData = new HashMap<>();
    public static HashMap<String, Integer> expForagingData = new HashMap<>();
    public static HashMap<String, Integer> expMiningData = new HashMap<>();
    public static HashMap<String, Integer> expLevelData = new HashMap<>();



    @Override
    public void onEnable() {
        scheduler = this.getServer().getScheduler();

        log.info("Loading save files...");
        try {getConfig().getKeys(false).forEach(name -> typesData.put(name, getConfig().getInt(name+".type")));}
        catch (NullPointerException e) {log.warning("Error loading types! (No config.yml file?) It can be first run issue.");}
        try {getConfig().getKeys(false).forEach(name -> classesData.put(name, getConfig().getInt(name+".class")));}
        catch (NullPointerException e) {log.warning("Error loading classes! (No config.yml file?) It can be first run issue.");}
        log.info("Save files loaded!");
        log.info("Initialising ItemDataBase...");
        ItemDataBase.Initialize(log);
        log.info("ItemDataBase initialised!");

        getCommand("customgive").setExecutor(new CustomGiveCommand());

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(this::sendPlayerListHeaderAndFooter);
        }, 0, 100);

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void sendPlayerListHeaderAndFooter(Player player) {
        Component header = Component.text(""+GOLD+BOLD+"NetherRPG\n"+GRAY+"Игроков онлайн: "+WHITE+Bukkit.getOnlinePlayers().size());
        Component footer = Component.text(GREEN+"Тпс: "+GRAY+BOLD+String.format("%.2f", Bukkit.getServer().getTPS()[0]));
        player.sendPlayerListHeaderAndFooter(header, footer);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayName, message, viewer) -> Component.text(GRAY+"<"+GREEN).append(sourceDisplayName.append(Component.text(GRAY+"> "+WHITE)).append(message)));
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isGliding() && player.getName().equals("DragonTheByte") && player.isSneaking()) {
//            player.setVelocity(new Vector(0,1,0));
//            World world = player.getWorld();
//            world.playSound(player.getLocation(), Sound.BLOCK_WOOL_BREAK, 1f, 0f);
//            world.spawnParticle(Particle.CLOUD, player.getLocation(), 25);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 30, 1, false, false));
            player.setGliding(false);
            player.setCooldown(Material.ELYTRA, 100);
        }
    }

    @EventHandler
    public void onPlayerToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.isGliding() && player.getCooldown(Material.ELYTRA) > 0) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            // event.getDamage() > 2 --> Проверка на "не снежок"
            // event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) --> Если дамаг от стрелы или другого снаряда
            // player.isGliding() --> Если игрок летает на элитре
            if (event.getDamage() > 2 && event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && player.isGliding() && rand.nextInt(10) < 3) {
                player.setGliding(false);
                player.setCooldown(Material.ELYTRA, 100);
            }
        }
    }

//    @EventHandler
//    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
//        if (event.getDamager() instanceof Player player) {
//            try {
//                int id = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
//                if (ItemDataBase.items.get(id) != null) {
//                    Material m = player.getInventory().getItemInMainHand().getType();
//                    boolean b = m.equals(Material.BOW) || m.equals(Material.CROSSBOW);
//                    if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && b) event.setDamage(ItemDataBase.itemDamage.get(id));
//                    else {
//                        if (b) event.setCancelled(true);
//                        event.setDamage(ItemDataBase.itemDamage.get(id));
//                    }
//                } else event.setCancelled(true);
//            } catch (Exception e) {event.setCancelled(true);}
//        }
//    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if (player.isGliding() && player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                player.setSprinting(false);
                if (player.getCooldown(Material.FIREWORK_ROCKET)<=0) {
                    dash(player, 1.5, player.getLocation().getYaw(), player.getLocation().getPitch());
                    player.setCooldown(Material.FIREWORK_ROCKET, 400);

                }
            }
        }
    }
    @EventHandler
    public void onPlayerItemCooldown(PlayerItemCooldownEvent event) {
        if (event.getCooldown() <= 0) {
            if (event.getType() == Material.FIREWORK_ROCKET) event.getPlayer().sendActionBar("< Рывок востановлен >");
        }
    }

//    private boolean isOnCooldown(String playerName) {
//        if (cooldowns.containsKey(playerName)) {
//            Timestamp cooldownTime = cooldowns.get(playerName);
//            long now = System.currentTimeMillis();
//            return now < cooldownTime.getTime();
//        }
//        return false;
//    }
//
//    private void setCooldown(String playerName) {
//        long now = System.currentTimeMillis();
//        cooldowns.put(playerName, new Timestamp(now + COOLDOWN_DURATION));
//    }

    private void dash(Player player, double strength, double yaw, double pitch) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);

        double x = -Math.sin(yaw) * Math.cos(pitch);
        double y = -Math.sin(pitch);
        double z = Math.cos(yaw) * Math.cos(pitch);

        // Normalize the direction vector
        double length = Math.sqrt(x * x + y * y + z * z);
        if (length > 0) {
            x /= length;
            y /= length;
            z /= length;
        }

        player.setVelocity(new Vector(x, y, z).multiply(strength));
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


    public static int getStat(String name, String stat) {
        switch (stat) {
            case "health": {
                return 0;
            }
        }
        return 0;
    }

    public static int getClass(String name) {
        return classesData.get(name);
    }

    public static int getType(String name) {
        return typesData.get(name);
    }


    // NAMES
    public static String getClassName(int Class) {
        return switch (Class) {
            case 0 -> "Воин";
            case 1 -> "Лучник";
            case 2 -> "Берсерк";
            case 3 -> "Фантом";
            case 4 -> "Маг";
            case 5 -> "Арбалетчик";
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
