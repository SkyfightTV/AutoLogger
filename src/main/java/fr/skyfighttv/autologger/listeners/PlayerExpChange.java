package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.text.SimpleDateFormat;

public class PlayerExpChange implements Listener {
    @EventHandler
    private void onPlayerExpChange(PlayerExpChangeEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerExpChange.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%amount%", event.getAmount() + "")
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerExpChange"), text);

        Main.sendDebug("PlayerExpChange event was called");
        Main.sendLogs("PlayerExpChange event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerExpChange"))
            Main.getInstance().webhookClients.get("PlayerExpChange").send(text);
    }
}
