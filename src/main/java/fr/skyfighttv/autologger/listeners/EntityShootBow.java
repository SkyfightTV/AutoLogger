package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.text.SimpleDateFormat;

public class EntityShootBow implements Listener {
    @EventHandler
    private void onEntityShootBow(EntityShootBowEvent event) {
        String text = FileManager.getValues().get("config").getString("EntityShootBow.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntityType().name())
                .replaceAll("%entity%", event.getEntityType().name())
                .replaceAll("%projectile%", event.getProjectile().getName())
                .replaceAll("%worldname%", event.getEntity().getWorld().getName())
                .replaceAll("%x%", event.getEntity().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getEntity().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getEntity().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("EntityShootBow"), text);

        Main.sendDebug("EntityShootBow event was called");
        Main.sendLogs("EntityShootBow event was called", event.getEntity());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
