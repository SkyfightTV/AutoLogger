package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.SimpleDateFormat;

public class EntityDamage implements Listener {
    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        String text = FileManager.getValues().get("config").getString("EntityDamage.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntityType().name())
                .replaceAll("%entity%", event.getEntityType().name())
                .replaceAll("%cause%", event.getCause().name())
                .replaceAll("%worldname%", event.getEntity().getWorld().getName())
                .replaceAll("%x%", event.getEntity().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getEntity().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getEntity().getLocation().getBlockZ() + "")
                .replaceAll("%damage%", event.getDamage() + "");

        FileManager.writeInFile(FileManager.getFiles().get("EntityDamage"), text);

        Main.sendDebug("EntityDamage event was called");
    }
}