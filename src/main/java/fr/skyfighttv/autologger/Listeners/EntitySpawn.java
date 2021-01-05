package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.text.SimpleDateFormat;

public class EntitySpawn implements Listener {
    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        String text = FileManager.getValues().get("config").getString("EntitySpawn.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntityType().name())
                .replaceAll("%entity%", event.getEntityType().name())
                .replaceAll("%worldname%", event.getLocation().getWorld().getName())
                .replaceAll("%x%", event.getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("EntitySpawn"), text);

        Main.sendDebug("EntitySpawn event was called");
    }
}