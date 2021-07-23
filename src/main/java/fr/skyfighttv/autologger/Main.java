package fr.skyfighttv.autologger;

import fr.skyfighttv.autologger.commands.AutoLoggerManager;
import fr.skyfighttv.autologger.listeners.*;
import fr.skyfighttv.autologger.utils.DiscordWebhook;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {
    public static String ANSI_RESET = "";
    public static String ANSI_BLACK = "";
    public static String ANSI_RED = "";
    public static String ANSI_GREEN = "";
    public static String ANSI_YELLOW = "";
    public static String ANSI_BLUE = "";
    public static String ANSI_PURPLE = "";
    public static String ANSI_CYAN = "";
    public static String ANSI_WHITE = "";
    private static Main Instance;
    public final HashMap<Player, String> loggers = new HashMap<>();
    private final HashMap<String, Listener> listenerList = new HashMap<String, Listener>() {{
        put("BlockBreak", new BlockBreak());
        put("BlockPlace", new BlockPlace());
        put("EntityDamage", new EntityDamage());
        put("EntityExplode", new EntityExplode());
        put("EntityShootBow", new EntityShootBow());
        put("EntitySpawn", new EntitySpawn());
        put("EntityTarget", new EntityTarget());
        put("PlayerBedEnter", new PlayerBedEnter());
        put("PlayerBedLeave", new PlayerBedLeave());
        put("PlayerBucketEmpty", new PlayerBucketEmpty());
        put("PlayerBucketFill", new PlayerBucketFill());
        put("PlayerChangedWorld", new PlayerChangedWorld());
        put("PlayerChat", new PlayerChat());
        put("PlayerChatTabComplete", new PlayerChatTabComplete());
        put("PlayerCommandPreprocess", new PlayerCommandPreprocess());
        put("PlayerDeath", new PlayerDeath());
        put("PlayerDropItem", new PlayerDropItem());
        put("PlayerExpChange", new PlayerExpChange());
        put("PlayerFish", new PlayerFish());
        put("PlayerGameModeChange", new PlayerGameModeChange());
        put("PlayerInteractAtEntity", new PlayerInteractAtEntity());
        put("PlayerInteract", new PlayerInteract());
        put("PlayerItemBreak", new PlayerItemBreak());
        put("PlayerItemConsume", new PlayerItemConsume());
        put("PlayerItemDamage", new PlayerItemDamage());
        put("PlayerItemHeld", new PlayerItemHeld());
        put("PlayerJoin", new PlayerJoin());
        put("PlayerKick", new PlayerKick());
        put("PlayerLevelChange", new PlayerLevelChange());
        put("PlayerMove", new PlayerMove());
        put("PlayerPickupArrow", new PlayerPickupArrow());
        put("PlayerPickupItem", new PlayerPickupItem());
        put("PlayerPortal", new PlayerPortal());
        put("PlayerQuit", new PlayerQuit());
        put("PlayerRespawn", new PlayerRespawn());
        put("PlayerTeleport", new PlayerTeleport());
    }};
    public Integer fileNumber = 0;
    public HashMap<String, File> folders = new HashMap<>();
    private static final HashMap<String, DiscordWebhook> webhookClients = new HashMap<>();

    public static void sendDebug(String text) {
        if (Main.getInstance().getConfig().getBoolean("DebugMode"))
            System.out.print(ANSI_BLUE + "[AutoLogger] Debug : " + ANSI_PURPLE + text + ANSI_RESET);
    }

    public static void sendLogs(String text, Entity player) {
        for (Player p : Main.getInstance().loggers.keySet()) {
            if (player instanceof Player) {
                if (player.getName().equals(Main.getInstance().loggers.get(p))
                        || Main.getInstance().loggers.get(p).equals("all")) {
                    p.sendMessage("§a§lAutoLogger §f§l>> §e" + player.getName() + " §f§l: §7" + text);
                }
            } else if (Main.getInstance().loggers.get(p).equals("all")) {
                p.sendMessage("§a§lAutoLogger §f§l>> §e" + player.getName() + " §f§l: §7" + text);
            }
        }
    }

    public static void sendWebHook(String event, String text) {
        DiscordWebhook wh = webhookClients.get(event.replaceAll("event", ""));
        System.out.println(Arrays.toString(webhookClients.keySet().toArray()));
        System.out.println(wh);
        if (wh != null) {
            wh.setContent(text);
            try {
                wh.execute();
            } catch (IOException e) {
                System.out.println(ANSI_RED + "ERROR WEBHOOK : " + event + " webhook message was cancel" + ANSI_RESET);
            }
        }
    }

    public static Main getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        Instance = this;

        saveDefaultConfig();

        if (getConfig().getBoolean("ColorConsole")) {
            ANSI_RESET = "\u001B[0m";
            ANSI_BLACK = "\u001B[30m";
            ANSI_RED = "\u001B[31m";
            ANSI_GREEN = "\u001B[32m";
            ANSI_YELLOW = "\u001B[33m";
            ANSI_BLUE = "\u001B[34m";
            ANSI_PURPLE = "\u001B[35m";
            ANSI_CYAN = "\u001B[36m";
            ANSI_WHITE = "\u001B[37m";
        }

        System.out.print(ANSI_YELLOW + "-_-_-_-_- " + ANSI_GREEN + "AutoLogger" + ANSI_YELLOW + " -_-_-_-_-" + ANSI_RESET);
        System.out.print(ANSI_CYAN + "- Creation of folders :" + ANSI_RESET);

        for (String events : getConfig().getConfigurationSection("Modules").getKeys(false)) {
            if (getConfig().getBoolean("Modules." + events)) {
                folders.put(events, new File(getConfig().getString("FolderPath") + "/" + getConfig().getString(events + ".FolderName")));
                folders.get(events).mkdir();
                if (fileNumber == 0)
                    fileNumber = Objects.requireNonNull(folders.get(events).listFiles()).length + 1;

                System.out.print(ANSI_GREEN + events + " folder created" + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + events + " folder skipped" + ANSI_RESET);
            }
        }

        System.out.print(ANSI_CYAN + "- Initialization of files :" + ANSI_RESET);


        try {
            new FileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(ANSI_CYAN + "- Initialization of events :" + ANSI_RESET);

        for (String events : getConfig().getConfigurationSection("Modules").getKeys(false)) {
            if (getConfig().getBoolean("Modules." + events)) {
                getServer().getPluginManager().registerEvents(listenerList.get(events), this);
                System.out.print(ANSI_GREEN + events + " event registered" + ANSI_RESET);
            } else {
                if (HandlerList.getRegisteredListeners(this).contains(listenerList.get(events))) {
                    HandlerList.unregisterAll(listenerList.get(events));
                    System.out.print(ANSI_RED + events + " event unregistered" + ANSI_RESET);
                } else {
                    System.out.print(ANSI_RED + events + " event skipped" + ANSI_RESET);
                }
            }
        }

        System.out.print(ANSI_CYAN + "- Initialization of Discord WebHooks : " + ANSI_RESET);

        for (String events : getConfig().getConfigurationSection("Modules").getKeys(false)) {
            if (getConfig().getString(events + ".WebHookDiscord") != null
                    && !getConfig().getString(events + ".WebHookDiscord").isEmpty()) {
                try {
                    webhookClients.put(events.toLowerCase(), new DiscordWebhook(getConfig().getString(events + ".WebHookDiscord")));
                    System.out.print(ANSI_GREEN + events + " webhook was loaded" + ANSI_RESET);
                } catch (Exception e) {
                    System.out.println(ANSI_RED + "ERROR WEBHOOK : " + events + " can't find webhook" + ANSI_RESET);
                }
            } else {
                System.out.print(ANSI_RED + events + " webhook was skipped" + ANSI_RESET);
            }
        }

        if (getConfig().getBoolean("AutoCleanup.Enable")) {
            System.out.print(ANSI_CYAN + "- AutoCleanup :" + ANSI_RESET);
            long lifeTime = getConfig().getInt("AutoCleanup.MaxFileLife") * 604800000L;
            int number = 0;

            for (File folders : folders.values()) {
                for (File files : folders.listFiles()) {
                    if (files.lastModified() + lifeTime <= System.currentTimeMillis()) {
                        String filename = files.getName();
                        number++;
                        if (files.delete()) {
                            System.out.print(ANSI_GREEN + filename + "was deleted" + ANSI_RESET);
                        } else {
                            System.out.print(ANSI_RED + filename + "could not be deleted" + ANSI_RESET);
                        }
                    }
                }
            }

            if (number == 0)
                System.out.print(ANSI_GREEN + "Nothing was deleted" + ANSI_RESET);
        }

        getCommand("logs").setExecutor(new AutoLoggerManager());

        System.out.print("-_-_-_-_- AutoLogger Initialized -_-_-_-_-");
    }
}