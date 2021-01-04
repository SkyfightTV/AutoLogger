package fr.skyfighttv.autologger;

import fr.skyfighttv.autologger.Listeners.*;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {
    private static Main Instance;
    public Integer fileNumber;
    public HashMap<String, File> folders = new HashMap<>();
    private final HashMap<String, ?> listenerList = new HashMap<>() {{
        put("BlockBreak", new BlockBreak());
        put("BlockPlace", new BlockPlace());
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
        put("PlayerEditBook", new PlayerEditBook());
        put("PlayerEggThrow", new PlayerEggThrow());
        put("PlayerExpChange", new PlayerExpChange());
        put("PlayerFish", new PlayerFish());
        put("PlayerGameModeChange", new PlayerGameModeChange());
        put("PlayerInteractAtEntity", new PlayerInteractAtEntity());
        put("PlayerInteract", new PlayerInteract());
        put("PlayerItemBreak", new PlayerItemBreak());
        put("PlayerItemConsume", new PlayerItemConsume());
        put("PlayerItemDamage", new PlayerItemDamage());
        put("PlayerItemHeld", new PlayerItemHeld());
        put("PlayerItemMend", new PlayerItemMend());
        put("PlayerJoin", new PlayerJoin());
        put("PlayerKick", new PlayerKick());
        put("PlayerLevelChange", new PlayerLevelChange());
        put("PlayerLogin", new PlayerLogin());
        put("PlayerMove", new PlayerMove());
        put("PlayerPickupArrow", new PlayerPickupArrow());
        put("PlayerPickupItem", new PlayerPickupItem());
        put("PlayerPortal", new PlayerPortal());
        put("PlayerQuit", new PlayerQuit());
        put("PlayerRespawn", new PlayerRespawn());
        put("PlayerSwapHandItems", new PlayerSwapHandItems());
        put("PlayerTeleport", new PlayerTeleport());
        put("PlayerToggleFlight", new PlayerToggleFlight());
        put("PlayerToggleSneak", new PlayerToggleSneak());
        put("PlayerToggleSprint", new PlayerToggleSprint());
    }};

    @Override
    public void onEnable() {
        Instance = this;

        System.out.println("-_-_-_-_- AutoLogger -_-_-_-_-");
        System.out.println("- Creation of folders :");

        for (String events : getConfig().getConfigurationSection("Modules").getKeys(false)) {
            if (getConfig().getBoolean("Modules." + events)) {
                folders.put(events, new File(getConfig().getString("FolderPath") + "/" + getConfig().getString(events + ".FolderName")));
                folders.get(events).mkdir();
                if (fileNumber == 0)
                    fileNumber = Objects.requireNonNull(folders.get(events).listFiles()).length + 1;

                System.out.println(events + " folder created");
            } else {
                System.out.println(events + " folder skipped");
            }
        }

        System.out.println("- Initialization of files :");

        try {
            new FileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("- Initialization of events :");

        for (String events : getConfig().getConfigurationSection("Modules").getKeys(false)) {
            if (getConfig().getBoolean("Modules." + events)) {
                getServer().getPluginManager().registerEvents((Listener) listenerList.get(events), this);
                System.out.println(events + " event registered");
            } else {
                if (HandlerList.getRegisteredListeners(this).contains(listenerList.get(events))) {
                    HandlerList.unregisterAll((Listener) listenerList.get(events));
                    System.out.println(events + " event unregistered");
                } else {
                    System.out.println(events + " event skipped");
                }
            }
        }

        System.out.println("-_-_-_-_- AutoLogger Initialized -_-_-_-_-");
    }


    public static Main getInstance() {
        return Instance;
    }
}
