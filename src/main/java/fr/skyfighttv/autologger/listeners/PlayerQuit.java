package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerQuit implements Listener {
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerQuit.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%message%", Objects.requireNonNull(event.getQuitMessage()));

        FileManager.writeInFile(FileManager.getFiles().get("PlayerQuit"), text);

        Main.sendDebug("PlayerQuit event was called");
        Main.sendLogs("PlayerQuit event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
