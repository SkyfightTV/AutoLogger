package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.text.SimpleDateFormat;

public class EntityExplode implements Listener {
    @EventHandler
    private void onEntityExplode(EntityExplodeEvent event) {
        String text = FileManager.getValues().get("config").getString("EntityExplode.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntityType().name())
                .replaceAll("%entity%", event.getEntityType().name())
                .replaceAll("%worldname%", event.getEntity().getWorld().getName())
                .replaceAll("%x%", event.getEntity().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getEntity().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getEntity().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("EntityExplode"), text);

        Main.sendDebug("EntityExplode event was called");
    }
}