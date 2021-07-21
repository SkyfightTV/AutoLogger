package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

import java.text.SimpleDateFormat;

public class PlayerPickupArrow implements Listener {
    @EventHandler
    private void onPlayerPickupArrow(PlayerPickupArrowEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerPickupArrow.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerPickupArrow"), text);

        Main.sendDebug("PlayerPickupArrow event was called");
        Main.sendLogs("PlayerPickupArrow event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerPickupArrow"))
            Main.getInstance().webhookClients.get("PlayerPickupArrow").send(text);
    }
}
