package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
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

        if (Main.getInstance().webhookClients.containsKey("PlayerBedLeave"))
            Main.getInstance().webhookClients.get("PlayerBedLeave").send(text);
    }
}
