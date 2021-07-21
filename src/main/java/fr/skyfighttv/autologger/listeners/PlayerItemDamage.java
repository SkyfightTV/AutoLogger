package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.text.SimpleDateFormat;

public class PlayerItemDamage implements Listener {
    @EventHandler
    private void onPlayerItemDamage(PlayerItemDamageEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerItemDamage.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%item%", event.getItem().getType().name())
                .replaceAll("%damage%", event.getDamage() + "")
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerItemDamage"), text);

        Main.sendDebug("PlayerItemDamage event was called");
        Main.sendLogs("PlayerItemDamage event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerItemDamage"))
            Main.getInstance().webhookClients.get("PlayerItemDamage").send(text);
    }
}