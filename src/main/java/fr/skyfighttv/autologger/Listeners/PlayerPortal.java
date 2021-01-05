package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerPortal implements Listener {
    @EventHandler
    private void onPlayerPortal(PlayerPortalEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerPortal.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis())
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%fromx%", event.getFrom().getBlockX() + "")
                .replaceAll("%fromy%", event.getFrom().getBlockY() + "")
                .replaceAll("%fromz%", event.getFrom().getBlockZ()+ "")
                .replaceAll("%tox%", Objects.requireNonNull(event.getTo()).getBlockX() + "")
                .replaceAll("%toy%", Objects.requireNonNull(event.getTo()).getBlockY() + "")
                .replaceAll("%toz%", Objects.requireNonNull(event.getTo()).getBlockZ() + ""));

        FileManager.writeInFile(FileManager.getFiles().get("PlayerPortal"), text);
    }
}
