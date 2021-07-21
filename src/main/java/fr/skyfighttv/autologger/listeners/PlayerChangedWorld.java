package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.text.SimpleDateFormat;

public class PlayerChangedWorld implements Listener {
    @EventHandler
    private void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerChangedWorld.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%from%", event.getFrom().getName());

        FileManager.writeInFile(FileManager.getFiles().get("PlayerChangedWorld"), text);

        Main.sendDebug("PlayerChangedWorld event was called");
        Main.sendLogs("PlayerChangedWorld event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerChangedWorld"))
            Main.getInstance().webhookClients.get("PlayerChangedWorld").send(text);
    }
}