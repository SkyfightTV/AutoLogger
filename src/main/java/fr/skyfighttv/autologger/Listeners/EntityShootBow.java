package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
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

        if (Main.getInstance().webhookClients.containsKey("EntityShootBow"))
            Main.getInstance().webhookClients.get("EntityShootBow").send(text);
    }
}
