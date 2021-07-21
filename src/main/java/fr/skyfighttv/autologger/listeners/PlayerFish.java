package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.text.SimpleDateFormat;

public class PlayerFish implements Listener {
    @EventHandler
    private void onPlayerFish(PlayerFishEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerFish.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%fish%", event.getCaught().getName())
                .replaceAll("%exp%", event.getExpToDrop() + "")
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerFish"), text);

        Main.sendDebug("PlayerFish event was called");
        Main.sendLogs("PlayerFish event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerFish"))
            Main.getInstance().webhookClients.get("PlayerFish").send(text);
    }
}
