package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.text.SimpleDateFormat;

public class PlayerDropItem implements Listener {
    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerDropItem.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%amount%", event.getItemDrop().getItemStack().getAmount() + "")
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerDropItem"), text);

        Main.sendDebug("PlayerDropItem event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerDropItem"))
            Main.getInstance().webhookClients.get("PlayerDropItem").send(text);
    }
}
