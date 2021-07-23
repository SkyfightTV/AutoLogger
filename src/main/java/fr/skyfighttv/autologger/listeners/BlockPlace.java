package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.SimpleDateFormat;

public class BlockPlace implements Listener {
    @EventHandler
    private void onPlace(BlockPlaceEvent event) {
        String text = FileManager.getValues().get("config").getString("BlockPlace.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%blockname%", event.getBlock().getType().name())
                .replaceAll("%worldname%", event.getBlock().getWorld().getName())
                .replaceAll("%x%", event.getBlock().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getBlock().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getBlock().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("BlockPlace"), text);

        Main.sendDebug("BlockPlace event was called");
        Main.sendLogs("BlockPlace event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
