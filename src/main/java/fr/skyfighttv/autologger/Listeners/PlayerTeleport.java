package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.IOException;

public class PlayerTeleport implements Listener {
    @EventHandler
    private void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause()==PlayerTeleportEvent.TeleportCause.UNKNOWN) return;
        if (!event.getPlayer().isOnline() || Bukkit.getPlayer(event.getPlayer().getName()) == null) return;

        String text = "("
                + event.getCause().name()
                + ") "
                + event.getFrom().getWorld().getName()
                + " at "
                + (int) event.getFrom().getX()
                + " "
                + (int) event.getFrom().getY()
                + " "
                + (int) event.getFrom().getZ()
                + " -> "
                + event.getTo().getWorld().getName()
                + " at "
                + (int) event.getTo().getX()
                + " "
                + (int) event.getTo().getY()
                + " "
                + (int) event.getTo().getZ();

        FileManager.writeInFile(FileManager.teleportFile, "default", text, event.getPlayer().getName());
    }
}
