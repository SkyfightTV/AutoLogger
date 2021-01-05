package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerItemHeld implements Listener {
    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerItemHeld.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%previous%", Objects.requireNonNull(event.getPlayer().getInventory().getItem(event.getPreviousSlot())).getType().name())
                .replaceAll("%new%", Objects.requireNonNull(event.getPlayer().getInventory().getItem(event.getNewSlot())).getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerItemHeld"), text);
    }
}