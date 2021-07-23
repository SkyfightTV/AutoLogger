package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.text.SimpleDateFormat;

public class PlayerItemHeld implements Listener {
    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerItemHeld.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%previous%", event.getPlayer().getInventory().getItem(event.getPreviousSlot()) == null ? "null" : event.getPlayer().getInventory().getItem(event.getPreviousSlot()).getType().name())
                .replaceAll("%new%", event.getPlayer().getInventory().getItem(event.getNewSlot()) == null ? "null" : event.getPlayer().getInventory().getItem(event.getNewSlot()).getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerItemHeld"), text);

        Main.sendDebug("PlayerItemHeld event was called");
        Main.sendLogs("PlayerItemHeld event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}