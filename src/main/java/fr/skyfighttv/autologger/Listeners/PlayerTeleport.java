package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerTeleport implements Listener {
    @EventHandler
    private void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause()==PlayerTeleportEvent.TeleportCause.UNKNOWN) return;
        if (!event.getPlayer().isOnline() || Bukkit.getPlayer(event.getPlayer().getName()) == null) return;

        String text = FileManager.getValues().get("config").getString("PlayerTeleport.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis()))
                        .replaceAll("%playername%", event.getPlayer().getName())
                        .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                        .replaceAll("%cause%", event.getCause().name())
                        .replaceAll("%fromworldname%", Objects.requireNonNull(event.getFrom().getWorld()).getName())
                        .replaceAll("%fromx%", event.getFrom().getBlockX() + "")
                        .replaceAll("%fromy%", event.getFrom().getBlockY() + "")
                        .replaceAll("%fromz%", event.getFrom().getBlockZ()+ "")
                        .replaceAll("%tox%", Objects.requireNonNull(event.getTo()).getBlockX() + "")
                        .replaceAll("%toy%", Objects.requireNonNull(event.getTo()).getBlockY() + "")
                        .replaceAll("%toz%", Objects.requireNonNull(event.getTo()).getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerTeleport"), text);
    }
}