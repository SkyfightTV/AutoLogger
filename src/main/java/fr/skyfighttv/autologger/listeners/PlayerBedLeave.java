package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.text.SimpleDateFormat;

public class PlayerBedLeave implements Listener {
    @EventHandler
    private void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerBedLeave.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getBed().getWorld().getName())
                .replaceAll("%x%", event.getBed().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getBed().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getBed().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerBedLeave"), text);

        Main.sendDebug("PlayerBedLeave event was called");
        Main.sendLogs("PlayerBedLeave event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
