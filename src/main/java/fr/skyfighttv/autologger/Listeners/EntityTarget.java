package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.text.SimpleDateFormat;

public class EntityTarget implements Listener {
    @EventHandler
    private void onEntityTarget(EntityTargetEvent event) {
        String text = FileManager.getValues().get("config").getString("EntityTarget.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntityType().name())
                .replaceAll("%entity%", event.getEntityType().name())
                .replaceAll("%reason%", event.getReason().name())
                .replaceAll("%worldname%", event.getEntity().getLocation().getWorld().getName())
                .replaceAll("%x%", event.getEntity().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getEntity().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getEntity().getLocation().getBlockZ() + "")
                .replaceAll("%targetx%", event.getTarget().getLocation().getBlockX() + "")
                .replaceAll("%targety%", event.getTarget().getLocation().getBlockY() + "")
                .replaceAll("%targetz%", event.getTarget().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("EntityTarget"), text);

        Main.sendDebug("EntityTarget event was called");
    }
}