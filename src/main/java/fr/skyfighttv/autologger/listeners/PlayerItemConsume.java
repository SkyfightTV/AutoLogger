package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.text.SimpleDateFormat;

public class PlayerItemConsume implements Listener {
    @EventHandler
    private void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerItemConsume.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%item%", event.getItem().getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerItemConsume"), text);

        Main.sendDebug("PlayerItemConsume event was called");
        Main.sendLogs("PlayerItemConsume event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerItemConsume"))
            Main.getInstance().webhookClients.get("PlayerItemConsume").send(text);
    }
}