package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.text.SimpleDateFormat;

public class PlayerRespawn implements Listener {
    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerRespawn.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerRespawn"), text);

        Main.sendDebug("PlayerRespawn event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerRespawn"))
            Main.getInstance().webhookClients.get("PlayerRespawn").send(text);
    }
}
