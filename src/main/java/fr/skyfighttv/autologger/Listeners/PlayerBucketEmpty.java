package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.text.SimpleDateFormat;

public class PlayerBucketEmpty implements Listener {
    @EventHandler
    private void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerBucketEmpty.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%bucket%", event.getBucket().name())
                .replaceAll("%worldname%", event.getBlockClicked().getWorld().getName())
                .replaceAll("%x%", event.getBlockClicked().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getBlockClicked().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getBlockClicked().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerBucketEmpty"), text);

        Main.sendDebug("PlayerBucketEmpty event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerBucketEmpty"))
            Main.getInstance().webhookClients.get("PlayerBucketEmpty").send(text);
    }
}
