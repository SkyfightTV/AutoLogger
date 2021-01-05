package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.text.SimpleDateFormat;

public class PlayerBedEnter implements Listener {
    @EventHandler
    private void onPlayerBedEnter(PlayerBedEnterEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerBedEnter.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%BedEnterResult%", event.getBedEnterResult().name())
                .replaceAll("%worldname%", event.getBed().getWorld().getName())
                .replaceAll("%x%", event.getBed().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getBed().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getBed().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerBedEnter"), text);

        Main.sendDebug("PlayerBedEnter event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerBedEnter"))
            Main.getInstance().webhookClients.get("PlayerBedEnter").send(text);
    }
}
