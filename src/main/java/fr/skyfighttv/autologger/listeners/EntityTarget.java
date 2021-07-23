package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
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
                .replaceAll("%targetx%", event.getTarget() != null ? event.getTarget().getLocation().getBlockX() + "" : "null")
                .replaceAll("%targety%", event.getTarget() != null ? event.getTarget().getLocation().getBlockY() + "" : "null")
                .replaceAll("%targetz%", event.getTarget() != null ? event.getTarget().getLocation().getBlockZ() + "" : "null");

        FileManager.writeInFile(FileManager.getFiles().get("EntityTarget"), text);

        Main.sendDebug("EntityTarget event was called");
        Main.sendLogs("EntityTarget event was called", event.getEntity());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}