package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.text.SimpleDateFormat;

public class PlayerLevelChange implements Listener {
    @EventHandler
    private void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerLevelChange.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%old%", event.getOldLevel() + "")
                .replaceAll("%new%", event.getNewLevel() + "")
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerLevelChange"), text);

        Main.sendDebug("PlayerLevelChange event was called");
        Main.sendLogs("PlayerLevelChange event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
