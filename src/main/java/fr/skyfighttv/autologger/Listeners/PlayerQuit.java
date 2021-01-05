package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerQuit implements Listener {
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerQuit.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis())
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%message%", Objects.requireNonNull(event.getQuitMessage())));

        FileManager.writeInFile(FileManager.getFiles().get("PlayerQuit"), text);
    }
}
