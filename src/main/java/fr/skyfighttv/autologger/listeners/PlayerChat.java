package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;

public class PlayerChat implements Listener {
    @EventHandler
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerChat.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%message%", event.getMessage());

        FileManager.writeInFile(FileManager.getFiles().get("PlayerChat"), text);

        Main.sendDebug("PlayerChat event was called");
        Main.sendLogs("PlayerChat event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerChat"))
            Main.getInstance().webhookClients.get("PlayerChat").send(text);
    }
}
