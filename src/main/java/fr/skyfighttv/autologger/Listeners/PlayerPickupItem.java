package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.text.SimpleDateFormat;

public class PlayerPickupItem implements Listener {
    @EventHandler
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerPickupItem.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%amount%", event.getItem().getItemStack().getAmount() + "")
                .replaceAll("%item%", event.getItem().getItemStack().getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerPickupItem"), text);

        Main.sendDebug("PlayerPickupItem event was called");
    }
}