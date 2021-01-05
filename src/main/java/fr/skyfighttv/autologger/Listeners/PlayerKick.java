package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerKick implements Listener {
    @EventHandler
    private void onPlayerKick(PlayerKickEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerKick.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%message%", Objects.requireNonNull(event.getLeaveMessage()))
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerKick"), text);

        Main.sendDebug("PlayerKick event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerKick"))
            Main.getInstance().webhookClients.get("PlayerKick").send(text);
    }
}
